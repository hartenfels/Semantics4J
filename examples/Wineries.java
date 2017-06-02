import java.util.HashSet;
import java.util.Set;
import semantics.model.Individual;
import static semantics.Individuals.getNames;
import static semantics.Individuals.getSorted;


public class Wineries knows "wine.rdf" {
  private static Set<«:Winery»> toMaker(Set<? extends «:Wine»> wines) {
    Set<«:Winery»> wineries = new HashSet<>();

    for («:Wine» wine : wines) {
      for (Individual maker : wine.«:hasMaker») {
        wineries.add((«:Winery») maker);
      }
    }

    return wineries;
  }


  public static void main(String[] args) {
    Set<«:Winery»> makers = toMaker(do-query(":RedWine" ⊓ ":DryWine"));
    System.out.println(String.join("\n", getNames(getSorted(makers))));
  }
}
