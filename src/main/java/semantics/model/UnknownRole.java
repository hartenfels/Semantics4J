package semantics.model;

import com.google.gson.JsonElement;


public class UnknownRole extends Roleish {
  @Override
  public JsonElement toJson() {
    throw new UnsupportedOperationException(
        "Unknown role cannot be converted to JSON");
  }

  @Override
  public boolean isUnknown() {
    return true;
  }

  @Override
  public String toString() {
    return "?";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof UnknownRole;
  }
}
