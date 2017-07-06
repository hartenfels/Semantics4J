package semantics.model;


public class Existence extends Quantifier {
  public Existence(Roleish r, Conceptual c) {
    super(r, c);
  }

  @Override
  protected Quantifier construct(Roleish r, Conceptual c) {
    return new Existence(r, c);
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
  protected Roleish getUnknownRoleValue() {
    return new TopRole();
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Existence && super.equals(o);
  }
}
