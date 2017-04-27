package semantics.model;

import org.junit.Test;
import semantics.model.Individual;
import static org.junit.Assert.*;


public class IndividualTest {
  private static void nameIs(String expected, String iri) {
    assertEquals(expected, new Individual(null, iri).getName());
  }

  @Test
  public void getName() {
    nameIs("hendrix", ":hendrix");
    nameIs("ever",    "<what:ever>");
    nameIs("ever",    "<http://example.org/what#ever>");
  }
}
