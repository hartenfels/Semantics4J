import java.util.List;
import semantics.model.Individual;
import static semantics.Util.head;
import static semantics.Util.names;
import static semantics.Util.sorted;


public class Modify from "music.rdf" {
  private static void printArtists() {
    for («:MusicArtist» artist : sorted(query-for(":MusicArtist"))) {
      System.out.print(artist.getName());

      List<String> influences = sorted(names(query-for((∃":influencedBy"⁻·artist))));
      if (!influences.isEmpty()) {
        System.out.print(" | influenced by: " + String.join(", ", influences));
      }

      System.out.println();

      for («:Song» song : sorted(query-for(":Song" ⊓ ∃":recorded"⁻·artist))) {
        System.out.println(" * " + song.getName());
      }
    }
  }

  private static Individual insert(String iri, String concept) {
    return new Individual("music.rdf", iri).insert(concept);
  }


  public static void main(String[] args) {
    System.out.println("—————————————————————————————");
    printArtists();
    System.out.println("—————————————————————————————");

    head(query-for(⎨":beatles"⎬))
      .learn(":recorded", insert(":yellowSubmarine",          ":Song"))
      .learn(":recorded", insert(":lucyInTheSkyWithDiamonds", ":Song"));

    insert(":drone", ":MusicGroup")
      .learn(":influencedBy", ":pantera")
      .learn(":influencedBy", ":machineHead")
      .learn(":recorded", insert(":boneless",     ":Song"))
      .learn(":recorded", insert(":guilt",        ":Song"))
      .learn(":recorded", insert(":intoDarkness", ":Song"));

    printArtists();
    System.out.println("—————————————————————————————");
  }
}
