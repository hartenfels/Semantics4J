package semantics.err;


public class CommunicationException extends SemanticsException {
  public CommunicationException(String msg) {
    super(msg);
  }

  public CommunicationException(Exception e) {
    super(e);
  }
}
