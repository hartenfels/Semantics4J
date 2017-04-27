package semantics.err;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class MessageException extends SemanticsException {
  public MessageException(JsonElement je) {
    super(new GsonBuilder().setPrettyPrinting().create().toJson(je));
  }
}
