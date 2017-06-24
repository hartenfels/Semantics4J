package semantics.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import semantics.Util;


public class Role extends Roleish {
  private final String iri;

  public Role(String iri) {
    this.iri = iri;
  }

  @Override
  public String getSignatureType() {
    return "role";
  }

  @Override
  public String getSignatureIri() {
    return iri;
  }

  @Override
  public JsonElement toJson() {
    return Util.toTaggedArray("r", new JsonPrimitive(iri));
  }

  @Override
  public String toString() {
    return String.format("«%s»", iri);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Role) {
      return iri.equals(((Role) o).iri);
    }
    return false;
  }
}
