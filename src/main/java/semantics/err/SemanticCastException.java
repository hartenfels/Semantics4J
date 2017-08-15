package semantics.err;

import semantics.model.Conceptual;
import semantics.model.Individual;


/** Exception for when a cast with a semantic type fails. */
public class SemanticCastException extends ClassCastException {
  public SemanticCastException(Conceptual c, Individual i) {
    super(String.format("'%s' is not a member of '%s'", i, c));
  }
}
