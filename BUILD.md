εχTeX Build
=================

This document describes how to build εχTeX.

Prerequisites
-------------

Unless otherwise specified by individual components, each component
requires the following software packages:

* [OpenJDK](https://openjdk.java.net/) version 8 (or newer)
* [Gradle](https://gradle.org/) version 6.6.1 (or newer)




Compiling εχTeX
-------------------------------------------

εχTeX is consists of separate modules, which can be
assembled in various ways.

Compiling sub-projects
-------------------------------------------

εχTeX is organized into the following sub-projects:

* `ExBib` -- Bibliography processor
* `ExTeX` -- Typesetting system
* `ExTeX-resource` -- Common classes
* `ExIndex` -- Index processor

These sub-projects are organized in a way that they can
be compiled independently. Consult the `README` files in
the sub-projects to get instructions to compile them separately.

Testing εχTeX
-------------------------------------------

Run the unit tests as follows:

    gradle test

The unit tests must always succeed. Unit tests that fail
indicate where the development is incomplete. Failed tests
must be annotated using `@Ignore` until they pass.
