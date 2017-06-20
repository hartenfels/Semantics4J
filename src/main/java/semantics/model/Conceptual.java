package semantics.model;


public abstract class Conceptual extends DescriptionLogic {
  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
