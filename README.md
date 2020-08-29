εχTeX
=================

The goal of this fork is to ascertain whether real-time rendering of
TeX as either MathML or SVG is feasible using εχTeX.

εχTeX aims at the development of a high quality typesetting
system. Experience with the TeX typesetting drives the
development of this library. Despite its age, TeX can still be
considered a very good choice. Nevertheless, Knuth's original
TeX design decisions are no longer considered state of the art.

The stability of TeX is one of its virtues. On the other side it
makes it hard to improve the system -- even in the few areas which
deserve improvements. The new millenium needs a system which is open
for further development and at the same time compatible with TeX as
much as reasonable.

Original Code
-------------------------------

The original εχTeX source code is on
[SourceForge](https://sourceforge.net/projects/extex).

Getting Started
-------------------------------

If you are interested in using εχTeX, see the
[*εχTeX User's Guide*](doc/UsersGuide). If you are interested
in integrating εχTeX, see the [*Developer's Guide*](doc/DevelopersGuide),
which can also be found on the [εχTeX web site](http://www.extex.org).

File Organization
-----------------

The directory structure follows:

![](src/images/folder-blue.png) [archive](archive)
contains various archived projects not directly related to generating
TeX output.

![](src/images/folder-blue.png) [CLI](CLI)
contains a general-purpose library for command line parsing and
processing.

![](src/images/folder-blue.png) [ExBib](ExBib)
εχTeX has a sub-project εχBib which aims at providing a
bibliography processor. εχBib can be found in the sub-directory
[`ExBib`](ExBib).

![](src/images/folder-blue.png) [ExIndex](ExIndex)
εχTeX has a sub-project εχIndex which aims at providing an
index processor. εχIndex can be found in the sub-directory
[`ExIndex`](ExIndex).

![](src/images/folder-blue.png) [ExTeX](ExTeX)
εχTeX has the primary goal to provide a typesetter in the
spirit of TeX. This directory contains the specific modules for
this program.

![](src/images/folder-blue.png) [ExTeX-resource](ExTeX-resource)
contains a library for resource loading and several supporting
functionality for components and configuration.

![](src/images/folder-blue.png) [doc](doc)
contains documentation for the overall project and εχTeX
itself. See sub-projects for details.

![](src/images/folder-blue.png) [library](library)
contains the library of collected papers and documents.

![](src/images/folder-blue.png) [texmf](texmf)
contains texmf tree source files.

![](src/images/folder-blue.png) [tools](tools)
contains some tools.
