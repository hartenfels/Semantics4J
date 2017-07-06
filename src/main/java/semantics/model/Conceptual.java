package semantics.model;


public interface Conceptual extends DescriptionLogic {
  default Conceptual stripUnknownConcept() {
    return this;
  }
}
