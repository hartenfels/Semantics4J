# NAME

JASTics — semantic data language extension for the [Software Languages Team](http://softlang.wikidot.com/) and [Institute for Web Science](https://west.uni-koblenz.de/lambda-dl) of the [University of Koblenz-Landau](https://www.uni-koblenz-landau.de/en/university-of-koblenz-landau)


# SYNOPSIS

TBD


# BUILDING

Building requires a Unix-like system that has the commands `make`, `sh`,
`patch`, `find` and `git` available. [Gradle](https://gradle.org/) is required
for Java-related build tasks. The produced JAR files should be compatible with
any Java 8 installation.

First, `git clone https://github.com/hartenfels/Jastics.git`. Do **not** use
recursive cloning to pull in the submodules, they require some patches that
won't be applied if you clone them yourself.

A knowledge base server must be available to compile and run programs, as well
as run the tests. For that, see <https://github.com/hartenfels/Semserv>.
Compilation of the compiler itself will work without this, but the tests will
fail, since they can't connect to the knowledge base.

Once you're all set up, simply run `make`. This will pull in and patch the
required submodules, compile the compiler itself (`jastics.jar`) and then
execute the tests.

After that, the compiler can be used by running `java -jar jastics.jar`. Its
interface is compatible with that of `javac`. You can also run some of the
examples at this point – see section [Examples](#examples).


# DESCRIPTION

TBD

## Examples

TBD


# LICENSE

Copyright 2017 Carsten Hartenfels.

Licensed under the [Apache License, Version 2](LICENSE).


# SEE ALSO

* [λ-DL](https://west.uni-koblenz.de/lambda-dl)

* [LambdaDL](https://github.com/hartenfels/LambdaDL)

* [Semserv](https://github.com/hartenfels/Semserv)

* [HermiT](http://www.hermit-reasoner.com/)
