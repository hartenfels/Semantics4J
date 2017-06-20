import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.IntStream;
import semantics.model.Concept;
import semantics.model.Conceptual;
import semantics.model.Individual;
import spark.Request;
import spark.Response;
import static semantics.Util.head;
import static semantics.Util.names;
import static semantics.Util.sorted;
import static spark.Spark.*;


public class WineSearch knows "wine.rdf" {
  private static List<String> getCriterion(String concept) {
    return sorted(names(query-for(concept)));
  }

  private static Set<«:Region»> getTopLevelRegions() {
    Set<«:Region»> regions = query-for(":Region");
    regions.removeAll(query-for(∃":locatedIn"·":Region"));
    return regions;
  }

  private static Map<String, List<String>> criteria(Request  req, Response res) {
    Map<String, List<String>> criteria = new HashMap<>();

    criteria.put("body",   getCriterion(":WineBody"  ));
    criteria.put("color",  getCriterion(":WineColor" ));
    criteria.put("flavor", getCriterion(":WineFlavor"));
    criteria.put("sugar" , getCriterion(":WineSugar" ));
    criteria.put("maker" , getCriterion(":Winery"    ));
    criteria.put("region", sorted(names(getTopLevelRegions())));

    return criteria;
  }


  private static String ucfirst(String s) {
    return s.substring(0, 1).toUpperCase() + s.substring(1);
  }

  private static List<Individual> wines(Request req, Response res) {
    Conceptual dl = new Concept(":Wine");

    for (String key : "body color flavor maker region sugar".split("\\s+")) {
      String role  = key.equals("region") ? ":locatedIn" : ":has" + ucfirst(key);
      String param = req.queryParams(key);

      if (param != null) {
        String[]   parts = param.split(",");
        Conceptual union = ∃role·⎨":" + parts[0]⎬;

        for (int i = 1; i < parts.length; ++i) {
          union ⊔= ∃role·⎨":" + parts[i]⎬;
        }

        dl ⊓= union;
      }
    }

    return sorted(query-for(dl));
  }


  private static String coalesce(Set<? extends Individual> set, String dft) {
    return set.isEmpty() ? dft : head(set).getName();
  }

  private static Map<String, String> wine(Request req, Response res) {
    String  iri = ":" + req.params(":wine");
    «:Wine» wine;

    try {
      wine = («:Wine») head(query-for(⎨iri⎬));
    }
    catch (ClassCastException | NoSuchElementException e) {
      res.status(404);
      return null;
    }

    Map<String, String> wineInfo = new HashMap<>();

    wineInfo.put("body",   coalesce(wine.«:hasBody»,   "Incorporeal"));
    wineInfo.put("color",  coalesce(wine.«:hasColor»,  "Colorless"  ));
    wineInfo.put("flavor", coalesce(wine.«:hasFlavor», "Flavorless" ));
    wineInfo.put("maker",  coalesce(wine.«:hasMaker»,  "Nobody"     ));
    wineInfo.put("region", coalesce(wine.«:locatedIn», "Æther"      ));
    wineInfo.put("sugar",  coalesce(wine.«:hasSugar»,  "Tasteless"  ));

    return wineInfo;
  }


  private static Gson buildGson() {
    JsonSerializer<Individual> serializer = (src, type, context) -> {
      return new JsonPrimitive(src.getName());
    };

    return new GsonBuilder()
        .registerTypeAdapter(Individual.class, serializer)
        .create();
  }

  public static void main(String[] args) {
    port(3000);
    Gson gson = buildGson();

    get("/criteria",    WineSearch::criteria, gson::toJson);
    get("/wines",       WineSearch::wines,    gson::toJson);
    get("/wines/:wine", WineSearch::wine,     gson::toJson);

    after((req, res) -> {
      res.type("application/json");
      res.header("Access-Control-Allow-Origin", "*");
      res.header("Access-Control-Allow-Methods", "GET, OPTIONS, POST");
    });
  }
}
