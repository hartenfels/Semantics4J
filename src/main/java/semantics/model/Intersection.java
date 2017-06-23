package semantics.model;


public class Intersection extends Binary {
  public Intersection(Conceptual... cs) {
    super(cs);
  }

  @Override
  protected Binary construct(Conceptual[] cs) {
    return new Intersection(cs);
  }

  @Override
  protected String getTag() {
    return "I";
  }

  @Override
  protected String getOperator() {
    return "⊓";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Intersection && super.equals(o);
  }
}
