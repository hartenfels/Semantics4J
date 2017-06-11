import java.util.HashSet;
import java.util.Set;
import static semantics.Individuals.names;
import static semantics.Individuals.sorted;


public class Wineries knows "wine.rdf" {
  private static Set<«:Winery»> toMaker(Set<? extends «:Wine»> wines) {
    Set<«:Winery»> wineries = new HashSet<>();

    for («:Wine» wine : wines) {
      for (∃«:hasMaker»⁻ ⇒ «:Wine» maker : wine.«:hasMaker») {
        wineries.add(maker);
      }
    }

    return wineries;
  }


  public static void main(String[] args) {
    Set<«:Winery»> makers = toMaker(query-for(":RedWine" ⊓ ":DryWine"));
    System.out.println(String.join("\n", names(sorted(makers))));
  }
}
