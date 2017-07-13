package semantics;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;
import java.util.Set;
import semantics.err.CommunicationException;
import semantics.err.IncompatibleIndividualException;
import semantics.err.MessageException;
import semantics.err.SemanticCastException;
import semantics.model.Conceptual;
import semantics.model.Individual;
import semantics.model.Roleish;
import static java.nio.charset.StandardCharsets.UTF_8;


/**
 * Internal class, do not touch.
 *
 * This is used by Semantics4J to represent a knowledge base. This is purely
 * internal, don't use this directly.
 *
 * You can, however, set environment variables to tell Semantics4J where to
 * find Semserv. Use <code>SEMSERV_HOST</code> to specify the host (an IP
 * address or a URL, default is localhost) and <code>SEMSERV_PORT</code> for
 * the port (default is 53115). Set those before you start your process though,
 * modifying them from within your program is undefined behavior.
 */
public class KnowBase {
  public static final String DEFAULT_HOST = "localhost";
  public static final int    DEFAULT_PORT = 53115;

  private static HashMap<String, KnowBase> cache = new HashMap<>();

  private String         path;
  private Socket         socket;
  private BufferedWriter output;
  private BufferedReader input;


  private KnowBase(String path, String host, int port) {
    this.path = path;
    try {
      socket = new Socket(host, port);

      output = new BufferedWriter(
          new OutputStreamWriter(socket.getOutputStream(), UTF_8));

      input = new BufferedReader(
          new InputStreamReader(socket.getInputStream(), UTF_8));
    }
    catch (IOException e) {
      throw new CommunicationException(e);
    }
  }

  public static synchronized KnowBase of(String path) {
    KnowBase kb = cache.get(path);
    if (kb != null) {
      return kb;
    }

    String eh   = System.getenv("SEMSERV_HOST");
    String ep   = System.getenv("SEMSERV_PORT");
    String host = eh == null ? DEFAULT_HOST : eh;
    int    port = ep == null ? DEFAULT_PORT : Integer.parseInt(ep);

    kb = new KnowBase(path, host, port);
    cache.put(path, kb);
    return kb;
  }


  private synchronized JsonElement msg(String op, JsonElement arg) {
    JsonArray req = new JsonArray();
    req.add(path);
    req.add(op);
    req.add(arg);

    JsonElement res;

    try {
       output.write(req.toString() + "\n");
       output.flush();
       res = new JsonParser().parse(input.readLine());
    }
    catch (IOException e) {
      throw new CommunicationException(e);
    }

    if (res.isJsonObject()) {
      throw new MessageException(res);
    }

    return res;
  }


  private Set<Individual> toUtil(JsonElement from) {
    Set<Individual> is = new HashSet<Individual>();

    for (JsonElement e : from.getAsJsonArray()) {
      is.add(new Individual(this, e.getAsString()));
    }

    return is;
  }


  private Individual ensureCorrectSource(Individual i) {
    if (!equals(i.getKnowBase())) {
      throw new IncompatibleIndividualException(this, i);
    }
    return i;
  }


  public boolean isSatisfiable(Conceptual c) {
    return msg("satisfiable", c.toJson()).getAsBoolean();
  }

  public boolean isSubtype(Conceptual sub, Conceptual sup) {
    return msg("subtype", Util.allToJson(sub, sup)).getAsBoolean();
  }

  public boolean isMember(Conceptual c, Individual i) {
    JsonArray arg = new JsonArray();
    arg.add(c.toJson());
    arg.add(ensureCorrectSource(i).getIri());
    return msg("member", arg).getAsBoolean();
  }


  public Individual nominal(String iri) {
    checkSignature("individual", iri);
    String actualIri = msg("individual", new JsonPrimitive(iri)).getAsString();
    return new Individual(this, actualIri);
  }


  public Set<Individual> query(Conceptual c) {
    c.checkSignature(this);
    return toUtil(msg("query", c.toJson()));
  }


  public Set<Individual> project(Individual i, Roleish r) {
    r.checkSignature(this);
    JsonArray arg = new JsonArray();
    arg.add(ensureCorrectSource(i).getIri());
    arg.add(r.toJson());
    return toUtil(msg("project", arg));
  }


  public Individual cast(Conceptual c, Object o) {
    Individual i = (Individual) o;
    if (i != null && !isMember(c, i)) {
      throw new SemanticCastException(c, i);
    }
    return i;
  }

  public boolean isInstanceOf(Conceptual c, Object o) {
    return o instanceof Individual && isMember(c, (Individual) o);
  }


  public boolean hasSignature(String type, String iri) {
    JsonArray arg = new JsonArray();
    arg.add(type);
    arg.add(iri);
    return msg("signature", arg).getAsBoolean();
  }

  public void checkSignature(String type, String iri) {
    if (!hasSignature(type, iri)) {
      Logger.getGlobal().warning(
          String.format("%s '%s' not in signature of '%s'", type, iri, path));
    }
  }


  @Override
  public String toString() {
    return String.format("KnowBase[%s]", path);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof KnowBase) {
      return path.equals(((KnowBase) other).path);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return path.hashCode();
  }
}
