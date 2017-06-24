package semantics.model;

import com.google.gson.JsonElement;
import semantics.KnowBase;
import semantics.Util;


public class Negation extends Conceptual {
  private final Conceptual c;

  public Negation(Conceptual c) {
    this.c = c;
  }

  @Override
  public void checkSignature(KnowBase kb) {
    c.checkSignature(kb);
  }

  @Override
  public JsonElement toJson() {
    return Util.toTaggedArray("N", c.toJson());
  }

  @Override
  public boolean containsUnknown() {
    return c.containsUnknown();
  }

  @Override
  public Conceptual stripUnknown() {
    return containsUnknown() ? new Everything() : this;
  }

  @Override
  public String toString() {
    return String.format("¬%s", c);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Negation) {
      return c.equals(((Negation) o).c);
    }
    return false;
  }
}
