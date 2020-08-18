![ExTeX](src/images/ExTeX-side.png){.left}
![](src/images/eli-256.png){.right}

εχ[T]{.t}[e]{.e}X
=================

εχ[T]{.t}[e]{.e}X aims at the development of a high quality typesetting
system. This development is massively based on the experiences with the
typesetting system T[e]{.e}X. Despite of its age T[e]{.e}X can still be
considered a very good choice. Nevertheless design decisions which where
reasonable at the time of the writing of T[e]{.e}X can nowadays no
longer be considered as state of the art.

The stability of T[e]{.e}X is one of its virtues. On the other side it
makes it hard to improve the system -- even in the few areas which
deserve improvements. The new millenium needs a system which is open for
further development and at the same time compatible with T[e]{.e}X as
much as reasonable.

εχ[T]{.t}[e]{.e}X on the Web
----------------------------

εχ[T]{.t}[e]{.e}X is present on the Web with the Web site at
[www.extex.org](http://www.extex.org/). This Web site is hosted by
[DANTE e.V.](http://www.dante.de)

The source for this site can be found in the directory [`www`](www/).
The site is regenerated from these source each night.

Getting Started -- with Reading
-------------------------------

If you are interested in using εχ[T]{.t}[e]{.e}X you should have a look
at the *εχ[T]{.t}[e]{.e}X User\'s Guide*. The source code for the
user\'s guide can be found in the directory
[`doc/UsersGuide`](doc/UsersGuide).

If you are interested in the sources you should have a look at the
*Developer\'s Guide*. The source code can be found in
[`doc/DevelopersGuide`](doc/DevelopersGuide). A compiled version can be
downloaded from the [εχ[T]{.t}[e]{.e}X web site](http://www.extex.org/).

Licenses -- Legal Issues
------------------------

εχ[T]{.t}[e]{.e}X is developed under an Open Source license. It is meant
to be free. Thus the [GNU Library General Public License](LICENSE.txt)
has been chosen.

εχ[T]{.t}[e]{.e}X uses some libraries. They usually come with a license
of their own. Thus watch out to respect those licenses as well.

εχ[T]{.t}[e]{.e}X contains some documentation. It is distributed under
the [GNU Free Documentation License](doc/LICENSE.html).

File Organization
-----------------

![\*](src/images/folder-blue.png) [CLI](CLI/)\
contains a general purpose library for command line parsing and
processing.

![\*](src/images/folder-blue.png) [ExBib](ExBib/README.html)\
εχ[T]{.t}[e]{.e}X has a sub-project εχBib which aims at providing a
bibliography processor. εχBib can be found in the sub-directory
[`ExBib`](ExBib).

![\*](src/images/folder-blue.png) [ExIndex](ExIndex/README.html)\
εχ[T]{.t}[e]{.e}X has a sub-project εχIndex which aims at providing an
index processor. εχIndex can be found in the sub-directory
[`ExIndex`](ExIndex).

![\*](src/images/folder-blue.png) [ExTeX](ExTeX/)\
εχ[T]{.t}[e]{.e}X has the primary goal to provide a typesetter in the
spirit of T[e]{.e}X. This directory contains the specific modules for
this program.

![\*](src/images/folder-blue.png) [ExTeX-resource](ExTeX-resource/)\
contains a library for resource loading and several supporting
functionality for components and configuration.

![\*](src/images/folder-blue.png) [Sandbox](Sandbox/)\
contains sand boxes of developers. Here code can be put under version
control without integrating it into the official project tree.

![\*](src/images/folder-blue.png) [build](build/)\
contains the [Maven](http://maven.apache.org)-based build system. It
also contains some fragments from the old
[Ant](http://ant.apache.org)-based build system which is now outdated,
does not work any more, and will sooner or later be overcome completely.

![\*](src/images/folder-blue.png) [develop](develop/)\
contains configurations and tools for the development.

![\*](src/images/folder-blue.png) [doc](doc/)\
contains documentation for the overall project and εχ[T]{.t}[e]{.e}X
itself. Additional documentation can be found in the sub-projects.

![\*](src/images/folder-blue.png) [library](library/)\
contains the library of collected papers and documents.

![\*](src/images/folder-blue.png) [maven2](maven2/)\
contains [Maven](http://maven.apache.org) projects used in
εχ[T]{.t}[e]{.e}X which do not have a public site of their own. The
contents is directly published each night under the URL
<http://www.extex.org/maven2/>.

![\*](src/images/folder-blue.png) [site](site/)\
contains the relaunch of the web pages on
[www.extex.org](http://www.extex.org/). It is not active yet.

![\*](src/images/folder-blue.png) [src](src/) contains additional
sources. Thus we follow the conventions of Maven.

![+](src/images/folder-gray.png) [target](target/)\
is not contained in the repository. It is created on the fly during
compilation to receive the intermediate files and results.

![\*](src/images/folder-blue.png) [texmf](texmf/)\
contains sources for a texmf tree.

![\*](src/images/folder-blue.png) [tools](tools/)\
contains some tools.

![\*](src/images/folder-blue.png) [util](util/)\
contains some utilities. Usually they are shell scripts or programs of
some sort which do not need the global build system of
εχ[T]{.t}[e]{.e}X.

![\*](src/images/folder-blue.png) [www](www/)\
contains the sources for the web pages for
[www.extex.org](http://www.extex.org/).

Compiling εχ[T]{.t}[e]{.e}X or Sub-Projects
-------------------------------------------

εχ[T]{.t}[e]{.e}X is organized in a bunch of separate Maven modules.
This is driven by the design goal of configurability: The modules can be
be assembled in various ways.

The downside is that these modules need to be compiled. This is
accomplished by Maven.

    -> mvn compile
    [INFO] Scanning for projects...
    [INFO] ------------------------------------------------------------------------
    [INFO] Reactor Build Order:
    [INFO] 
    [INFO] ExTeX Development Resources
    [INFO] The ExTeX Project
    [INFO] ExTeX Build System
    [INFO] ExTeX texmf tree
    [INFO] ExTeX Maven Skin
    [INFO] ExTeX Tools
    [INFO] Command Line Interface
    [INFO] ExTeX Resource and Framework
    [INFO] ExTeX
    [INFO] ExTeX Core
    [INFO] ExTeX Font System API
    [INFO] ExTeX Scanner API
    [INFO] ExTeX Typesetter API
    [INFO] ExTeX Interpreter API
    [INFO] ExTeX Scanner
    [INFO] ExTeX Typesetter TeX
    [INFO] ExTeX Base
    [INFO] ExTeX Backend Dump
    [INFO] ExTeX Backend DVI
    [INFO] ExTeX Backend DVIx
    [INFO] ExTeX Pdf API
    [INFO] ExTeX Backend iText
    [INFO] ExTeX Backend PDFBox
    [INFO] ExTeX Backend PS
    [INFO] ExTeX Backend RTF
    [INFO] ExTeX Backend SVG
    [INFO] ExTeX Backend Text
    [INFO] ExTeX Backend XML
    [INFO] ExTeX Typesetter core
    [INFO] ExTeX Interpreter max
    [INFO] ExTeX base test
    [INFO] ExTeX Font Test Support
    [INFO] ExTeX AFM Font System
    [INFO] ExTeX TFM Font System
    [INFO] ExTeX Unit TeX
    [INFO] ExTeX Unit Native
    [INFO] ExTeX Base Ext
    [INFO] ExTeX OTF Font System
    [INFO] ExTeX Font Utilities
    [INFO] ExTeX Unit Namespace
    [INFO] ExTeX Unit ExTeX
    [INFO] ExTeX Unit eTeX
    [INFO] ExTeX Unit pdfTeX
    [INFO] ExTeX OCPware
    [INFO] ExTeX Unit Omega
    [INFO] ExTeX Unit Color
    [INFO] ExTeX Format Utility Program
    [INFO] ExTeX TeX Main Program
    [INFO] ExTeX Scanner 32
    [INFO] ExTeX Unit Image
    [INFO] ExBib
    [INFO] ExBib Core
    [INFO] ExBib Command Line
    [INFO] ExBib BSF Adaptor
    [INFO] ExBib Groovy
    [INFO] ExBib BST to Groovy Compiler
    [INFO] ExBib Jacl
    [INFO] ExBib Jython
    [INFO] ExBib Ant Task
    [INFO] ExBib texmf
    [INFO] ExBib Utility Program
    [INFO] ExTeX Developer's Site
    [INFO]                                                                         
    [INFO] ------------------------------------------------------------------------
    [INFO] Building ExTeX Development Resources 1.4
    [INFO] ------------------------------------------------------------------------
    [INFO] 
    [INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ develop ---
    [INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
    [INFO] Copying 3 resources
    [INFO] 
    [INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ develop ---
    [INFO] No sources to compile
    [INFO]                                                                         
    ...
    [INFO] ------------------------------------------------------------------------
    [INFO] Reactor Summary:
    [INFO] 
    [INFO] ExTeX Development Resources ....................... SUCCESS [2.400s]
    [INFO] The ExTeX Project ................................. SUCCESS [0.001s]
    [INFO] ExTeX Build System ................................ SUCCESS [0.000s]
    [INFO] ExTeX texmf tree .................................. SUCCESS [0.000s]
    [INFO] ExTeX Maven Skin .................................. SUCCESS [0.207s]
    [INFO] ExTeX Tools ....................................... SUCCESS [0.001s]
    [INFO] Command Line Interface ............................ SUCCESS [0.515s]
    [INFO] ExTeX Resource and Framework ...................... SUCCESS [0.542s]
    [INFO] ExTeX ............................................. SUCCESS [0.002s]
    [INFO] ExTeX Core ........................................ SUCCESS [0.804s]
    [INFO] ExTeX Font System API ............................. SUCCESS [0.549s]
    [INFO] ExTeX Scanner API ................................. SUCCESS [0.734s]
    [INFO] ExTeX Typesetter API .............................. SUCCESS [5.532s]
    [INFO] ExTeX Interpreter API ............................. SUCCESS [1.006s]
    [INFO] ExTeX Scanner ..................................... SUCCESS [0.603s]
    [INFO] ExTeX Typesetter TeX .............................. SUCCESS [0.533s]
    [INFO] ExTeX Base ........................................ SUCCESS [1.728s]
    [INFO] ExTeX Backend Dump ................................ SUCCESS [0.429s]
    [INFO] ExTeX Backend DVI ................................. SUCCESS [0.395s]
    [INFO] ExTeX Backend DVIx ................................ SUCCESS [0.331s]
    [INFO] ExTeX Pdf API ..................................... SUCCESS [0.545s]
    [INFO] ExTeX Backend iText ............................... SUCCESS [0.332s]
    [INFO] ExTeX Backend PDFBox .............................. SUCCESS [0.350s]
    [INFO] ExTeX Backend PS .................................. SUCCESS [0.291s]
    [INFO] ExTeX Backend RTF ................................. SUCCESS [0.231s]
    [INFO] ExTeX Backend SVG ................................. SUCCESS [0.221s]
    [INFO] ExTeX Backend Text ................................ SUCCESS [0.250s]
    [INFO] ExTeX Backend XML ................................. SUCCESS [0.259s]
    [INFO] ExTeX Typesetter core ............................. SUCCESS [0.178s]
    [INFO] ExTeX Interpreter max ............................. SUCCESS [0.185s]
    [INFO] ExTeX base test ................................... SUCCESS [0.427s]
    [INFO] ExTeX Font Test Support ........................... SUCCESS [0.211s]
    [INFO] ExTeX AFM Font System ............................. SUCCESS [0.315s]
    [INFO] ExTeX TFM Font System ............................. SUCCESS [0.556s]
    [INFO] ExTeX Unit TeX .................................... SUCCESS [2.535s]
    [INFO] ExTeX Unit Native ................................. SUCCESS [0.396s]
    [INFO] ExTeX Base Ext .................................... SUCCESS [1.083s]
    [INFO] ExTeX OTF Font System ............................. SUCCESS [0.528s]
    [INFO] ExTeX Font Utilities .............................. SUCCESS [0.370s]
    [INFO] ExTeX Unit Namespace .............................. SUCCESS [0.276s]
    [INFO] ExTeX Unit ExTeX .................................. SUCCESS [0.341s]
    [INFO] ExTeX Unit eTeX ................................... SUCCESS [0.767s]
    [INFO] ExTeX Unit pdfTeX ................................. SUCCESS [0.319s]
    [INFO] ExTeX OCPware ..................................... SUCCESS [0.773s]
    [INFO] ExTeX Unit Omega .................................. SUCCESS [0.837s]
    [INFO] ExTeX Unit Color .................................. SUCCESS [0.377s]
    [INFO] ExTeX Format Utility Program ...................... SUCCESS [0.207s]
    [INFO] ExTeX TeX Main Program ............................ SUCCESS [0.618s]
    [INFO] ExTeX Scanner 32 .................................. SUCCESS [0.332s]
    [INFO] ExTeX Unit Image .................................. SUCCESS [0.310s]
    [INFO] ExBib ............................................. SUCCESS [0.001s]
    [INFO] ExBib Core ........................................ SUCCESS [6.510s]
    [INFO] ExBib Command Line ................................ SUCCESS [0.370s]
    [INFO] ExBib BSF Adaptor ................................. SUCCESS [0.289s]
    [INFO] ExBib Groovy ...................................... SUCCESS [0.542s]
    [INFO] ExBib BST to Groovy Compiler ...................... SUCCESS [0.706s]
    [INFO] ExBib Jacl ........................................ SUCCESS [0.203s]
    [INFO] ExBib Jython ...................................... SUCCESS [0.143s]
    [INFO] ExBib Ant Task .................................... SUCCESS [0.311s]
    [INFO] ExBib texmf ....................................... SUCCESS [1.266s]
    [INFO] ExBib Utility Program ............................. SUCCESS [0.292s]
    [INFO] ExTeX Developer's Site ............................ SUCCESS [0.000s]
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 44.778s
    [INFO] Finished at: Fri Jun 24 12:52:39 CEST 2011
    [INFO] Final Memory: 8M/20M
    [INFO] ------------------------------------------------------------------------

As a result the compiled classes can be found in the various
subdirectories `target/classes` of the Maven modules.

εχ[T]{.t}[e]{.e}X is organized into the sub-projects `ExTeX`, `ExBib`,
and `ExIndex`. These sub-projects are organized in a way that they can
be compiled independently. Consult the ReadMe files in the sub-projects
to get instructions to compile them separately.

In a similar way you can get the jar files for all modules with the
following command:

    -> mvn package

![Warning:](src/images/warn.png) εχ[T]{.t}[e]{.e}X is still in
development. Thus you should not be surprised to get test failures when
running the tests.

The test failures are an indication, where the development needs to
continue. If you are willing to continue instruct Maven to ignore them.

© 2011 [The εχ[T]{.t}[e]{.e}X Group](mailto:extex@dante.de)
