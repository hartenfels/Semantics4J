package semantics.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import static semantics.KnowBase.toTaggedArray;


public class Role extends Base implements Roleish {
  private final String iri;

  /**
   * Internal, use {@link semantics.Util#role(String) semantics.Util.role} instead.
   */
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
    return toTaggedArray("r", new JsonPrimitive(iri));
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
