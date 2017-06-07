import java.util.Set;
import static semantics.Individuals.names;


public class Influences knows "music.rdf" {
  private static String getInfluences(«:MusicArtist» artist) {
    switch-type (artist) {
      ∃«:influencedBy» ⇒ ⊤ influenceable {
        return String.join(", ", names(influenceable.«:influencedBy»));
      }
      default {
        return "nobody";
      }
    }
  }

  public static void main(String[] args) {
    for («:MusicArtist» a : do-query(":MusicArtist")) {
      System.out.format("%s influences: %s\n", a.getName(), getInfluences(a));
    }
  }
}
