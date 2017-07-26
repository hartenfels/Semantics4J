package semantics.model;

import com.google.gson.JsonElement;
import semantics.KnowBase;


public abstract class DescriptionLogic {
  public String getSignatureType() {
    return null;
  }

  public String getSignatureIri() {
    return null;
  }

  public void checkSignature(KnowBase kb) {
    String type = getSignatureType();
    String iri  = getSignatureIri();
    if (type != null && iri != null) {
      kb.checkSignature(type, iri);
    }
  }

  public boolean isUnknown() {
    return false;
  }

  public boolean containsUnknown() {
    return isUnknown();
  }


  public abstract JsonElement toJson();


  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
