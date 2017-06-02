import semantics.model.Individual;


public class ImplicitTypeConversion knows "wine.rdf" {
  public static void main(String[] args) {
    for («:RedWine» ⊓ «:DryWine» redAndDryWine : do-query(":RedWine" ⊓ ":DryWine")) {
      Individual individual = redAndDryWine;
      «:RedWine» redWine    = redAndDryWine;
      «:DryWine» dryWine    = redAndDryWine;
      «:Wine»    wine       = redAndDryWine;

      redWine       = dryWine;
      redAndDryWine = wine;
      wine          = individual;
    }
  }
}


/*
 *! src/test/resources/integration/ImplicitTypeConversion.java:12: error: can not assign redWine of type «:RedWine» a value of type «:DryWine»
 *! src/test/resources/integration/ImplicitTypeConversion.java:13: error: can not assign redAndDryWine of type ｢«:RedWine» ⊓ «:DryWine»｣ a value of type «:Wine»
 *! src/test/resources/integration/ImplicitTypeConversion.java:14: error: can not assign wine of type «:Wine» a value of type semantics.model.Individual
 */
