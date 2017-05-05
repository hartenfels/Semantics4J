package semantics.model;

import com.google.gson.JsonElement;
import semantics.Util;


public class Universal extends Conceptual {
  private final Roleish    r;
  private final Conceptual c;

  public Universal(Roleish r, Conceptual c) {
    this.r = r;
    this.c = c;
  }

  public JsonElement toJson() {
    return Util.toTaggedArray("A", r.toJson(), c.toJson());
  }

  @Override
  public String toString() {
    return String.format("∀%s.%s", r, c);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Universal) {
      Universal u = (Universal) o;
      return r.equals(u.r) && c.equals(u.c);
    }
    return false;
  }
}
