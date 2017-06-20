package semantics.model;


public class Existence extends Quantifier {
  public Existence(Roleish r, Conceptual c) {
    super(r, c);
  }

  @Override
  protected String getTag() {
    return "E";
  }

  @Override
  protected String getPrefix() {
    return "∃";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Existence && super.equals(o);
  }
}
