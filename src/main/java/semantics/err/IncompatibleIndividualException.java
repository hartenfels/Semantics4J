package semantics.err;

import semantics.KnowBase;
import semantics.model.Individual;


/**
 * Exception for when data sources are intermixed.
 *
 * Attempting to use an Individual with one data source on a knowledge base of
 * a different data source will throw an exception like this. That is always a
 * bug in the code, either you didn't intend to actually mix data sources, or
 * you need to convert your Individual by constructing a new one with the
 * expected data source and the same IRI.
 */
public class IncompatibleIndividualException extends SemanticsException {
  public IncompatibleIndividualException(String msg) {
    super(msg);
  }

  public IncompatibleIndividualException(KnowBase kb, Individual i) {
    this(String.format("Data source mismatch for '%s': '%s' when '%s' expected",
                       i, i.getSource(), kb));
  }
}
