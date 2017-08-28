import semantics.model.*;
import static semantics.Util.concept;
import static semantics.Util.head;
import static semantics.Util.role;


public class Model from "music.rdf" {
  private static int n = 0;


  public static void main(String[] args) {
    is(⊤, new Everything());
    is(⊥, new Nothing());
    is(▽, new Full());
    is(△, new Empty());

    is(concept(":MusicArtist"), new Concept(":MusicArtist"));
    is(role(":influencedBy"), new Role(":influencedBy"));

    is(⎨":hendrix"⎬, new One(":hendrix"));
    is(¬":MusicArtist", new Negation(new Concept(":MusicArtist")));
    is(":influencedBy"⁻, new Inversion(new Role(":influencedBy")));

    is(":MusicArtist" ⊔ ⊥, new Union(new Concept(":MusicArtist"),
                                     new Nothing()));

    is(":MusicArtist" ⊓ ⊤, new Intersection(new Concept(":MusicArtist"),
                                            new Everything()));

    is(∃":influencedBy"·⊤, new Existence(new Role(":influencedBy"),
                                         new Everything()));

    is(∀△·":MusicArtist", new Universal(new Empty(),
                                        new Concept(":MusicArtist")));

    final «:MusicArtist» hendrix = head(query-for(⎨":hendrix"⎬));
    is(¬hendrix, new Negation(new One(hendrix.getIri())));

    System.out.format("1..%d\n", n);
  }


  private static void is(DescriptionLogic dl, DescriptionLogic model) {
    if (!dl.equals(model)) {
      System.out.print("not ");
    }
    System.out.format("ok %d - '%s' is '%s'\n", ++n, dl, model);
  }
}

/*
 *> ok 1 - '⊤' is '⊤'
 *> ok 2 - '⊥' is '⊥'
 *> ok 3 - '▽' is '▽'
 *> ok 4 - '△' is '△'
 *> ok 5 - '«:MusicArtist»' is '«:MusicArtist»'
 *> ok 6 - '«:influencedBy»' is '«:influencedBy»'
 *> ok 7 - '⎨«:hendrix»⎬' is '⎨«:hendrix»⎬'
 *> ok 8 - '¬«:MusicArtist»' is '¬«:MusicArtist»'
 *> ok 9 - '«:influencedBy»⁻' is '«:influencedBy»⁻'
 *> ok 10 - '[«:MusicArtist» ⊔ ⊥]' is '[«:MusicArtist» ⊔ ⊥]'
 *> ok 11 - '[«:MusicArtist» ⊓ ⊤]' is '[«:MusicArtist» ⊓ ⊤]'
 *> ok 12 - '∃«:influencedBy»·⊤' is '∃«:influencedBy»·⊤'
 *> ok 13 - '∀△·«:MusicArtist»' is '∀△·«:MusicArtist»'
 *> ok 14 - '¬⎨«http://example.org/music#hendrix»⎬' is '¬⎨«http://example.org/music#hendrix»⎬'
 *> 1..14
 */
