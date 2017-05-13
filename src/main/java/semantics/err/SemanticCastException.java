package semantics.err;

import semantics.model.Conceptual;
import semantics.model.Individual;


public class SemanticCastException extends ClassCastException {
  public SemanticCastException(String msg) {
    super(msg);
  }

  public SemanticCastException(Conceptual c, Individual i) {
    this(String.format("<%s> is not a member of %s", i, c));
  }
}
