εχTeX Build
=================

This document describes how to build εχTeX. εχTeX consists
of separate modules, which can be assembled in various ways.

Prerequisites
-------------

Unless otherwise specified, each component
requires the following software packages:

* [OpenJDK](https://openjdk.java.net/) version 8 (or newer)
* [Gradle](https://gradle.org/) version 6.6.1 (or newer)

Compiling εχTeX
-------------------------------------------

Compile all of εχTeX from the `extex` directory as follows:

    gradle clean build

Any `README.md` files that suggest using Maven to build are
outdated as of 29-Aug-2020.

Compiling sub-projects
-------------------------------------------

εχTeX is organized into the following sub-projects:

* `ExTeX` -- Typesetting system
* `ExBib` -- Bibliography processor
* `ExIndex` -- Index processor
* `ExTeX-resource` -- Common classes
* `ExTeX-example` -- Demonstrates usage scenarios

These sub-projects can be compiled independently using:

    gradle clean build

Compiling Javadocs
-------------------------------------------

Build and aggregate the εχTeX API Javadocs from the
`extex` directory into `build/docs/javadoc` as follows: 

    gradle aggregateJavadocs 

Alternatively, build individual component Javadocs as follows:

    gradle javadoc

Testing εχTeX
-------------------------------------------

Run the unit tests as follows:

    gradle test

Unit tests must always succeed. Failing unit tests indicate
where the development is incomplete; such tests must be
annotated using `@Ignore`, until the tests are corrected.
