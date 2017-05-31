package semantics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;
import java.util.HashSet;
import java.util.List;
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

  private static List<String> getVia(List<? extends Individual>   is,
                                     Function<Individual, String> func) {
    List<String> list = new ArrayList<>(is.size());
    for (Individual i : is) {
      list.add(func.apply(i));
    }
    return list;
  }


  public static Set<String> getIris(Set<? extends Individual> is) {
    return getVia(is, Individual::getIri);
  }

  public static List<String> getIris(List<? extends Individual> is) {
    return getVia(is, Individual::getIri);
  }


  public static Set<String> getNames(Set<? extends Individual> is) {
    return getVia(is, Individual::getName);
  }

  public static List<String> getNames(List<? extends Individual> is) {
    return getVia(is, Individual::getName);
  }


  public static <T extends Individual> List<T> getSorted(Set<T> is) {
    List<T> list = new ArrayList<T>(is);
    Collections.sort(list);
    return list;
  }
}
