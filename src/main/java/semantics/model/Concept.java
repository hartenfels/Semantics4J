package semantics.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import semantics.KnowBase;
import semantics.Util;


public class Concept extends Conceptual {
  private final String iri;

  public Concept(String iri) {
    this.iri = iri;
  }

  public void checkSignature(KnowBase kb) {
    kb.checkConceptSignature(iri);
  }

  public JsonElement toJson() {
    return Util.toTaggedArray("C", new JsonPrimitive(iri));
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
