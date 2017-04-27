package semantics.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import semantics.Util;


public class Role extends Roleish {
  private final String iri;

  public Role(String iri) {
    this.iri = iri;
  }

  public JsonElement toJson() {
    return Util.toTaggedArray("r", new JsonPrimitive(iri));
  }

  @Override
  public String toString() {
    return String.format("<%s>", iri);
  }
}
