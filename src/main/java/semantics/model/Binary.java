package semantics.model;

import com.google.gson.JsonElement;
import java.util.Arrays;
import semantics.KnowBase;
import semantics.Util;


public abstract class Binary extends Conceptual {
  private final Conceptual[] cs;

  public Binary(Conceptual[] cs) {
    this.cs = cs;
  }

  protected abstract String getTag();
  protected abstract String getOperator();

  public void checkSignature(KnowBase kb) {
    for (Conceptual c : cs) {
      c.checkSignature(kb);
    }
  }

  public JsonElement toJson() {
    return Util.toTaggedArray(getTag(), Util.allToJson(cs));
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