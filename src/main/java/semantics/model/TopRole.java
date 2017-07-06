package semantics.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;


public class TopRole extends Roleish {
  @Override
  public JsonElement toJson() {
    return new JsonPrimitive(true);
  }

  @Override
  public String toString() {
    return "↑";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof TopRole;
  }
}
