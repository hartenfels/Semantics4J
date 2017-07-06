package semantics.model;

import com.google.gson.JsonElement;
import semantics.KnowBase;


public interface DescriptionLogic {
  public String getSignatureType();
  public String getSignatureIri();

  public void checkSignature(KnowBase kb);

  public boolean isUnknown();
  public boolean containsUnknown();

  public JsonElement toJson();
}
