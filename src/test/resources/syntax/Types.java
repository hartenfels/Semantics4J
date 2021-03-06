import java.util.Set;

public class Types from "music.rdf" {
  public static void genericTypes() {
    Set<«:MusicArtist»> s1 = query-for(":MusicArtist");
    Set<«:MusicArtist» ⊔ «:RadioStation»> s2 = query-for(":MusicArtist" ⊔ ":RadioStation");
    Set<«:RadioStation» ⊔ «:MusicArtist»> s3 = query-for(":MusicArtist" ⊔ ":RadioStation");
  }

  public static void wildcardTypes() {
    Set<? extends «:MusicArtist»> s1 = query-for(":MusicArtist");
    Set<? extends «:MusicArtist» ⊔ «:RadioStation»> s2 = query-for(":MusicArtist" ⊔ ":RadioStation");
    Set<? extends «:RadioStation» ⊔ «:MusicArtist»> s3 = query-for(":MusicArtist" ⊔ ":RadioStation");
  }
}
