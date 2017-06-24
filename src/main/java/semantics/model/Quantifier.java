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

  protected abstract Quantifier construct(Roleish r, Conceptual c);

  protected abstract String getTag();
  protected abstract String getPrefix();

  @Override
  public void checkSignature(KnowBase kb) {
    r.checkSignature(kb);
    c.checkSignature(kb);
  }

  @Override
  public JsonElement toJson() {
    return Util.toTaggedArray(getTag(), r.toJson(), c.toJson());
  }


  @Override
  public boolean containsUnknown() {
    return c.containsUnknown() || r.containsUnknown();
  }

  @Override
  public Conceptual stripUnknown() {
    return r.containsUnknown()
         ? new Everything()
         : construct(r, c.stripUnknown());
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
