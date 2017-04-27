package semantics.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import semantics.Util;


public class Concept extends Conceptual {
  private final String iri;

  public Concept(String iri) {
    this.iri = iri;
  }

  public JsonElement toJson() {
    return Util.toTaggedArray("C", new JsonPrimitive(iri));
  }

  @Override
  public String toString() {
    return String.format("<%s>", iri);
  }
}
