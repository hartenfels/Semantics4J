package semantics.model;


public abstract class Roleish implements DescriptionLogic {
  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
