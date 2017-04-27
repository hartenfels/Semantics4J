package semantics.model;

import com.google.gson.JsonElement;
import semantics.Util;


public class Negation extends Conceptual {
  private final Conceptual c;

  public Negation(Conceptual c) {
    this.c = c;
  }

  public JsonElement toJson() {
    return Util.toTaggedArray("N", c.toJson());
  }

  @Override
  public String toString() {
    return String.format("¬%s", c);
  }
}
