package semantics.err;

import semantics.KnowBase;
import semantics.model.Individual;


public class IncompatibleIndividualException extends SemanticsException {
  public IncompatibleIndividualException(String msg) {
    super(msg);
  }

  public IncompatibleIndividualException(KnowBase kb, Individual i) {
    this(String.format("Incompatible KnowBase for <%s>: %s when %s expected",
                       i, i.getKnowBase(), kb));
  }
}
