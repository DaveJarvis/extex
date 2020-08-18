εχTeX
=================

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

Purpose
-------------------------------

The goal of this fork is to ascertain whether real-time rendering of
TeX into either MathML or SVG is feasible.

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

Licenses
------------------------

εχTeX is developed under an Open Source license. It is meant
to be free. Thus the [GNU Library General Public License](LICENSE.md)
has been chosen.

εχTeX uses some libraries. They usually come with a license
of their own. Thus watch out to respect those licenses as well.

εχTeX contains some documentation. It is distributed under
the [GNU Free Documentation License](doc/LICENSE.html).

File Organization
-----------------

* ![](src/images/folder-blue.png) [CLI](CLI/)
contains a general purpose library for command line parsing and
processing.

* ![](src/images/folder-blue.png) [ExBib](ExBib/README.html)
εχTeX has a sub-project εχBib which aims at providing a
bibliography processor. εχBib can be found in the sub-directory
[`ExBib`](ExBib).

* ![](src/images/folder-blue.png) [ExIndex](ExIndex/README.html)
εχTeX has a sub-project εχIndex which aims at providing an
index processor. εχIndex can be found in the sub-directory
[`ExIndex`](ExIndex).

* ![](src/images/folder-blue.png) [ExTeX](ExTeX/)
εχTeX has the primary goal to provide a typesetter in the
spirit of TeX. This directory contains the specific modules for
this program.

* ![](src/images/folder-blue.png) [ExTeX-resource](ExTeX-resource/)
contains a library for resource loading and several supporting
functionality for components and configuration.

* ![](src/images/folder-blue.png) [Sandbox](Sandbox/)
contains sand boxes of developers. Here code can be put under version
control without integrating it into the official project tree.

* ![](src/images/folder-blue.png) [build](build/)
contains the [Maven](http://maven.apache.org)-based build system. It
also contains some fragments from the old
[Ant](http://ant.apache.org)-based build system which is now outdated,
does not work any more, and will sooner or later be overcome completely.

* ![](src/images/folder-blue.png) [develop](develop/)
contains configurations and tools for the development.

* ![](src/images/folder-blue.png) [doc](doc/)
contains documentation for the overall project and εχTeX
itself. Additional documentation can be found in the sub-projects.

* ![](src/images/folder-blue.png) [library](library/)
contains the library of collected papers and documents.

* ![](src/images/folder-blue.png) [maven2](maven2/)
contains [Maven](http://maven.apache.org) projects used in
εχTeX which do not have a public site of their own.

* ![](src/images/folder-blue.png) [site](site/)
contains the relaunch of the web pages on
[www.extex.org](http://www.extex.org/). It is not active yet.

* ![](src/images/folder-blue.png) [src](src/) contains additional
sources. Thus we follow the conventions of Maven.

![+](src/images/folder-gray.png) [target](target/)
is not contained in the repository. It is created on the fly during
compilation to receive the intermediate files and results.

* ![](src/images/folder-blue.png) [texmf](texmf/)
contains sources for a texmf tree.

* ![](src/images/folder-blue.png) [tools](tools/)
contains some tools.

* ![](src/images/folder-blue.png) [util](util/)
contains some utilities. Usually they are shell scripts or programs of
some sort which do not need the global build system of
εχTeX.

* ![](src/images/folder-blue.png) [www](www/)
contains the sources for the web pages for
[www.extex.org](http://www.extex.org/).

Compiling εχTeX or Sub-Projects
-------------------------------------------

εχTeX is organized in a bunch of separate Maven modules.
This is driven by the design goal of configurability: The modules can be
be assembled in various ways.

The downside is that these modules need to be compiled. This is
accomplished by Maven.

As a result the compiled classes can be found in the various
subdirectories `target/classes` of the Maven modules.

εχTeX is organized into the sub-projects `ExTeX`, `ExBib`,
and `ExIndex`. These sub-projects are organized in a way that they can
be compiled independently. Consult the ReadMe files in the sub-projects
to get instructions to compile them separately.

In a similar way you can get the jar files for all modules with the
following command:

    -> mvn package

![Warning:](src/images/warn.png) εχTeX is still in
development. Thus you should not be surprised to get test failures when
running the tests.

The test failures are an indication, where the development needs to
continue. If you are willing to continue instruct Maven to ignore them.

