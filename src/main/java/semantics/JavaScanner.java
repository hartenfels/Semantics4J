package semantics;

import beaver.Symbol;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import static org.extendj.parser.JavaParser.Terminals.*;


public class JavaScanner extends org.extendj.scanner.JavaScanner {
  protected LinkedList<Symbol> buf = new LinkedList<>();

  public JavaScanner(Reader in) {
    super(in);
  }


  @Override
  public Symbol nextToken() throws IOException, Exception {
    return handle(buf.isEmpty() ? super.nextToken() : buf.poll());
  }

  protected Symbol handle(Symbol token) throws Exception, IOException {
    switch (token.getId()) {
      case DL_EXISTENCE:
      case DL_UNIVERSAL:
        return intuitQuantifierType() ? makeTypeTokens(token) : token;
      case DL_EVERYTHING:
      case DL_NOTHING:
        return intuitConceptTypeEnd() ? makeTypeTokens(token) : token;
      default:
        return token;
    }
  }


  protected Symbol toTypeToken(Symbol token) {
    switch (token.getId()) {
      case DL_EXISTENCE:  return new Symbol(DLT_EXISTENCE);
      case DL_UNIVERSAL:  return new Symbol(DLT_UNIVERSAL);
      case DL_EVERYTHING: return new Symbol(DLT_EVERYTHING);
      case DL_NOTHING:    return new Symbol(DLT_NOTHING);
      default:            return token;
    }
  }

  protected Symbol makeTypeTokens(Symbol first) {
    for (int i = 0; i < buf.size() - 1; ++i) {
      buf.set(i, toTypeToken(buf.get(i)));
    }
    return toTypeToken(first);
  }


  protected Symbol bufferNextToken() throws Exception, IOException {
      Symbol token = super.nextToken();
      buf.addLast(token);
      return handle(token);
  }

  protected short lookAhead() throws Exception, IOException {
    return bufferNextToken().getId();
  }


  protected boolean intuitQuantifierType() throws Exception, IOException {
    switch (lookAhead()) {
      case DL_LITERAL:
      case LBRACK:
        return true;

      case DL_ANY:
      case DL_NONE:
        return intuitDotType();

      default:
        return false;
    }
  }

  protected boolean intuitDotType() throws Exception, IOException {
    while (true) {
      switch (lookAhead()) {
        case DL_INVERSION:
          continue;

        case DL_DOT:
        case ELLIPSIS:
          return intuitConceptType();

        default:
          return false;
      }
    }
  }

  protected boolean intuitConceptType() throws Exception, IOException {
    while (true) {
      switch (lookAhead()) {
        case DL_NEGATION:
        case DL_LCURLY:
          continue;

        case DLT_EXISTENCE:
        case DLT_UNIVERSAL:
        case DLT_EVERYTHING:
        case DLT_NOTHING:
        case DL_LITERAL:
        case LBRACK:
          return true;

        case DL_EVERYTHING:
        case DL_NOTHING:
          return intuitConceptTypeEnd();

        default:
          return false;
      }
    }
  }

  protected boolean intuitConceptTypeEnd() throws Exception, IOException {
    while (true) {
      switch (lookAhead()) {
        case RPAREN:
        case RBRACK:
          continue;

        case DL_UNION:
        case DL_INTERSECTION:
          return intuitConceptType();

        case GT:
        case RSHIFT:
        case URSHIFT:
        case IDENTIFIER:
          return true;

        default:
          return false;
      }
    }
  }
}
