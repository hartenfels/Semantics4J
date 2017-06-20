package semantics.model;


public abstract class Conceptual implements DescriptionLogic {
  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
