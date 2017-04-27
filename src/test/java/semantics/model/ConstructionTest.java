package semantics.model;

import org.junit.Test;
import semantics.model.*;
import static org.junit.Assert.*;


public class ConstructionTest {
  private static void is(String expected, Jsonable actual) {
    assertEquals(expected, actual.toString());
  }


  @Test
  public void simple() {
    is("⊤",          new Everything());
    is("⊥",          new Nothing());
    is("<Con:cept>", new Concept("Con:cept"));
    is("<Ro:le>",    new Role("Ro:le"));
    is("{On:e}",     new One("On:e"));
  }


  @Test
  public void unary() {
    is("¬<c>", new Negation (new Concept("c")));
    is("<r>⁻", new Inversion(new Role   ("r")));

    is("¬¬<c>", new Negation (new Negation (new Concept("c"))));
    is("<r>⁻⁻", new Inversion(new Inversion(new Role   ("r"))));
  }


  @Test
  public void quantifiers() {
    is("∃<r>.<c>", new Existence(new Role("r"), new Concept("c")));
    is("∀<r>.<c>", new Universal(new Role("r"), new Concept("c")));
  }


  @Test
  public void infix() {
    Conceptual a = new Concept("a");
    Conceptual b = new Concept("b");
    Conceptual c = new Concept("c");

    is("()", new Union());
    is("()", new Intersection());

    is("(<a>)", new Union(a));
    is("(<a>)", new Intersection(a));

    is("(<a> ⊔ <b>)", new Union(a, b));
    is("(<a> ⊓ <b>)", new Intersection(a, b));

    is("(<a> ⊔ <b> ⊔ <c>)", new Union(a, b, c));
    is("(<a> ⊓ <b> ⊓ <c>)", new Intersection(a, b, c));
  }


  @Test
  public void nested() {
    is(
        "(∃<r>.(∀<s>⁻.¬{o} ⊓ <c>) ⊔ ⊥)",
        new Union(
          new Existence(
            new Role("r"),
            new Intersection(
              new Universal(
                new Inversion(new Role("s")),
                new Negation(new One("o"))
              ),
              new Concept("c")
            )
          ),
          new Nothing()
        )
    );
  }
}
