package semantics.model;

import com.google.gson.JsonElement;
import semantics.KnowBase;
import semantics.Util;


public abstract class Quantifier extends Conceptual {
  private final Roleish    r;
  private final Conceptual c;

  public Quantifier(Roleish r, Conceptual c) {
    this.r = r;
    this.c = c;
  }

  protected abstract String getTag();
  protected abstract String getPrefix();

  public void checkSignature(KnowBase kb) {
    r.checkSignature(kb);
    c.checkSignature(kb);
  }

  public JsonElement toJson() {
    return Util.toTaggedArray(getTag(), r.toJson(), c.toJson());
  }

  @Override
  public String toString() {
    return String.format("%s%s·%s", getPrefix(), r, c);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Quantifier) {
      Quantifier q = (Quantifier) o;
      return r.equals(q.r) && c.equals(q.c);
    }
    return false;
  }
}