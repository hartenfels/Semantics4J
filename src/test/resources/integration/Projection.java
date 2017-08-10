import java.util.Set;
import semantics.model.Individual;


public class Projection from "wine.rdf" {
  private static Set<∃«:hasColor»⁻·⊤> testObject(Object o) {
    return o.(":hasColor");
  }

  private static Set<∃«:hasColor»⁻·⊤> testIndividual(Individual ind) {
    return ind.(":hasColor");
  }

  private static Set<∃«:hasColor»⁻·⊤> testBadConcept(⊤ top) {
    return top.(":hasColor");
  }

  private static Set<∃«:hasGlassShape»⁻·«:Wine»> testBadRole(«:Wine» wine) {
    return wine.(":hasGlassShape");
  }

  private static Set<∃«:hasColor»⁻·«:Wine»> testProjection(«:Wine» wine) {
    return wine.(":hasColor");
  }
}

/*
 *! src/test/resources/integration/Projection.java:7: error: '(unknown concept)' is not a subtype of '∃«:hasColor»·⊤'
 *! src/test/resources/integration/Projection.java:11: error: '(unknown concept)' is not a subtype of '∃«:hasColor»·⊤'
 *! src/test/resources/integration/Projection.java:15: error: '⊤' is not a subtype of '∃«:hasColor»·⊤'
 *! src/test/resources/integration/Projection.java:19: error: '«:Wine»' is not a subtype of '∃«:hasGlassShape»·⊤'
 */
