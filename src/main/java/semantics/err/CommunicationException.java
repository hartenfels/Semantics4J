package semantics.err;


public class CommunicationException extends SemanticsException {
  public CommunicationException(Exception e) {
    super("Can't communicate with knowledge base server (is it running?)", e);
  }
}
