package semantics;

import beaver.Symbol;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import static org.extendj.parser.JavaParser.Terminals.*;


/**
 * Scanner extension to help the parser figure out ambiguous syntax.
 *
 * The parser sometimes can't figure out if ambiguous sequences like
 * <code>⊤</code> or <code>∃▽·«:whatever</code> is actually a type declaration
 * instead of an expression, and then fails with a strange error message rather
 * than backtracking and trying again. This helper does some token lookahead
 * and figures out if tokens like <code>⊤</code>, <code>⊥</code>,
 * <code>▽</code>, <code>△</code>, <code>∃</code> or <code>∀</code> are
 * definitely part of a type and emits special tokens for them in that case.
 *
 * This would ideally use ExtendJ's Java 8 scanner lookahead buffer for this,
 * but unfortunately that's not extensible, since all the fields and methods
 * are <code>private</code>. So this is instead another buffer on top of that
 * one, but at least this interface is extensible.
 */
public class JavaScanner extends org.extendj.scanner.JavaScanner {
  /** The token lookahead buffer. */
  protected LinkedList<Symbol> buf = new LinkedList<>();

  public JavaScanner(Reader in) {
    super(in);
  }


  @Override
  public Symbol nextToken() throws IOException, Exception {
    // Takes the next token out of the lookahead buffer if possible.
    return handle(buf.isEmpty() ? super.nextToken() : buf.poll());
  }

  /**
   * Reads the next token, handles it and puts it in the lookahead buffer.
   *
   * This is different from {@link #nextToken() nextToken} in that it always
   * reads the next token from the stream, rather than taking it out of the
   * buffer if it's non-empty, since that'd be useless for lookahead purposes.
   *
   * @return Symbol The next token in the stream, after being
   *                {@link #handle(Symbol) handle}d.
   */
  protected Symbol bufferNextToken() throws Exception, IOException {
      Symbol token = super.nextToken();
      buf.addLast(token);
      return handle(token);
  }

  /**
   * Convenience method, buffers the next token and returns its ID.
   */
  protected short lookAhead() throws Exception, IOException {
    return bufferNextToken().getId();
  }

  /**
   * Handle the next token, potentially doing lookahead and intuiting.
   *
   * This is the main entry point for this helper class. You'd override this
   * method if you want to do your own token intuiting and defer back to it in
   * the default case.
   *
   *
   * @param token The token that would be sent to the parser next, which may
   *              either come from the token stream or the lookahead buffer.
   *
   * @return The token that will actually be sent to the parser. If there's
   *         nothing to intuit, this should just be the given parameter.
   *         Otherwise, it can be some different token that helps the parser
   *         figure out what's going on.
   */
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


  /**
   * Turn a Semantics4J token into its equivalent type token.
   *
   * For example, the <code>∃</code> token is turned from
   * <code>DL_EXISTENCE</code> into <code>DLT_EXISTENCE</code>. All the tokens
   * that don't have a type equivalent are left alone and simply returned.
   */
  protected Symbol toTypeToken(Symbol token) {
    switch (token.getId()) {
      case DL_EXISTENCE:  return new Symbol(DLT_EXISTENCE);
      case DL_UNIVERSAL:  return new Symbol(DLT_UNIVERSAL);
      case DL_EVERYTHING: return new Symbol(DLT_EVERYTHING);
      case DL_NOTHING:    return new Symbol(DLT_NOTHING);
      default:            return token;
    }
  }

  /**
   * Mark the latest tokens in the buffer as type tokens.
   *
   * This is called after a semantic type is intuited. All the tokens except
   * the last one (since that one is the one that decided the lookahead) are
   * converted with {@link #toTypeToken(Symbol) toTypeToken}. This prevents
   * {@link #handle(Symbol) handle} from having to do the same work again.
   */
  protected Symbol makeTypeTokens(Symbol first) {
    for (int i = 0; i < buf.size() - 1; ++i) {
      buf.set(i, toTypeToken(buf.get(i)));
    }
    return toTypeToken(first);
  }


  /**
   * Intuits if a quantifier token is part of a semantic type.
   *
   * If it's followed by a DL literal or an opening bracket, it must be part of
   * a type. If it's followed by <code>▽</code> or <code>△</code> further work
   * is done by {@link #intuitDotType() intuitDotType}. Anything else means
   * it's not a type.
   */
  protected boolean intuitQuantifierType() throws Exception, IOException {
    switch (lookAhead()) {
      case DL_LITERAL:
      case LBRACK:
        return true;

      case DL_FULL:
      case DL_EMPTY:
        return intuitDotType();

      default:
        return false;
    }
  }

  /**
   * Intuits if a quantifier's role is part of a semantic type.
   *
   * Skips over any <code>⁻</code> tokens until it reaches the <code>·</code>,
   * at which point it dispatches to
   * {@link #intuitConceptType() intuitConceptType}. Any other token means this
   * is not a type.
   */
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

  /**
   * Intuits if a concept is part of a semantic type.
   *
   * Skips over any <code>¬</code> and <code>⎨</code> tokens. If it finds any
   * <code>DLT_*</code> type token or a DL literal or a left bracket, it must
   * be a type. If it finds <code>⊤</code> or <code>⊥</code>, further
   * processing needs to be done by
   * {@link #intuitConceptTypeEnd() intuitConceptTypeEnd}. Otherwise, it's not
   * a type.
   */
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

  /**
   * Intuits if a concept expression is over and is part of a type.
   *
   * Opening parentheses and brackets are skipped over. If a <code>⊔</code> or
   * <code>⊓</code> follows, this calls back to
   * {@link #intuitConceptType() intuitConceptType}, since the concept
   * expression isn't actually over. When it's followed by an identifier or any
   * kind of <code>&gt;</code> combination, it must be a type, either a
   * variable declaration or a generic type (it can't be a comparison or a
   * shift operation, since those operations don't make sense on concept
   * expressions). Anything else means this is not a type.
   */
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
