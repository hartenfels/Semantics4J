package semantics.model;

import com.google.gson.JsonElement;
import java.util.Arrays;
import semantics.KnowBase;
import semantics.Util;


public abstract class Binary extends Base implements Conceptual {
  private final Conceptual[] cs;

  public Binary(Conceptual[] cs) {
    this.cs = cs;
  }

  protected abstract Binary construct(Conceptual[] cs);

  protected abstract String getTag();
  protected abstract String getOperator();

  @Override
  public void checkSignature(KnowBase kb) {
    for (Conceptual c : cs) {
      c.checkSignature(kb);
    }
  }

  @Override
  public JsonElement toJson() {
    return Util.toTaggedArray(getTag(), Util.allToJson(cs));
  }


  @Override
  public boolean containsUnknown() {
    for (Conceptual c : cs) {
      if (c.containsUnknown()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Conceptual stripUnknownConcept() {
    if (!containsUnknown()) {
      return this;
    }

    Conceptual[] stripped = new Conceptual[cs.length];

    for (int i = 0; i < cs.length; ++i) {
      stripped[i] = cs[i].stripUnknownConcept();
    }

    return construct(stripped);
  }


  @Override
  public String toString() {
    return String.format("[%s]", Util.join(" " + getOperator() + " ", cs));
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Binary) {
      return Arrays.equals(cs, ((Binary) o).cs);
    }
    return false;
  }
}
