# NAME

Semantics4J — semantic data language extension for the [Software Languages Team](http://softlang.wikidot.com/) and [Institute for Web Science](https://west.uni-koblenz.de/lambda-dl) of the [University of Koblenz-Landau](https://www.uni-koblenz-landau.de/en/university-of-koblenz-landau)


# SYNOPSIS

[Semserv](https://github.com/hartenfels/Semserv) needs to be available to run
the tests, use the compiler and run applications with semantic data, so build
and run that first.

Clone this repo *without submodules* and run `make` to build the compiler and
run the tests:

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

TBD

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

* [extendj](extendj) - ExtendJ as a submodule.

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
[IntegrationTestBase](src/test/java/IntegrationTestBase.java).


# LICENSE

Copyright 2017 Carsten Hartenfels.

Licensed under the [Apache License, Version 2](LICENSE).


# SEE ALSO

* [Semserv](https://github.com/hartenfels/Semserv)

* [λ-DL](https://west.uni-koblenz.de/lambda-dl)

* [OWL API](http://owlapi.sourceforge.net/)

* [HermiT](http://www.hermit-reasoner.com/)
