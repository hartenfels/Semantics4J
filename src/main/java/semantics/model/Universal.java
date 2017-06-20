package semantics.model;

import com.google.gson.JsonElement;
import semantics.Util;


public class Universal extends Quantifier {
  public Universal(Roleish r, Conceptual c) {
    super(r, c);
  }

  @Override
  protected String getTag() {
    return "A";
  }

  @Override
  protected String getPrefix() {
    return "∀";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Universal && super.equals(o);
  }
}
