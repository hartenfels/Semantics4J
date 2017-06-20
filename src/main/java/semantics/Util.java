package semantics;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import semantics.model.Concept;
import semantics.model.DescriptionLogic;
import semantics.model.Individual;
import semantics.model.Role;


public class Util {
  public static Concept concept(String iri) {
    return new Concept(iri);
  }

  public static Role role(String iri) {
    return new Role(iri);
  }


  private static Set<String> via(Set<? extends Individual>    is,
                                 Function<Individual, String> func) {
    Set<String> set = new HashSet<>();
    for (Individual i : is) {
      set.add(func.apply(i));
    }
    return set;
  }

  private static List<String> via(List<? extends Individual>   is,
                                  Function<Individual, String> func) {
    List<String> list = new ArrayList<>(is.size());
    for (Individual i : is) {
      list.add(func.apply(i));
    }
    return list;
  }


  public static Set<String> iris(Set<? extends Individual> is) {
    return via(is, Individual::getIri);
  }

  public static List<String> iris(List<? extends Individual> is) {
    return via(is, Individual::getIri);
  }


  public static Set<String> names(Set<? extends Individual> is) {
    return via(is, Individual::getName);
  }

  public static List<String> names(List<? extends Individual> is) {
    return via(is, Individual::getName);
  }


  public static <T extends Comparable<? super T>> List<T> sorted(Collection<T> c) {
    List<T> list = new ArrayList<T>(c);
    Collections.sort(list);
    return list;
  }


  public static <T> T head(Collection<T> c) {
    return c.iterator().next();
  }


  public static JsonElement toTaggedArray(String tag, JsonElement... es) {
    JsonArray arr = new JsonArray();
    arr.add(tag);

    for (JsonElement e : es) {
      arr.add(e);
    }

    return arr;
  }

  public static JsonElement allToJson(DescriptionLogic... elems) {
    JsonArray arr = new JsonArray();

    for (DescriptionLogic e : elems) {
      arr.add(e.toJson());
    }

    return arr;
  }

  public static <T> String join(String sep, T[] objects) {
    String[] strings = new String[objects.length];

    for (int i = 0; i < objects.length; ++i) {
      strings[i] = objects[i].toString();
    }

    return String.join(sep, strings);
  }
}
