public class Cast from "music.rdf" {
  public static void test() {
    for («:MusicArtist» artist : query-for(":MusicArtist")) {
      ∃«:influencedBy»·⊤ i0 = (∃«:influencedBy»·⊤) artist;
      ∃«:influencedBy»·⊤ i1 = (∃«:influencedBy»·⊤) (artist);
      ∃«:influencedBy»·⊤ i2 = ((∃«:influencedBy»·⊤) (artist));
    }
  }
}
