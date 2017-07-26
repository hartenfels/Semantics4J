import static semantics.Util.head;


public class Subtypes from "wine.rdf" {
  public static void main(String[] args) {
    [⊤]           top   = head(query-for(":Wine"));
    [«:Wine»]     wine  = head(query-for(":Wine" ⊓ ⎨":TaylorPort"⎬));
    [∃▽·⎨«:Red»⎬] exist = head(query-for(∃":hasColor"·⎨":Red"⎬));
    [∀△·⎨«:Red»⎬] all   = head(query-for(∀":hasColor"·⎨":Red"⎬));
  }
}
