# NAME

Semantics4J — semantic data language extension for the [Software Languages Team](http://softlang.wikidot.com/) and [Institute for Web Science](https://west.uni-koblenz.de/lambda-dl) of the [University of Koblenz-Landau](https://www.uni-koblenz-landau.de/en/university-of-koblenz-landau)


# SYNOPSIS

[Semserv](https://github.com/hartenfels/Semserv) needs to be available to run
the tests, use the compiler and run applications with semantic data, so build
and run that first.

Clone this repo *without submodules* and run `make` to build the
[ExtendJ](https://bitbucket.org/extendj/extendj)-based compiler and run the
tests:

```sh
$ git clone https://github.com/hartenfels/Semantics4J.git
$ make
```

Then you can either try out some examples or use the compiler like a regular
Java compiler via `java -jar semantics.jar`. Note that you need to add
`semantics.jar` to your classpath too if you want to use semantic data in your
program, as it contains some necessary classes.


# BUILDING

Building requires a Unix-like system that has the commands `make`, `sh`,
`patch`, `find` and `git` available. [Gradle](https://gradle.org/) is required
for Java-related build tasks. The produced JAR files should be compatible with
any Java 8 installation.

First, `git clone https://github.com/hartenfels/Semantics4J.git`. Do **not**
use recursive cloning to pull in the submodules, they require some patches that
won't be applied if you clone them yourself. If you did accidentaly clone
recursively, run `make patch` to manually apply them before doing anything
else.

A knowledge base server must be available to compile and run programs, as well
as run the tests. For that, see <https://github.com/hartenfels/Semserv>.
Compilation of the compiler itself will work without this, but the tests will
fail, since they can't connect to the knowledge base.

Once you're all set up, simply run `make`. This will pull in and patch the
required submodules, compile the compiler itself (`semantics.jar`) and then
execute the tests.

After that, the compiler can be used by running `java -jar semantics.jar`,
although if you want to use semantic data features, you also need to include it
in your classpath. Its interface is compatible with that of `javac`. You can
also run some of the examples at this point – see section
[Examples](#examples).


# DESCRIPTION

The ideas from this work are based on the following papers, which may be worth
reading if you want to use it:

* [λ<sub>DL</sub>: Syntax and Semantics (Preliminary
  Report)](https://arxiv.org/pdf/1610.07033) by Martin Leinberger, Prof. Dr.
  Ralf Lämmel and Prof. Dr. Steffen Staab.

* A yet to be submitted Master's Thesis by Carsten Hartenfels.

## Examples

You can run these [examples](examples) by using `make Name.example`, where
`Name` is the name of the example (except wineseλrch, that lives in a
[different repository](https://github.com/hartenfels/winesearch)). You *must*
have [Semserv](https://github.com/hartenfels/Semserv) available to compile and
run these.

* [Influences](examples/Influences.java)

A simple example that lists musicians' influences, converted from [the
λ<sub>DL</sub> paper](https://arxiv.org/pdf/1610.07033). Shows off semantic
data types, querying, projecting and type casing.

* [Recommend](examples/Recommend.java)

A more involved example, recommending a kind of food for all wines in the [Wine
Ontology](https://www.w3.org/TR/owl-guide/wine.rdf) (via Emoji). Involves
types, querying, projecting, type casing, casting and several utility methods
for sets of individuals.

* [Wineries](examples/Wineries.java)

Example for showing off various type-related things: wildcards, diamond,
projection result types and subtyping of more interesting types.

* [wineseλrch](https://github.com/hartenfels/winesearch)

A full interactive application for wine searching. See its page for details.

## Usage

This section describes the various features of Semantics4J that you can use
from your program. You can also use the Semantics4J compiler to compile regular
Java code and it will work as if you'd compiled it with the regular ExtendJ
Java compiler.

### Data Source Specification

To actually use any semantic data features in your class, you need to specify
what ontology you want to use. This is done via the `knows` modifier on a
class, followed by the path to the ontology:

```java
public class Influences knows "music.rdf" {
  // ...
}
```

All other features *require* a data source specification, and will fail to
compile if you fail to provide one. The data source specification follows
*after* any `extends` or `implements` specifications:

```java
public class Thing extends Object implements Stuffable knows "wine.rdf" {
  // ...
}
```

The path is given to [Semserv](https://github.com/hartenfels/Semserv). If it's
not running or doesn't know the ontology, you'll get a crash. To specify a
different IP address or port for Semserv, you can set the `SEMSERV_HOST` and
`SEMSERV_PORT` environment variables.

Data source specifications are *lexical*, like variables. They apply to the
class you specify them on and all inner classes unless you shadow them with
another data source specification. They do *not* participate in inheritance.

### Semantic Data Types

Semantics4J allows you to use semantic concepts as types, specified by a
description logic syntax. You don't define any classes or a type hierarchy
ahead of time, the types are checked by using the knowledge base whenever
necessary. You can use them anywhere you could access a type otherwise: fields,
variables, parameters, generics, wildcards, casts and instanceof checks. You
can't use them in `extends` or `implements` though, because that'd be nonsense.

The description logic syntax is summarized in the following table, where `C`
and `D` are some concept expression, `R` is some role expression. Each operator
has a normal, Unicodey version and a pure-ASCII [“Texas
version”](https://docs.perl6.org/language/glossary#Texas_operator). Their
semantics are identical.

| Expression                 | Normal Version    | Texas Version         |
| -------------------------- |------------------ | --------------------- |
| Top Concept/Role           | `⊤`               | `#T`                  |
| Bottom Concept/Role        | `⊥`               | `#F`                  |
| Concept Atom               | `«:MusicArtist»`  | `<<<:MusicArtist>>>`  |
| Role Atom                  | `«:influencedBy»` | `<<<:influencedBy>>>` |
| Nominal Concept            | `⎨«:hendrix»⎬`    | `{|<<<:hendrix>>>|}`  |
| Union                      | `C ⊔ D`           | `C ||| D`             |
| Intersection               | `C ⊓ D`           | `C &&& D`             |
| Negation                   | `¬C`              | `-.C`                 |
| Inversion                  | `R⁻`              | `R^-`                 |
| Existential Quantification | `∃R·C`            | `#E R...C`            |
| Universal Quantification   | `∀R·C`            | `#A R...C`            |

From this, you can build types like `«:MusicArtist»` or `∃«:influencedBy»·⊤`.
Types must always resolve to a concept expression, you can't use a role as a
type. Expressions may be grouped with square brackets: `«:A» ⊓ [«:B» ⊔ «:C»]`.

Types are checked in the context of the ontology that the class `knows`. A
concept type is a subtype of another subtype if that fact is known (or can be
reasoned to be true). Two semantic data types are equal if they are subtypes of
each other, even if you use different expressions to describe them.

All semantic data types are actually subtypes of
[semantics.model.Individual](src/main/java/semantics/model/Individual.java).
They undergo type erasure during compilation, but opposed to generics,
`instanceof` checks and type casts are supported regardless.

All concept atoms, role atoms and nominal concepts are checked against the
ontology's signature. If it does not exist in the ontology, you'll get a
compile-time warning (not an error), because it usually means you made a typo.

### Description Logic Expressions

You can also use description logic expressions in regular code, the only
difference being that you don't use the `«»` syntax to specify atoms, using
Strings (not necessarily string literals, you can use anything that's an
instance of `String`) in their place instead. You also use parentheses `()` for
grouping, as with any other expression.

Concepts are instances of `semantics.model.Conceptual` and roles are instances
of `semantics.model.Roleish`. You can use them like this:

```java
import semantics.model.Conceptual;
// ...
Conceptual infl = ∃":influencedBy"⁻·⊤;
// You can use all the features you'd use in any other expression.
infl ⊓= ":Music" + "Artist";
```

If you want, you can also be explicit about your role and concept atoms, rather
than having Strings be wrapped for you:

```java
import semantics.model.Conceptual;
import semantics.model.Roleish;
import static semantics.Util.concept;
import static semantics.Util.role;
// ...
Conceptual c    = concept(":MusicArtist");
Roleish    r    = role(":influencedBy");
Conceptual infl = ∃r⁻·⊤ ⊓ c;
```

Where possible, you'll also get signature warnings at compile-time, as with
types. If you're using variables as parameters, then there won't be any warning
until you actually use your expression in a query or projection.

### Queries

You can use your description logic expressions to perform queries, using the
`query-for` keyword:

```java
import java.util.Set;
// ...
Set<«:MusicArtist»>      artists    = query-for(":MusicArtist");
Set<∃«:influencedBy»⁻·⊤> influenced = query-for(∃":influencedBy"⁻·⊤);
```

Queries return a `java.util.Set` of the appropriate type for their expression.
Only the constant parts of your query will be typed, if you use variables, the
expression will be resolved to its upper bound.

If a query is detected to be unsatisfiable at compile-time, compilation fails
with a fatal error. At run-time, an unsatisfiable query is not an error, you'll
just get an empty set as a result.

When you execute a query, you'll get warnings about concept atoms, role atoms
and nominal concepts that don't exist in the ontology's signature (using the
default logger).

### Projections

Projections are executed on individuals using a role expression and also return
a set of matching results. The type of the expression is `∃R⁻·C`, where `R` is
the expression's role type (or its upper bound if there's variables in it) and
`C` is the type of the individual:

```
import java.util.Set;
import static semantics.Util.head;
// ...
// Find Hendrix (knowledge base infers that he's a MusicArtist):
«:MusicArtist» hendrix = head(query-for(⎨":hendrix"⎬));
// And get his influences:
Set<∃«:influencedBy».«:MusicArtist»> influences = hendrix.(":influencedBy");
```

The method-call looking thing is the projection: `individualExpr.(roleExpr)`.
As with queries, you'll get warnings about missing signature bits.

### Type Casing

If you need to switch on a semantic data type, you *could* use a bunch of
instanceof checks and casts. However, Semantics4J gives you a type casing
feature instead that's easier to write and has better static checking.

The following `switch-type` type casing statement:

```java
switch-type (wine) {
  «:RedWine»   red   { /* do something with a red wine    */ }
  «:WhiteWine» white { /* do something with a white wine  */ }
  «:RoseWine»  rose  { /* do something with a rosé wine   */ }
  default            { /* none matched, do something else */ }
}
```

Is *approximately* equal to this:

```java
if (wine instanceof «:RedWine) {
  final «:RedWine» red = («:RedWine») wine;
  /* do something with a red wine */
}
else if (wine instanceof «:WhiteWine») {
  final «:WhiteWine» white = («:WhiteWine») wine;
  /* do something with a red wine */
}
else if (wine instanceof «:RoseWine») {
  final «:RoseWine» rose = («:RoseWine») wine;
  /* do something with a rose wine */
}
else {
  /* none matched, do something else */
}
```

Except it saves you a lot of typing and error-prone repetition. Semantics4J
also makes sure that you don't put your types in the wrong order (with earlier
types subsuming later ones). The `default` case is required, so you can't
forget that one either.

You can't use types equivalent to `⊤`, because they'd subsume the `default`
case. You also can't use unsatisfiable types, because they'd never match
anything, and that'd be useless.

## API

The main class you'll be interacting with is
[semantics.model.Individual](src/main/java/semantics/model/Individual.java),
which is the supertype of all semantic data types.  There are also several
utility functions available in
[semantics.Util](src/main/java/semantics/Util.java), intended for being
imported statically. Their usage is described in the following.

Note that everything else you can access from Java is **internal**, those are
APIs used by the Semantics4J compiler itself. Don't mess with them directly,
only use the documented APIs.

### semantics.model.Individual

An individual just knows its knowledge base and its IRI. You can retrieve them
with `getKnowBase()` and `getIri()` respectively, but you shouldn't do anything
with the knowledge base, since it's internal API.

You can get the fragment part of the IRI by calling `getName()`, which is
useful if you want to display an individual to the user. The `toString()`
method is the same as calling `getIri()`.

Indviduals support `equals`, `hashCode` and `compareTo` (by implementing
Comparable), so you can compare them for equality, sort collections of them or
use them as hash keys.

### semantics.Util

* `Concept concept(String iri)`
* `Role    role   (String iri)`

You can use these to construct a concept atom or role atom and get compile-time
signature checking. The functions are special-cased in the compiler to give you
a warning if you use an IRI that is not part of the current ontology's
signature.

You shouldn't need these unless you actually need a freestanding concept or
role atom specifically, because all the semantic data operators will implicitly
wrap strings in concepts or roles for you.

* `Set <String> iris (Set <? extends Individual> is)`
* `List<String> iris (List<? extends Individual> is)`
* `Set <String> names(Set <? extends Individual> is)`
* `List<String> names(List<? extends Individual> is)`

This family of functions turns a list or set of individuals into a matching
list or set of names or IRIs by calling `getName()` or `getIri()` on each of
them. This is just to save you from manually mapping or looping over them.

* `List<T> sorted(Collection<T> c)`

Sorts a collection of some comparable type and returns it in a list. You can
use this to sort the sets you get from queries and projections without having
to deal with Java's weird interface that makes you store the result in a
variable first.

For example: `sorted(query-for(":Wine"))`, or even
`sorted(names(query-for(":Wine")))`.

* `T head(Collection<T> c, T otherwise)`
* `T head(Collection<T> c)`

Gets the first element of the collection, or the given `otherwise` value if the
collection was empty. If you don't pass any value, it'll use `null`.

The collection must support calling `iterator()` on it. If it's a collection
with no specific order (like a Set), you'll get whatever element the iterator
decides is the first one.

This function makes sense to use if you expect a query to only return one
result, and you want to get that single result. For example,
`head(query-for(":Wine" ⊓ ⎨":TaylorPort"⎬))`.


# DEVELOPMENT

This repository is structured as follows:

* [build.gradle](build.gradle), [jastadd\_modules](jastadd_modules),
  [Makefile](Makefile) - build files for Gradle, the JastAdd Gradle plugin and
  make.

* [examples](examples) - the examples. They should have one top-level .java
  file so that they'll automatically get picked up by `make list-examples` and
  `make %.example`. The expected output for the examples should also be part of
  the integration tests.

* [extendj](extendj) - [ExtendJ](https://bitbucket.org/extendj/extendj) as a
  submodule.

* [README.md](README.md) - this file. And until it gets way too long, the
  primary source of documentation.

* [patches](patches) - some patches to the ExtendJ parser, see section
  [Patches](#patches).

* [LICENSE](LICENSE) - the Apache License, Version 2.

* [src/main](src/main) - the actual source code.

  * [grammar](src/main/grammar) - JastAdd AST grammar definitions.

  * [jastadd](src/main/jastadd) - JastAdd aspect files, the meat of the
    compiler extension.

  * [java](src/main/java) - the library part of Semantics4J, with the
    description logic model, knowledge base interface and utility functions.

  * [parser](src/main/parser) - parsing definition files for Beaver.

  * [pretty-print](src/main/pretty-print) - pretty print template for
    [aspectgen](https://bitbucket.org/joqvist/aspectgen). This generates
    [PrettyPrint.jadd](src/main/jastadd/PrettyPrint.jadd).

  * [scanner](src/main/scanner) - JLex definitions for scanner tokens.

* [src/test](src/test) - code for tests, see section [Tests](#tests) for
  details.

## Patches

ExtendJ's parser is built by just concatenating a bunch of parser specification
files. That's not modular enough for Semantics4J's use case though, because we
need to change operator precedence. The solution to this are some small
[patches](patches).

These are automatically applied by using `make patch`, check the
[Makefile](Makefile) for what commands this actually runs. This also means that
git will always say that the ExtendJ submodule has been modified, which you can
disregard.

If you need to update the patches, make the appropriate changes in the ExtendJ
parser files and run the following:

```sh
# copy the final parser files
cp extendj/java4/parser/Java1.4.parser extendj/java5/parser/java14fix.parser patches
# reset ExtendJ
git -C extendj reset --hard
# make the patches
diff -u extendj/java4/parser/Java1.4.parser patches/Java1.4.parser > patches/java4.patch
diff -u extendj/java5/parser/java14fix.parser patches/java14fix.parser > patches/java5.patch
# remove the copied parser files
rm -f patches/Java1.4.parser patches/java14fix.java
```

Then you can re-apply your patches with `make patch` to see if they work.

## Tests

There are three kinds of tests set up currently: regular unit tests, syntax
tests and integration tests.

Unit tests are just testing the Java side of things, and are regular JUnit
tests. You can find them in [src/test/java/semantics](src/test/java/semantics).
Packages in that folder should reflect the packages of what the classes are
testing.

Syntax tests are intended to test the parsing and static checking of the
Semantics4J compiler. Tests consist of pairs of `.java` and `.expected` files
in [src/test/resources/syntax](src/test/resources/syntax): an input file with
the code to parse and a file representing the expected structured
pretty-printed output (which has a bunch of extra parentheses for ensuring
correct operator precedence). The [JUnit test
file](src/test/java/syntax/SyntaxTest.java) is generated automatically from
these pairs of files during build time. The interesting parts on how the syntax
tests are executed are found in
[SyntaxTestBase](src/test/java/syntax/SyntaxTestBase.java).

Finally, integration tests go the entire way: programs are compiled with
`semantics4j.jar` and then executed. The tests ensure that the compilation and
run phase output the expected things on stdout and stderr.

Test files go in
[src/test/resources/integration](src/test/resources/integration) as regular
Java files, however, you must specify the expected output in a comment. The
output must cover both the compilation and, if it's expected to get there, also
the execution. Lines that start with ` *> ` are taken to be output on stdout,
lines that start with ` *! ` are stderr output. See [the signatures
test](src/test/resources/integration/Signatures.java) for an example.

Once again, the [JUnit test
file](src/test/java/integration/IntegrationTest.java) is auto-generated and the
interesting parts about executing them are in
[IntegrationTestBase](src/test/java/integration/IntegrationTestBase.java).


# LICENSE

Copyright 2017 Carsten Hartenfels.

Licensed under the [Apache License, Version 2](LICENSE).


# SEE ALSO

* [Semserv](https://github.com/hartenfels/Semserv)

* [λ-DL](https://west.uni-koblenz.de/lambda-dl)

* [ExtendJ](https://bitbucket.org/extendj/extendj)

* [OWL API](http://owlapi.sourceforge.net/)

* [HermiT](http://www.hermit-reasoner.com/)
