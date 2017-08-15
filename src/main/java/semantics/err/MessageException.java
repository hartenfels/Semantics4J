package semantics.err;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;


/**
 * When you see an exception like this, file a bug against Semantics4J.
 *
 * This should never actually occur, but when it does, it points to a bug in
 * Semantics4J's serialization or semserv itself. If there's a schema error,
 * Semantics4J didn't serialize outoing data correctly, which isn't supposed to
 * happen. If there's a different error, semserv crashed for some reason, which
 * isn't supposed to happen either.
 */
public class MessageException extends SemanticsException {
  public MessageException(JsonElement je) {
    super(new GsonBuilder().setPrettyPrinting().create().toJson(je));
  }
}
