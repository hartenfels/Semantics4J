package semantics.model;


public abstract class Roleish extends DescriptionLogic {
  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
