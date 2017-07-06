package semantics;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.BeforeClass;
import org.junit.Test;
import semantics.KnowBase;
import semantics.model.*;
import static org.junit.Assert.*;
import static semantics.Util.iris;


public class KnowBaseTest {
  private static KnowBase kb;

  @BeforeClass
  public static void setUpClass() {
    kb = KnowBase.of("music.rdf");
  }


  @Test
  public void cache() {
    KnowBase music = KnowBase.of("music.rdf");
    assertSame(kb, music);
    assertEquals(kb, music);

    KnowBase wine = KnowBase.of("wine.rdf");
    assertNotSame(kb, wine);
    assertNotEquals(kb, wine);
  }


  private static void isSub(boolean expected, Conceptual sup, Conceptual sub) {
    assertEquals(expected, kb.isSubtype(sup, sub));
  }

  @Test
  public void subtype() {
    isSub(true,  new Concept(":MusicArtist"), new Concept(":MusicArtist"));
    isSub(true,  new One    (":hendrix"    ), new Concept(":MusicArtist"));
    isSub(false, new Concept(":MusicArtist"), new One    (":hendrix"    ));
  }


  private static void isMember(boolean expected, Conceptual c, String iri) {
    assertEquals(expected, kb.isMember(c, kb.nominal(iri)));
  }

  @Test
  public void member() {
    isMember(true, new Concept(":MusicArtist"), ":beatles");
    isMember(true, new Concept(":MusicArtist"), ":hendrix");

    Conceptual inf = new Existence(new Role(":influencedBy"), new Top());
    isMember(false, inf, ":beatles");
    isMember(true,  inf, ":hendrix");
  }


  private static void nominalIs(String expected, String actual) {
    assertEquals(expected, kb.nominal(actual).getIri());
  }

  @Test
  public void nominal() {
    nominalIs("http://example.org/music#hendrix", ":hendrix");
    nominalIs("http://example.org/music#hendrix", "http://example.org/music#hendrix");
    nominalIs("what:ever", "what:ever");
    nominalIs("http://example.org/what#ever", "http://example.org/what#ever");
  }


  private static Set<String> unabbreviate(String[] abbreviated) {
    return Arrays.stream(abbreviated)
      .map(iri -> "http://example.org/music#" + iri.replaceFirst("^:", ""))
      .collect(Collectors.toSet());
  }

  private static void queryIs(Conceptual c, String... abbreviated) {
    assertTrue(kb.isSatisfiable(c));
    assertEquals(unabbreviate(abbreviated), iris(kb.query(c)));
  }

  @Test
  public void everythingQuery() {
    queryIs(new Top(), ":beatles", ":coolFm", ":hendrix", ":machineGun");
  }

  @Test
  public void conceptQuery() {
    queryIs(new Concept(":MusicArtist"), ":beatles", ":hendrix");
  }

  @Test
  public void nestedQuery() {
    Conceptual inf = new Existence(new Role(":influencedBy"), new Top());
    queryIs(inf, ":hendrix");
  }

  @Test
  public void unsatisfiableQuery() {
    Conceptual bottom = new Bottom();
    assertFalse(kb.isSatisfiable(bottom));
    assertTrue(kb.query(bottom).isEmpty());
  }


  private static void projectIs(Individual i, Roleish r, String... abbreviated) {
    assertEquals(unabbreviate(abbreviated), iris(i.project(r)));
  }

  @Test
  public void project() {
    projectIs(kb.nominal(":beatles"), new Role(":influencedBy"));
    projectIs(kb.nominal(":hendrix"), new Role(":influencedBy"), ":beatles");
  }
}
