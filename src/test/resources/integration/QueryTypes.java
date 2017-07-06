import java.util.Set;
import java.util.Random;


public class QueryTypes knows "wine.rdf" {
  private static String getRandomWineName() {
    String[] names = {":RoseDAnjou", ":TaylorPort"};
    return names[new Random().nextInt(names.length)];
  }

  public static void main(String[] args) {
    // Some random string that isn't known at compile-time.
    String name = getRandomWineName();

    Set<⊥ ⊓ ⊤> unsatisfiable = query-for(⊥ ⊓ name);

    // Purposely cause type errors that tell use the actual type.
    int a = query-for(":Wine");
    int b = query-for(name);
    int c = query-for(⎨":TaylorPort"⎬);
    int d = query-for(⎨name⎬);
    int e = query-for(":Wine" ⊓ ⎨":TaylorPort"⎬);
    int f = query-for(":Wine" ⊓ ⎨name⎬);
    int g = query-for(":Wine" ⊔ ⎨":TaylorPort"⎬);
    int h = query-for(":Wine" ⊔ ⎨name⎬);
    int i = query-for(∃":hasColor"·⎨":Red"⎬);
    int j = query-for(∃":hasColor"·⎨name⎬);
    int k = query-for(∀":hasColor"·⎨":Red"⎬);
    int l = query-for(∀":hasColor"·⎨name⎬);
    int m = query-for(∃name·⎨":Red"⎬);
    int n = query-for(∀name·⎨":Red"⎬);
    int o = query-for(∃":hasColor"⁻·⎨":TaylorPort"⎬);
    int p = query-for(∃":hasColor"⁻·⎨name⎬);
    int q = query-for(∀":hasColor"⁻·⎨":TaylorPort"⎬);
    int r = query-for(∀":hasColor"⁻·⎨name⎬);
    int s = query-for(∃name⁻·⎨":TaylorPort"⎬);
    int t = query-for(∀name⁻·⎨":TaylorPort"⎬);
    int u = query-for(¬":Wine");
    int v = query-for(¬name);
    int w = query-for(¬(":Wine" ⊓ ⎨":TaylorPort"⎬));
    int x = query-for(¬(":Wine" ⊓ ⎨name⎬));
  }
}


/*
 *! src/test/resources/integration/QueryTypes.java:15: error: query-for [⊥ ⊓ ?] is unsatisfiable
 *! src/test/resources/integration/QueryTypes.java:18: error: can not assign variable a of type int a value of type java.util.Set<«:Wine»>
 *! src/test/resources/integration/QueryTypes.java:19: error: can not assign variable b of type int a value of type java.util.Set<⊤>
 *! src/test/resources/integration/QueryTypes.java:20: error: can not assign variable c of type int a value of type java.util.Set<⎨«:TaylorPort»⎬>
 *! src/test/resources/integration/QueryTypes.java:21: error: can not assign variable d of type int a value of type java.util.Set<⊤>
 *! src/test/resources/integration/QueryTypes.java:22: error: can not assign variable e of type int a value of type java.util.Set<[«:Wine» ⊓ ⎨«:TaylorPort»⎬]>
 *! src/test/resources/integration/QueryTypes.java:23: error: can not assign variable f of type int a value of type java.util.Set<[«:Wine» ⊓ ⊤]>
 *! src/test/resources/integration/QueryTypes.java:24: error: can not assign variable g of type int a value of type java.util.Set<[«:Wine» ⊔ ⎨«:TaylorPort»⎬]>
 *! src/test/resources/integration/QueryTypes.java:25: error: can not assign variable h of type int a value of type java.util.Set<[«:Wine» ⊔ ⊤]>
 *! src/test/resources/integration/QueryTypes.java:26: error: can not assign variable i of type int a value of type java.util.Set<∃«:hasColor»·⎨«:Red»⎬>
 *! src/test/resources/integration/QueryTypes.java:27: error: can not assign variable j of type int a value of type java.util.Set<∃«:hasColor»·⊤>
 *! src/test/resources/integration/QueryTypes.java:28: error: can not assign variable k of type int a value of type java.util.Set<∀«:hasColor»·⎨«:Red»⎬>
 *! src/test/resources/integration/QueryTypes.java:29: error: can not assign variable l of type int a value of type java.util.Set<∀«:hasColor»·⊤>
 *! src/test/resources/integration/QueryTypes.java:30: error: can not assign variable m of type int a value of type java.util.Set<∃↑·⎨«:Red»⎬>
 *! src/test/resources/integration/QueryTypes.java:31: error: can not assign variable n of type int a value of type java.util.Set<∀↓·⎨«:Red»⎬>
 *! src/test/resources/integration/QueryTypes.java:32: error: can not assign variable o of type int a value of type java.util.Set<∃«:hasColor»⁻·⎨«:TaylorPort»⎬>
 *! src/test/resources/integration/QueryTypes.java:33: error: can not assign variable p of type int a value of type java.util.Set<∃«:hasColor»⁻·⊤>
 *! src/test/resources/integration/QueryTypes.java:34: error: can not assign variable q of type int a value of type java.util.Set<∀«:hasColor»⁻·⎨«:TaylorPort»⎬>
 *! src/test/resources/integration/QueryTypes.java:35: error: can not assign variable r of type int a value of type java.util.Set<∀«:hasColor»⁻·⊤>
 *! src/test/resources/integration/QueryTypes.java:36: error: can not assign variable s of type int a value of type java.util.Set<∃↑·⎨«:TaylorPort»⎬>
 *! src/test/resources/integration/QueryTypes.java:37: error: can not assign variable t of type int a value of type java.util.Set<∀↓·⎨«:TaylorPort»⎬>
 *! src/test/resources/integration/QueryTypes.java:38: error: can not assign variable u of type int a value of type java.util.Set<¬«:Wine»>
 *! src/test/resources/integration/QueryTypes.java:39: error: can not assign variable v of type int a value of type java.util.Set<⊤>
 *! src/test/resources/integration/QueryTypes.java:40: error: can not assign variable w of type int a value of type java.util.Set<¬[«:Wine» ⊓ ⎨«:TaylorPort»⎬]>
 *! src/test/resources/integration/QueryTypes.java:41: error: can not assign variable x of type int a value of type java.util.Set<⊤>
 */
