package semantics.model;

import com.google.gson.JsonElement;
import semantics.Util;


public class Existence extends Conceptual {
  private final Roleish    r;
  private final Conceptual c;

  public Existence(Roleish r, Conceptual c) {
    this.r = r;
    this.c = c;
  }

  public JsonElement toJson() {
    return Util.toTaggedArray("E", r.toJson(), c.toJson());
  }

  @Override
  public String toString() {
    return String.format("∃%s.%s", r, c);
  }
}
