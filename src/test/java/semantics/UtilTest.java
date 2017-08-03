package semantics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;
import semantics.KnowBase;
import semantics.model.Concept;
import semantics.model.Individual;
import semantics.model.Role;
import static org.junit.Assert.*;
import static semantics.Util.*;


public class UtilTest {
  private static KnowBase   kb;
  private static Individual beatles;
  private static Individual hendrix;

  @BeforeClass
  public static void setUpClass() {
    kb      = KnowBase.of("music.rdf");
    beatles = new Individual("music.rdf", "http://example.org/music#beatles");
    hendrix = new Individual("music.rdf", "http://example.org/music#hendrix");
  }


  @Test
  public void testConcept() {
    assertEquals(new Concept(":Wine"), concept(":Wine"));
  }

  @Test
  public void testRole() {
    assertEquals(new Role(":hasColor"), role(":hasColor"));
  }


  @Test
  public void testIris() {
    Set<Individual> artists = kb.query(concept(":MusicArtist"));

    Set<String> expectedSet = new HashSet<>();
    expectedSet.add("http://example.org/music#beatles");
    expectedSet.add("http://example.org/music#hendrix");
    assertEquals(expectedSet, iris(artists));

    List<String> list = sorted(iris(new ArrayList<Individual>(artists)));
    assertEquals(2, list.size());
    assertEquals("http://example.org/music#beatles", list.get(0));
    assertEquals("http://example.org/music#hendrix", list.get(1));
  }


  @Test
  public void testNames() {
    Set<Individual> artists = kb.query(concept(":MusicArtist"));

    Set<String> expectedSet = new HashSet<>();
    expectedSet.add("beatles");
    expectedSet.add("hendrix");
    assertEquals(expectedSet, names(artists));

    List<String> list = sorted(names(new ArrayList<Individual>(artists)));
    assertEquals(2, list.size());
    assertEquals("beatles", list.get(0));
    assertEquals("hendrix", list.get(1));
  }


  @Test
  public void testSorted() {
    List<Individual> artists = sorted(kb.query(concept(":MusicArtist")));
    assertEquals(2,       artists.size());
    assertEquals(beatles, artists.get(0));
    assertEquals(hendrix, artists.get(1));
  }


  @Test
  public void testHead() {
    List<String> list = new ArrayList<>();
    Set<String>  set  = new HashSet<>();

    assertEquals(null,    head(list));
    assertEquals(null,    head(set));
    assertEquals(null,    head(list, null));
    assertEquals(null,    head(set,  null));
    assertEquals("dummy", head(list, "dummy"));
    assertEquals("dummy", head(set,  "dummy"));

    list.add("hello");
    list.add("world");
    set .add("hello");

    assertEquals("hello", head(list));
    assertEquals("hello", head(set));
    assertEquals("hello", head(list, null));
    assertEquals("hello", head(set,  null));
    assertEquals("hello", head(list, "dummy"));
    assertEquals("hello", head(set,  "dummy"));

    list.remove(0);
    assertEquals("world", head(list));
    assertEquals("world", head(list, null));
    assertEquals("world", head(list, "dummy"));

    list.add(0, null);
    assertEquals(null, head(list));
    assertEquals(null, head(list, null));
    assertEquals(null, head(list, "dummy"));
  }
}
