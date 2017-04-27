package semantics.model;

import com.google.gson.JsonElement;


public interface Jsonable {
  public JsonElement toJson();
}
