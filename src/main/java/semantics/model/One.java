package semantics.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import semantics.Util;


public class One extends Conceptual {
  private final String iri;

  public One(String iri) {
    this.iri = iri;
  }

  public JsonElement toJson() {
    return Util.toTaggedArray("O", new JsonPrimitive(iri));
  }

  @Override
  public String toString() {
    return String.format("{%s}", iri);
  }
}
