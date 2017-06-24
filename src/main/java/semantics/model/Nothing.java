package semantics.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;


public class Nothing extends Conceptual {
  @Override
  public JsonElement toJson() {
    return new JsonPrimitive(false);
  }

  @Override
  public String toString() {
    return "⊥";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Nothing;
  }
}
