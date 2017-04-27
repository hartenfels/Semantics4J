package semantics.model;

import com.google.gson.JsonElement;
import semantics.Util;


public class Intersection extends Conceptual {
  private final Conceptual[] cs;

  public Intersection(Conceptual... cs) {
    this.cs = cs;
  }

  public JsonElement toJson() {
    return Util.toTaggedArray("I", Util.allToJson(cs));
  }

  @Override
  public String toString() {
    return String.format("(%s)", Util.join(" ⊓ ", cs));
  }
}
