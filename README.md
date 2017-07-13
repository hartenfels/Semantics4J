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
won't be applied if you clone them yourself.

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


# DEVELOPMENT

TBD


# LICENSE

Copyright 2017 Carsten Hartenfels.

Licensed under the [Apache License, Version 2](LICENSE).


# SEE ALSO

* [Semserv](https://github.com/hartenfels/Semserv)

* [λ-DL](https://west.uni-koblenz.de/lambda-dl)

* [OWL API](http://owlapi.sourceforge.net/)

* [HermiT](http://www.hermit-reasoner.com/)
