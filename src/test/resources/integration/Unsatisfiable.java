import java.util.Set;
import semantics.model.Individual;


public class Unsatisfiable from "music.rdf" {
  public static void main(String[] args) {
    Set<? extends Individual> set1 = query-for(⊥);
    Set<? extends Individual> set2 = query-for((⊥));
    Set<? extends Individual> set3 = query-for(":MusicArtist" ⊓ ⊥);
    Set<? extends Individual> set4 = query-for(":MusicArtist" ⊔ ⊥);
    Set<? extends Individual> set5 = query-for(⊥ ⊓ ∃":influencedBy"·⎨":beatles"⎬);
    Set<? extends Individual> set6 = query-for((⊥) ⊓ (∃(":influencedBy")·(⎨":beatles"⎬)));
  }
}


/*
 *! src/test/resources/integration/Unsatisfiable.java:7: error: query-for ⊥ is unsatisfiable
 *! src/test/resources/integration/Unsatisfiable.java:8: error: query-for ⊥ is unsatisfiable
 *! src/test/resources/integration/Unsatisfiable.java:9: error: query-for [«:MusicArtist» ⊓ ⊥] is unsatisfiable
 *! src/test/resources/integration/Unsatisfiable.java:11: error: query-for [⊥ ⊓ ∃«:influencedBy»·⎨«:beatles»⎬] is unsatisfiable
 *! src/test/resources/integration/Unsatisfiable.java:12: error: query-for [⊥ ⊓ ∃«:influencedBy»·⎨«:beatles»⎬] is unsatisfiable
 */
