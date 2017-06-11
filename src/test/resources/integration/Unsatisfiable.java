import java.util.Set;
import semantics.model.Individual;


public class Unsatisfiable knows "music.rdf" {
  public static void main(String[] args) {
    Set<? extends Individual> set1 = query-for(⊥);
    Set<? extends Individual> set2 = query-for((⊥));
    Set<? extends Individual> set3 = query-for(":MusicArtist" ⊓ ⊥);
    Set<? extends Individual> set4 = query-for(":MusicArtist" ⊔ ⊥);
    Set<? extends Individual> set5 = query-for(⊥ ⊓ ∃":influencedBy" ⇒ ⎨":beatles"⎬);
    Set<? extends Individual> set6 = query-for((⊥) ⊓ (∃(":influencedBy") ⇒ (⎨":beatles"⎬)));
  }
}


/*
 *! src/test/resources/integration/Unsatisfiable.java:7: error: constant query is unsatisfiable
 *! src/test/resources/integration/Unsatisfiable.java:8: error: constant query is unsatisfiable
 *! src/test/resources/integration/Unsatisfiable.java:9: error: constant query is unsatisfiable
 *! src/test/resources/integration/Unsatisfiable.java:11: error: constant query is unsatisfiable
 *! src/test/resources/integration/Unsatisfiable.java:12: error: constant query is unsatisfiable
 */
