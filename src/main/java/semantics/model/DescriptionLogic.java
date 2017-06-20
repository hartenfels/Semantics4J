package semantics.model;

import com.google.gson.JsonElement;
import semantics.KnowBase;


public interface DescriptionLogic {
  public void checkSignature(KnowBase kb);
  public JsonElement toJson();
}
