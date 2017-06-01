import java.util.Set;
import semantics.model.Individual;


public class Unsatisfiable knows "music.rdf" {
  public static void main(String[] args) {
    Set<? extends Individual> set1 = do-query(⊥);
    Set<? extends Individual> set2 = do-query((⊥));
    Set<? extends Individual> set3 = do-query(":MusicArtist" ⊓ ⊥);
    Set<? extends Individual> set4 = do-query(":MusicArtist" ⊔ ⊥);
    Set<? extends Individual> set5 = do-query(⊥ ⊓ ∃":influencedBy" ⇒ ⎨":beatles"⎬);
    Set<? extends Individual> set6 = do-query((⊥) ⊓ (∃(":influencedBy") ⇒ (⎨":beatles"⎬)));
  }
}


/*
 *! src/test/resources/integration/Unsatisfiable.java:7: error: constant query is unsatisfiable
 *! src/test/resources/integration/Unsatisfiable.java:8: error: constant query is unsatisfiable
 *! src/test/resources/integration/Unsatisfiable.java:9: error: constant query is unsatisfiable
 *! src/test/resources/integration/Unsatisfiable.java:11: error: constant query is unsatisfiable
 *! src/test/resources/integration/Unsatisfiable.java:12: error: constant query is unsatisfiable
 */
