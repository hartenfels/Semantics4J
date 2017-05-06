package semantics.model;

import com.google.gson.JsonElement;
import java.util.Arrays;
import semantics.Util;


public class Intersection extends Conceptual {
  private final Conceptual[] cs;

  public Intersection(Conceptual... cs) {
    this.cs = cs;
  }

  public JsonElement toJson() {
    return Util.toTaggedArray("I", Util.allToJson(cs));
  }

  @Override
  public String toString() {
    return String.format("｢%s｣", Util.join(" ⊓ ", cs));
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Existence) {
      return Arrays.equals(cs, ((Intersection) o).cs);
    }
    return false;
  }
}
