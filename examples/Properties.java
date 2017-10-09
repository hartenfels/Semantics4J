import java.util.ArrayList;
import java.util.List;
import static semantics.Util.sorted;


public class Properties from "music.rdf" {
  private static String getSongReleaseYears(«:Song» song) {
    List<String> years = new ArrayList<>();

    // No type inference yet, so everything's just an Object.
    for (Object prop : song.props(":released")) {
      // But the values do have the correct type.
      Integer year = (Integer) prop;
      years.add(year.toString());
    }

    return String.join(", ", years);
  }

  public static void main(String[] args) {
    for («:MusicArtist» artist : sorted(query-for(":MusicArtist"))) {

      // Print artist's name from its datatype property.
      System.out.println(artist.prop(":name"));

      // And all their recorded songs with the years they were released.
      for («:Song» song : sorted(artist.(":recorded"))) {
        String name  = (String) song.prop(":name");
        String years = getSongReleaseYears(song);
        System.out.println(" - " + name + " (" + years + ")");
      }
    }
  }
}
