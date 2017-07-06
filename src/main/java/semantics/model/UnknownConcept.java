package semantics.model;

import com.google.gson.JsonElement;


public class UnknownConcept extends Base implements Conceptual {
  @Override
  public JsonElement toJson() {
    throw new UnsupportedOperationException(
        "Unknown concept cannot be converted to JSON");
  }

  @Override
  public boolean isUnknown() {
    return true;
  }

  @Override
  public Conceptual stripUnknownConcept() {
    return new Everything();
  }

  @Override
  public String toString() {
    return "?";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof UnknownConcept;
  }
}
