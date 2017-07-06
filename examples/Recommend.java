import java.util.Set;
import static semantics.Util.head;
import static semantics.Util.sorted;


public class Recommend knows "wine.rdf" {
  private static «:Wine» getWineFor(«:Winery» winery) {
    return («:Wine») head(sorted(winery.(":hasMaker"⁻)));
  }

  private static String recommendFor(«:Wine» wine) {
    switch-type (wine) {
      «:RedWine»   _ { return "\uD83C\uDF57"; } // poultry leg
      «:WhiteWine» _ { return "\uD83D\uDC1F"; } // fish
      «:RoseWine»  _ { return "\u2753";       } // question mark ornament
      default        { return "stay away!";   }
    }
  }

  public static void main(String[] args) {
    for («:Winery» winery : sorted(query-for(":Winery"))) {
      «:Wine» wine = getWineFor(winery);
      String  food = wine == null ? "not a winery at all" : recommendFor(wine);
      System.out.format("%25s: %s\n", winery.getName(), food);
    }
  }
}
