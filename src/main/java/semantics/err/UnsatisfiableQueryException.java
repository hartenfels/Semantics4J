package semantics.err;

import semantics.KnowBase;
import semantics.model.Conceptual;


public class UnsatisfiableQueryException extends SemanticsException {
  public UnsatisfiableQueryException(KnowBase kb, Conceptual c) {
    super(String.format("query '%s' in '%s'", c, kb));
  }
}
