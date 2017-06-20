package semantics.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import semantics.KnowBase;


public class Everything extends Conceptual {
  public void checkSignature(KnowBase kb) {
    // ⊤ is always in the signature
  }

  public JsonElement toJson() {
    return new JsonPrimitive(true);
  }

  @Override
  public String toString() {
    return "⊤";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Everything;
  }
}
