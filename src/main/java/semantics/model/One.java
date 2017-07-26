package semantics.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import semantics.KnowBase;
import static semantics.KnowBase.toTaggedArray;


public class One extends Conceptual {
  private final String iri;

  public One(String iri) {
    this.iri = iri;
  }

  @Override
  public String getSignatureType() {
    return "individual";
  }

  @Override
  public String getSignatureIri() {
    return iri;
  }

  @Override
  public JsonElement toJson() {
    return toTaggedArray("O", new JsonPrimitive(iri));
  }

  @Override
  public String toString() {
    return String.format("⎨«%s»⎬", iri);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof One) {
      return iri.equals(((One) o).iri);
    }
    return false;
  }
}
