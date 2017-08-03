package semantics.model;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;


/**
 * An individual, retrieved via querying or projection.
 *
 * This represents an individual from a knowledge base. It's basically just an
 * identity built from a knowledge base and an IRI string. But don't mess with
 * the knowledge base part directly.
 */
public final class Individual implements Comparable<Individual>, Serializable {
  private final String source;
  private final String iri;

  /**
   * Internal, don't use.
   *
   * Get your individuals by querying, don't construct them yourself.
   */
  public Individual(String source, String iri) {
    this.source = source;
    this.iri    = iri;
  }


  /**
   * This individual's data source.
   *
   * This is usually the path that was given in the <code>from</code>
   * specification in the class that you queried it from.
   */
  public String getSource() {
    return source;
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
      return source.equals(i.source) && iri.equals(i.iri);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return (source + "\0" + iri).hashCode();
  }

  @Override
  public int compareTo(Individual other) {
    return getIri().compareTo(other.getIri());
  }
}
