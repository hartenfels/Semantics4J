package semantics.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import semantics.KnowBase;


public final class Individual implements Comparable<Individual> {
  private final KnowBase kb;
  private final String   iri;

  public Individual(KnowBase kb, String iri) {
    this.kb  = kb;
    this.iri = iri;
  }


  public KnowBase getKnowBase() {
    return kb;
  }

  public String getIri() {
    return iri;
  }

  public String getName() {
    // FIXME: URIs aren't exactly IRIs, since they don't allow Unicode
    // characters. This either needs URL encoding or a real IRI library.
    try {
      return new URI(iri).getFragment();
    }
    catch (URISyntaxException e) {
      return iri;
    }
  }


  @Override
  public String toString() {
    return getIri();
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Individual) {
      Individual i = (Individual) other;
      return kb.equals(i.kb) && iri.equals(i.iri);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return (kb + "\0" + iri).hashCode();
  }


  public int compareTo(Individual other) {
    return getIri().compareTo(other.getIri());
  }
}
