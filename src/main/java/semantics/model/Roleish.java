package semantics.model;


public abstract class Roleish extends DescriptionLogic {
  public Roleish stripUnknown() {
    return this;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
