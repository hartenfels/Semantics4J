package semantics.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import semantics.KnowBase;


/**
 * An individual, retrieved via querying or projection.
 *
 * This represents an individual from a knowledge base. It's basically just an
 * identity built from a knowledge base and an IRI string. But don't mess with
 * the knowledge base part directly.
 */
public final class Individual implements Comparable<Individual> {
  private final KnowBase kb;
  private final String   iri;

  /**
   * Internal, don't use.
   *
   * Get your individuals by querying, don't construct them yourself.
   */
  public Individual(KnowBase kb, String iri) {
    this.kb  = kb;
    this.iri = iri;
  }


  /**
   * This individual's knowledge base, for identity.
   *
   * But don't use it for anything other than checking the identity of the
   * knowledge base, all the knowledge base functions are internal-only.
   */
  public KnowBase getKnowBase() {
    return kb;
  }

  /**
   * This individual's IRI.
   *
   * That's an Internationalized Resource Identifier, similar to a URL.
   */
  public String getIri() {
    return iri;
  }

  /**
   * Tries to extract the fragment part from the IRI.
   *
   * You can use this to display individuals to the user. If for some reason
   * the fragment part can't be figured out, you'll get the entire IRI instead.
   */
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

  @Override
  public int compareTo(Individual other) {
    return getIri().compareTo(other.getIri());
  }
}
