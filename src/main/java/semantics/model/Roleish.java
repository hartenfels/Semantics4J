package semantics.model;


public interface Roleish extends DescriptionLogic {
  default Roleish stripUnknownRole() {
    return this;
  }
}
