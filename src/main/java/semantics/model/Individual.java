package semantics.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import semantics.Individuals;
import semantics.KnowBase;


public final class Individual {
  private static final Pattern[] NAME_PATTERNS = {
    Pattern.compile("<.*#(.*)>"),
    Pattern.compile("<.*?:(.*)>"),
    Pattern.compile("<(.*)>"),
    Pattern.compile(".*?:(.*)"),
  };


  private final KnowBase kb;
  private final String   iri;

  public Individual(KnowBase kb, String iri) {
    this.kb  = kb;
    this.iri = iri;
  }


  public String getIri() {
    return iri;
  }

  public String getName() {
    for (Pattern p : NAME_PATTERNS) {
      Matcher m = p.matcher(iri);
      if (m.matches()) {
        return m.group(1);
      }
    }
    return iri;
  }


  public Individuals project(Roleish r) {
    return kb.project(this, r);
  }

  public Individuals project(String iri) {
    return project(new Role(iri));
  }


  @Override
  public boolean equals(Object other) {
    if (other instanceof Individual) {
      Individual i = (Individual) other;
      return kb.equals(i.kb) && iri.equals(i.iri);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return (kb + "\0" + iri).hashCode();
  }
}
