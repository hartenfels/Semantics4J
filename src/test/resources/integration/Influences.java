import java.util.Set;
import static semantics.Util.names;


public class Influences from "music.rdf" {
  private static String getInfluences(«:MusicArtist» artist) {
    switch-type (artist) {
      ∃«:influencedBy»·⊤ influenceable {
        return String.join(", ", names(influenceable.(":influencedBy")));
      }
      default {
        return "nobody";
      }
    }
  }

  public static void main(String[] args) {
    for («:MusicArtist» a : query-for(":MusicArtist")) {
      System.out.format("%s influences: %s\n", a.getName(), getInfluences(a));
    }
  }
}


/*
 *> hendrix influences: beatles
 *> beatles influences: nobody
 */
