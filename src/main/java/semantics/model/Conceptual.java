package semantics.model;


public abstract class Conceptual extends DescriptionLogic {
  public Conceptual stripUnknown() {
    return this;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
