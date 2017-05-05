package semantics.model;


public abstract class Conceptual implements Jsonable {
  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
