package semantics.err;


/** Base for Semantics4J-related exceptions. */
public abstract class SemanticsException extends RuntimeException {
  public SemanticsException(String msg) {
    super(msg);
  }

  public SemanticsException(Exception e) {
    super(e);
  }

  public SemanticsException(String msg, Exception e) {
    super(msg, e);
  }
}
