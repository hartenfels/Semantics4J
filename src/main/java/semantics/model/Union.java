package semantics.model;


public class Union extends Binary {
  public Union(Conceptual... cs) {
    super(cs);
  }

  @Override
  protected String getTag() {
    return "U";
  }

  @Override
  protected String getOperator() {
    return "⊔";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Union && super.equals(o);
  }
}
