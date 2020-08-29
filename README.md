εχTeX
=================

The goal of this fork is to ascertain whether real-time rendering of
TeX into either MathML or SVG is feasible using εχTeX.

εχTeX aims at the development of a high quality typesetting
system. This development is massively based on the experiences with the
typesetting system TeX. Despite of its age TeX can still be
considered a very good choice. Nevertheless design decisions which where
reasonable at the time of the writing of TeX can nowadays no
longer be considered as state of the art.

The stability of TeX is one of its virtues. On the other side it
makes it hard to improve the system -- even in the few areas which
deserve improvements. The new millenium needs a system which is open for
further development and at the same time compatible with TeX as
much as reasonable.

Original Code
-------------------------------

The original εχTeX source code can be found on
[SourceForge](https://sourceforge.net/projects/extex/).

Getting Started
-------------------------------

If you are interested in using εχTeX you should have a look
at the *εχTeX User's Guide*. The source code for the
user's guide can be found in the directory
[`doc/UsersGuide`](doc/UsersGuide).

If you are interested in the sources you should have a look at the
*Developer's Guide*. The source code can be found in
[`doc/DevelopersGuide`](doc/DevelopersGuide). A compiled version can be
downloaded from the [εχTeX web site](http://www.extex.org/).

File Organization
-----------------

![](src/images/folder-blue.png) [archive](archive/)
contains various archived projects not directly related to generating
TeX output.

![](src/images/folder-blue.png) [CLI](CLI/)
contains a general purpose library for command line parsing and
processing.

![](src/images/folder-blue.png) [ExBib](ExBib/)
εχTeX has a sub-project εχBib which aims at providing a
bibliography processor. εχBib can be found in the sub-directory
[`ExBib`](ExBib).

![](src/images/folder-blue.png) [ExIndex](ExIndex/)
εχTeX has a sub-project εχIndex which aims at providing an
index processor. εχIndex can be found in the sub-directory
[`ExIndex`](ExIndex).

![](src/images/folder-blue.png) [ExTeX](ExTeX/)
εχTeX has the primary goal to provide a typesetter in the
spirit of TeX. This directory contains the specific modules for
this program.

![](src/images/folder-blue.png) [ExTeX-resource](ExTeX-resource/)
contains a library for resource loading and several supporting
functionality for components and configuration.

![](src/images/folder-blue.png) [develop](develop/)
contains configurations and tools for the development.

![](src/images/folder-blue.png) [doc](doc/)
contains documentation for the overall project and εχTeX
itself. Additional documentation can be found in the sub-projects.

![](src/images/folder-blue.png) [library](library/)
contains the library of collected papers and documents.

![](src/images/folder-blue.png) [texmf](texmf/)
contains sources for a texmf tree.

![](src/images/folder-blue.png) [tools](tools/)
contains some tools.

