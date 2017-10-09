package semantics.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import semantics.KnowBase;


public class Property {
  private final String iri;

  public Property(String iri) {
    this.iri = iri;
  }

  public void checkSignature(KnowBase kb) {
    kb.checkSignature("property", iri);
  }

  public JsonElement toJson() {
    return new JsonPrimitive(iri);
  }

  @Override
  public String toString() {
    return String.format("‹%s›", iri);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Property) {
      return iri.equals(((Property) o).iri);
    }
    return false;
  }
}
