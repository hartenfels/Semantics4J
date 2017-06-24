package semantics.model;


public class Universal extends Quantifier {
  public Universal(Roleish r, Conceptual c) {
    super(r, c);
  }

  @Override
  protected Quantifier construct(Roleish r, Conceptual c) {
    return new Universal(r, c);
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
