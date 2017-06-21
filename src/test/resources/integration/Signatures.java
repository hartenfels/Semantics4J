import java.util.Set;
import semantics.model.Individual;
import static semantics.Util.concept;
import static semantics.Util.role;


public class Signatures knows "music.rdf" {
  private static void typeSignature(∃«roleless»⁻·⎨«noOne»⎬ arg) {}

  public static void main(String[] args) {
    // Remove the date from the logger output so that it can be tested.
    System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

    concept("nonexistent");
    role("nonexistent");

    System.out.println("atom: "   + query-for(":Politician").size());
    System.out.println("one: "    + query-for(⎨":Skeletonwitch"⎬).size());
    System.out.println("exists: " + query-for(∃":bribedBy"·":Company").size());
  }
}


/*
 *! src/test/resources/integration/Signatures.java:8: warning: role 'roleless' not in signature of 'music.rdf'
 *! src/test/resources/integration/Signatures.java:8: warning: individual 'noOne' not in signature of 'music.rdf'
 *! src/test/resources/integration/Signatures.java:14: warning: concept 'nonexistent' not in signature of 'music.rdf'
 *! src/test/resources/integration/Signatures.java:15: warning: role 'nonexistent' not in signature of 'music.rdf'
 *! src/test/resources/integration/Signatures.java:17: warning: concept ':Politician' not in signature of 'music.rdf'
 *! src/test/resources/integration/Signatures.java:18: warning: individual ':Skeletonwitch' not in signature of 'music.rdf'
 *! src/test/resources/integration/Signatures.java:19: warning: role ':bribedBy' not in signature of 'music.rdf'
 *! src/test/resources/integration/Signatures.java:19: warning: concept ':Company' not in signature of 'music.rdf'
 *! WARNING: concept ':Politician' not in signature of 'music.rdf'
 *> atom: 0
 *! WARNING: individual ':Skeletonwitch' not in signature of 'music.rdf'
 *> one: 0
 *! WARNING: role ':bribedBy' not in signature of 'music.rdf'
 *! WARNING: concept ':Company' not in signature of 'music.rdf'
 *> exists: 0
 */
