package semantics;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;
import semantics.err.UnsatisfiableQueryException;
import semantics.Individuals;
import semantics.KnowBase;
import semantics.model.*;
import static org.junit.Assert.*;


public class KnowBaseTest {
  private static KnowBase kb;

  @BeforeClass
  public static void setUpClass() {
    kb = KnowBase.of("share/music.rdf");
  }


  @Test
  public void cache() {
    KnowBase music = KnowBase.of("share/music.rdf");
    assertSame(kb, music);
    assertEquals(kb, music);

    KnowBase wine = KnowBase.of("share/wine.rdf");
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

    Conceptual inf = new Existence(new Role(":influencedBy"), new Everything());
    isMember(false, inf, ":beatles");
    isMember(true,  inf, ":hendrix");
  }


  private static void nominalIs(String expected, String actual) {
    assertEquals(expected, kb.nominal(actual).getIri());
  }

  @Test
  public void nominal() {
    nominalIs(":hendrix", ":hendrix");
    nominalIs(":hendrix", "http://example.org/music#hendrix");
    nominalIs("<what:ever>", "what:ever");
    nominalIs("<http://example.org/what#ever>", "http://example.org/what#ever");
  }


  private static void queryIs(Conceptual c, String... iris) {
    assertTrue(kb.isSatisfiable(c));
    assertEquals(new HashSet<>(Arrays.asList(iris)), kb.query(c).getIris());
  }

  @Test
  public void everythingQuery() {
    queryIs(new Everything(), ":beatles", ":coolFm", ":hendrix", ":machineGun");
  }

  @Test
  public void conceptQuery() {
    queryIs(new Concept(":MusicArtist"), ":beatles", ":hendrix");
  }

  @Test
  public void nestedQuery() {
    Conceptual inf = new Existence(new Role(":influencedBy"), new Everything());
    queryIs(inf, ":hendrix");
  }

  @Test(expected = UnsatisfiableQueryException.class)
  public void unsatisfiableQuery() {
    Conceptual nothing = new Nothing();
    assertFalse(kb.isSatisfiable(nothing));
    kb.query(nothing);
  }


  private static void projectIs(Individual i, Roleish r, String... iris) {
    assertEquals(new HashSet<>(Arrays.asList(iris)), i.project(r).getIris());
  }

  @Test
  public void project() {
    projectIs(kb.nominal(":beatles"), new Role(":influencedBy"));
    projectIs(kb.nominal(":hendrix"), new Role(":influencedBy"), ":beatles");
  }
}
