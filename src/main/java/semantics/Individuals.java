package semantics;

import java.util.function.Function;
import java.util.HashSet;
import java.util.Set;
import semantics.model.Individual;


public class Individuals {
  private static Set<String> getVia(Set<? extends Individual>    is,
                                    Function<Individual, String> func) {
    Set<String> set = new HashSet<>();

    for (Individual i : is) {
      set.add(func.apply(i));
    }

    return set;
  }


  public static Set<String> getIris(Set<? extends Individual> is) {
    return getVia(is, Individual::getIri);
  }

  public static Set<String> getNames(Set<? extends Individual> is) {
    return getVia(is, Individual::getName);
  }
}
