package semantics.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;


public class Everything extends Conceptual {
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
