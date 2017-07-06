package semantics.model;

import com.google.gson.JsonElement;
import semantics.KnowBase;


public abstract class Base implements DescriptionLogic {
  @Override
  public String getSignatureType() {
    return null;
  }

  @Override
  public String getSignatureIri() {
    return null;
  }

  @Override
  public void checkSignature(KnowBase kb) {
    String type = getSignatureType();
    String iri  = getSignatureIri();
    if (type != null && iri != null) {
      kb.checkSignature(type, iri);
    }
  }

  @Override
  public boolean isUnknown() {
    return false;
  }

  @Override
  public boolean containsUnknown() {
    return isUnknown();
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
