package semantics.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import semantics.KnowBase;
import semantics.Util;


public class Inversion extends Roleish {
  private final Roleish r;

  public Inversion(Roleish r) {
    this.r = r;
  }

  @Override
  public void checkSignature(KnowBase kb) {
    r.checkSignature(kb);
  }

  @Override
  public JsonElement toJson() {
    return Util.toTaggedArray("i", r.toJson());
  }

  @Override
  public boolean containsUnknown() {
    return r.containsUnknown();
  }

  @Override
  public String toString() {
    return String.format("%s⁻", r);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Inversion) {
      return r.equals(((Inversion) o).r);
    }
    return false;
  }
}
