package semantics;

import java.util.function.Function;
import java.util.HashSet;
import java.util.Set;
import semantics.model.Individual;


public class Individuals extends HashSet<Individual> {
  private Set<String> getVia(Function<Individual, String> func) {
    Set<String> set = new HashSet<>();

    for (Individual i : this) {
      set.add(func.apply(i));
    }

    return set;
  }


  public Set<String> getIris() {
    return getVia(Individual::getIri);
  }

  public Set<String> getNames() {
    return getVia(Individual::getName);
  }
}
