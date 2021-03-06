package semantics.model;

import com.google.gson.JsonElement;


public class UnknownConcept extends Conceptual {
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
  public Conceptual stripUnknown() {
    return new Everything();
  }

  @Override
  public String toString() {
    return "(unknown concept)";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof UnknownConcept;
  }
}
