import semantics.model.Individual;
import java.util.Set;

public class Query knows "music.rdf" {
  public static void query() {
    Set<? extends Individual> everything = query-for(⊤);

    Set<«:MusicArtist» > artists  = query-for(":MusicArtist");
    Set<«:RadioStation»> stations = query-for(":Radio" + "Station");

    System.out.println(query-for(":MusicArtist").toString());
  }

  public static void literalExpr() {
    Object o1 = ⊤;
    Object o2 = (⊥);
  }

  public static void binaryExpr() {
    Object o1  = "a" ⊔ "b";
    Object o2  = "a" ⊓ "b";
    Object o3  = "a" ⊔ "b" ⊓ "c";
    Object o4  = "a" ⊓ "b" ⊔ "c";
    Object o5  = "a" ⊔ "b" ⊔ "c";
    Object o6  = "a" ⊓ "b" ⊓ "c";
    Object o7  = ("a" ⊔ "b");
    Object o8  = ("a" ⊓ "b");
    Object o9  = ("a" ⊔ "b") ⊓ "c";
    Object o10 = ("a" ⊔ "b" ⊓ "c");
    Object o11 = "a" ⊔ ("b" ⊓ "c");
    Object o12 = ("a" ⊔ ("b" ⊓ "c"));
    Object o13 = "a" ⊓ ("b" ⊔ "c");
    Object o14 = ("a" ⊓ ("b" ⊔ "c"));
  }

  public static void inversionExpr() {
    Object o1 = "a"⁻;
    Object o2 = "a"⁻⁻⁻⁻;
    Object o3 = ("a"⁻);
    Object o4 = (("a"⁻)⁻⁻)⁻;
  }

  public static void negationExpr() {
    Object o1 = ¬"a";
    Object o2 = ¬¬¬¬"a";
    Object o3 = (¬"a");
    Object o4 = ¬(¬¬(¬"a"));
  }

  public static void quantifierExpr() {
    Object o1 = ∃"a" ⇒ "b";
    Object o2 = ∀("a") ⇒ ("b");
    Object o3 = (∃"a" ⇒ "b");
    Object o4 = ∀"a" ⇒ ∃"b" ⇒ "c";
    Object o5 = ∀"a"⁻⁻ ⇒ "b" ⊓ "c";
    Object o6 = "a" ⊓ ∃"b" ⇒ "c";
  }

  public static void oneExpr() {
    Object o1 = ⎨"a"⎬;
    Object o2 = (⎨"a"⎬);
    Object o3 = (⎨("a")⎬);
    Object o4 = ⎨"a" + "b"⎬;
  }

  public static void complexExprs() {
    Object o1 = "a" ⊓ ∃"b" ⇒ ⎨"c"⎬;
    Object o2 = ("a" ⊓ ∃"b" ⇒ ⎨"c"⎬);

    Object o3 = ⊥ ⊓ ∃"a" ⇒ ⎨"b"⎬;
    Object o4 = (⊥ ⊓ ∃"a" ⇒ ⎨"b"⎬);
  }
}
