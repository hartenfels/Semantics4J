package semantics.err;


/**
 * Exception for when an IO error with the knowledge base server occurs.
 *
 * This basically just wraps an <code>IOException</code> with a nicer error
 * message (and makes it an unchecked exception of course). This usually means
 * that you forgot to run the knowledge base server (semserv) or that it went
 * away for some reason.
 */
public class CommunicationException extends SemanticsException {
  public CommunicationException(Exception e) {
    super("Can't communicate with knowledge base server (is it running?)", e);
  }
}
