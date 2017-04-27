package semantics.err;


public class SemanticsException extends RuntimeException {
  public SemanticsException(String msg) {
    super(msg);
  }

  public SemanticsException(Exception e) {
    super(e);
  }
}
