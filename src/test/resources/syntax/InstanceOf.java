import semantics.model.Individual;

public class Sample knows "music.rdf" {
  public static void test() {
    Individual ind = null;
    boolean a = ind instanceof «:MusicArtist»;
    boolean b = ind instanceof ∃«:influencedBy»·⊤;
    boolean c = new Object() instanceof ∀«a»⁻⁻·«b» ⊔ «c» ⊓ «d»;
  }
}
