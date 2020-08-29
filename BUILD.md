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


Compiling sub-projects
-------------------------------------------

εχTeX is organized into the following sub-projects:

* `ExTeX-resource` -- Common classes
* `ExBib` -- Bibliography processor
* `ExTeX` -- Typesetting system
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
