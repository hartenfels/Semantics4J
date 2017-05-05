package semantics.model;


public abstract class Roleish implements Jsonable {
  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
