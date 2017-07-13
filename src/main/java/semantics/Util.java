package semantics;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import semantics.model.Concept;
import semantics.model.DescriptionLogic;
import semantics.model.Individual;
import semantics.model.Role;


/**
 * Utility functions for Semantics4J data.
 *
 * These functions mostly save you tedium when dealing with query and
 * projection results, and give you a way to construct concept and role atoms
 * without meddling with Semantics4J's internal model (which you shouldn't do).
 *
 * You should import the functions you need statically.
 */
public class Util {
  /**
   * Construct a new concept atom, with compile-time signature checking.
   *
   * This function is special-cased in the compiler, you'll get signature
   * checking at compile-time if you call this with a constant string. If it's
   * a dynamic string, it's the same as instantiating the concept atom
   * yourself, but you should still use this function to do it because the
   * model classes are internal.
   *
   * @param iri The (potentially abbreviated) IRI for the concept.
   *
   * @return A new concept atom with your IRI.
   */
  public static Concept concept(String iri) {
    return new Concept(iri);
  }

  /**
   * Construct a new role atom, with compile-time signature checking.
   *
   * As with {@link #concept(String) concept}, this function is special-cased
   * and will give you compile-time signature checking.
   *
   * @param iri The (potentially abbreviated) IRI for the role.
   *
   * @return A new role atom with your IRI.
   */
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


  /**
   * Returns a Set of the IRIs of the given Set of Individuals.
   */
  public static Set<String> iris(Set<? extends Individual> is) {
    return via(is, Individual::getIri);
  }

  /**
   * Returns a List of the IRIs of the given List of Individuals.
   */
  public static List<String> iris(List<? extends Individual> is) {
    return via(is, Individual::getIri);
  }


  /**
   * Returns a Set of the names of the given Set of Individuals.
   */
  public static Set<String> names(Set<? extends Individual> is) {
    return via(is, Individual::getName);
  }

  /**
   * Returns a List of the names of the given List of Individuals.
   */
  public static List<String> names(List<? extends Individual> is) {
    return via(is, Individual::getName);
  }


  /**
   * Returns a sorted list of the elements of the given collection.
   */
  public static <T extends Comparable<? super T>> List<T> sorted(Collection<T> c) {
    List<T> list = new ArrayList<T>(c);
    Collections.sort(list);
    return list;
  }


  /**
   * Return the first item of a collection, or the given default value.
   *
   * @param c Some collection you want the first element of. It must support
   *          the <code>iterator()</code> method.
   *
   * @param otherwise The default value, in case the collection doesn't have a
   *                  first element. This should be some sentinel value that
   *                  you know how to check for.
   *
   * @return The first element of <code>c</code> according to its iterator, or
   *         your <code>otherwise</code> value if the collection is empty.
   */
  public static <T> T head(Collection<T> c, T otherwise) {
    try {
      return c.iterator().next();
    }
    catch (NoSuchElementException e) {
      return otherwise;
    }
  }

  /**
   * Shortcut for <code>head(c, null)</code>.
   *
   * If <code>null</code> is a potential value in your collection, you'll
   * probably want to specify a better sentinel value yourself.
   */
  public static <T> T head(Collection<T> c) {
    return head(c, null);
  }
}
