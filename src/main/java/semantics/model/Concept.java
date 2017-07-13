package semantics.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import semantics.KnowBase;
import static semantics.KnowBase.toTaggedArray;


public class Concept extends Base implements Conceptual {
  private final String iri;

  /**
   * Internal, use {@link semantics.Util#concept(String) semantics.Util.concept} instead.
   */
  public Concept(String iri) {
    this.iri = iri;
  }

  @Override
  public String getSignatureType() {
    return "concept";
  }

  @Override
  public String getSignatureIri() {
    return iri;
  }

  @Override
  public JsonElement toJson() {
    return toTaggedArray("C", new JsonPrimitive(iri));
  }

  @Override
  public String toString() {
    return String.format("«%s»", iri);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Concept) {
      return iri.equals(((Concept) o).iri);
    }
    return false;
  }
}
