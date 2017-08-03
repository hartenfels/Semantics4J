public class Intuit from "music.rdf" {
  public static void test() {
    ⊤ top;
    ⊥ bot;

    ¬⊤  nt;
    ¬¬⊤ nnt;

    ⊤ ⊔ ⊥ tub;
    ⊤ ⊓ ⊥ tnb;

    ∃△·«:MusicArtist» e1;
    ∀▽·⎨«:hendrix»⎬   a1;

    ∃▽·⊤    e2;
    ∀△⁻·¬¬⊥ a2;

    ∃▽·⊤ ⊔ «:MusicArtist»   e3;
    ∃▽·⊤ ⊔ ⊤ ⊔ ⊤ ⊓ ⊥        e4;
    ⊤ ⊔ ⊥ ⊔ ∃▽·⎨«:beatles»⎬ e5;
    ⊤ ⊔ ⊤ ⊓ ⊥ ⊔ ∃▽·⊤ ⊓ ⊥    e6;
  }

  public static void testTexas() {
    #T top;
    #F bot;

    -.#T   nt;
    -.-.#T nnt;

    #T ||| #F tub;
    #T &&& #F tnb;

    #E#f...<<<:MusicArtist>>> e1;
    #A#t...{|<<<:hendrix>>>|} a1;

    #E#t...#T      e2;
    #A#f⁻...-.-.#F a2;

    #E#t...#T ||| <<<:MusicArtist>>>        e3;
    #E#t...#T ||| #T ||| #T &&& #F          e4;
    #T ||| #F ||| #E#t...{|<<<:beatles>>>|} e5;
    #T ||| #T &&& #F ||| #E#t...#T &&& #F   e6;
  }
}
