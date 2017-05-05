package semantics.model;

import com.google.gson.JsonElement;
import java.util.Arrays;
import semantics.Util;


public class Union extends Conceptual {
  private final Conceptual[] cs;

  public Union(Conceptual... cs) {
    this.cs = cs;
  }

  public JsonElement toJson() {
    return Util.toTaggedArray("U", Util.allToJson(cs));
  }

  @Override
  public String toString() {
    return String.format("(%s)", Util.join(" ⊔ ", cs));
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Existence) {
      return Arrays.equals(cs, ((Union) o).cs);
    }
    return false;
  }
}
