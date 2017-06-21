public class TypecaseErrors knows "wine.rdf" {
  public static void main(String[] args) {
    for («:Wine» wine : query-for(":Wine")) {
      switch-type (wine) {
        ⊥              _ {}
        ⊤              _ {}
        ⊤ ⊔ «:RedWine» _ {}
        «:WhiteWine»   _ {}
      }
    }
  }
}


/*
 *! src/test/resources/integration/TypecaseErrors.java:4: error: typecase has 0 defaults, should be exactly 1
 *! src/test/resources/integration/TypecaseErrors.java:4: error: typecase '⊥' is unsatisfiable and will never match
 *! src/test/resources/integration/TypecaseErrors.java:4: error: typecase '⊤' will always match, use the default case instead
 *! src/test/resources/integration/TypecaseErrors.java:4: error: bad typecase order, '⊤' subsumes '⊤ ⊔ «:RedWine»'
 *! src/test/resources/integration/TypecaseErrors.java:4: error: typecase '⊤ ⊔ «:RedWine»' will always match, use the default case instead
 *! src/test/resources/integration/TypecaseErrors.java:4: error: bad typecase order, '⊤' subsumes '«:WhiteWine»'
 *! src/test/resources/integration/TypecaseErrors.java:4: error: bad typecase order, '⊤ ⊔ «:RedWine»' subsumes '«:WhiteWine»'
 */
