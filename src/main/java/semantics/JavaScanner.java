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
        return new Symbol(intuitQuantifier(DL_EXISTENCE, DLT_EXISTENCE));
      case DL_UNIVERSAL:
        return new Symbol(intuitQuantifier(DL_UNIVERSAL, DLT_UNIVERSAL));
      default:
        return token;
    }
  }


  protected Symbol bufferNextToken() throws Exception, IOException {
      Symbol token = super.nextToken();
      buf.addLast(token);
      return handle(token);
  }

  protected short lookAhead() throws Exception, IOException {
    return bufferNextToken().getId();
  }


  private short intuitQuantifier(short exprId, short typeId)
      throws Exception, IOException {
    switch (lookAhead()) {
      case DL_LITERAL:
      case LBRACK:
        return typeId;

      case DL_ANY:
      case DL_NONE:
        return intuitQuantifierDot(exprId, typeId);

      default:
        return exprId;
    }
  }

  private short intuitQuantifierDot(short exprId, short typeId)
      throws Exception, IOException {
    while (true) {
      switch (lookAhead()) {
        case DL_INVERSION:
          continue;

        case DL_DOT:
        case ELLIPSIS:
          return intuitQuantifierConcept(exprId, typeId);

        default:
          return exprId;
      }
    }
  }

  private short intuitQuantifierConcept(short exprId, short typeId)
      throws Exception, IOException {
    while (true) {
      switch (lookAhead()) {
        case DL_NEGATION:
        case DL_LCURLY:
          continue;

        case DLT_EXISTENCE:
        case DLT_UNIVERSAL:
        case DL_LITERAL:
        case LBRACK:
          return typeId;

        case DL_EVERYTHING:
        case DL_NOTHING:
          return intuitQuantifierFinal(exprId, typeId);

        default:
          return exprId;
      }
    }
  }

  private short intuitQuantifierFinal(short exprId, short typeId)
      throws Exception, IOException {
    while (true) {
      switch (lookAhead()) {
        case RPAREN:
        case RBRACK:
          continue;

        case GT:
        case RSHIFT:
        case URSHIFT:
        case IDENTIFIER:
          return typeId;

        default:
          return exprId;
      }
    }
  }
}
