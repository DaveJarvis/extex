![](src/images/ExTeX-side.png){.left}

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

Getting Started -- with Reading
-------------------------------

If you are interested in using εχ[T]{.t}[e]{.e}X you should have a look
at the *εχ[T]{.t}[e]{.e}X User\'s Guide*. The source code for the
user\'s guide can be found in the directory
[`../doc/UsersGuide`](../doc/UsersGuide).

Compiling εχ[T]{.t}[e]{.e}X
---------------------------

εχ[T]{.t}[e]{.e}X can be created in this directory with the help of
Maven:

      # mvn compile

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

``` {.output}
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO] 
[INFO] Command Line Interface
[INFO] ExTeX Framework and Resource Component
[INFO] ExTeX
[INFO] ExTeX core components
[INFO] ExTeX Font System API
[INFO] ExTeX Scanner API
[INFO] ExTeX Typesetter API
[INFO] ExTeX Interpreter API
[INFO] ExTeX Scanner
[INFO] ExTeX Typesetter TeX
[INFO] ExTeX base
[INFO] ExTeX Backend Dump
[INFO] ExTeX Backend DVI
[INFO] ExTeX Backend DVIx
[INFO] ExTeX Backend PS
[INFO] ExTeX Backend RTF
[INFO] ExTeX Backend SVG
[INFO] ExTeX Backend Text
[INFO] ExTeX Backend XML
[INFO] ExTeX Typesetter core
[INFO] ExTeX Interpreter max
[INFO] ExTeX base test
[INFO] ExTeX Unit TeX
[INFO] ExTeX Unit Native
[INFO] ExTeX Base Ext
[INFO] ExTeX Font Test Support
[INFO] ExTeX AFM Font System
[INFO] ExTeX TFM Font System
[INFO] ExTeX OTF Font System
[INFO] ExTeX Font Utilities
[INFO] ExTeX Unit eTeX
[INFO] ExTeX Unit Namespace
[INFO] ExTeX Unit ExTeX
[INFO] ExTeX Pdf API
[INFO] ExTeX Unit Pdftex
[INFO] ExTeX OCPware
[INFO] ExTeX Unit omega
[INFO] ExTeX Unit Color
[INFO] ExTeX Format Utility Program
[INFO] ExTeX TeX Main Program
[INFO] ExTeX Scanner 32
[INFO] ExTeX Unit Image
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building Command Line Interface 0.1
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ CLI ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 9 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ CLI ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Framework and Resource Component 0.1
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-resource ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 20 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-resource ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX core components 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-core ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 31 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-core ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Font System API 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Font-API ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 4 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Font-API ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Scanner API 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Scanner-API ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 7 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Scanner-API ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Typesetter API 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Typesetter-API ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 31 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Typesetter-API ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Interpreter API 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Interpreter-API ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 6 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Interpreter-API ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Scanner 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Scanner ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Scanner ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Typesetter TeX 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Typesetter-tex ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 7 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Typesetter-tex ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX base 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-base ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 39 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-base ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Backend Dump 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Backend-dump ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 5 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Backend-dump ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Backend DVI 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Backend-dvi ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 3 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Backend-dvi ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Backend DVIx 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Backend-dvix ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 2 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Backend-dvix ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Backend PS 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Backend-ps ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 6 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Backend-ps ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Backend RTF 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Backend-rtf ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 2 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Backend-rtf ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Backend SVG 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Backend-svg ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Backend-svg ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Backend Text 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Backend-text ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 2 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Backend-text ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Backend XML 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Backend-xml ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 2 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Backend-xml ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Typesetter core 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Typesetter-core ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 0 resource
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Typesetter-core ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Interpreter max 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Interpreter-max ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 0 resource
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Interpreter-max ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX base test 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-base-test ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 0 resource
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-base-test ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Unit TeX 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Unit-tex ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 99 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Unit-tex ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Unit Native 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Unit-native ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 10 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Unit-native ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Base Ext 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-base-ext ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 6 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-base-ext ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Font Test Support 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Font-test ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 0 resource
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Font-test ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX AFM Font System 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Font-afm ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Font-afm ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX TFM Font System 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Font-tfm ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 7 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Font-tfm ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX OTF Font System 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Font-otf ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 0 resource
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Font-otf ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Font Utilities 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-fontware ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 5 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-fontware ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Unit eTeX 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Unit-etex ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 15 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Unit-etex ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Unit Namespace 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Unit-namespace ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 4 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Unit-namespace ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Unit ExTeX 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Unit-extex ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 6 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Unit-extex ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Pdf API 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Pdf-API ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 10 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Pdf-API ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Unit Pdftex 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Unit-pdftex ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 4 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Unit-pdftex ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX OCPware 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-ocpware ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 38 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-ocpware ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Unit omega 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Unit-omega ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 14 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Unit-omega ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Unit Color 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Unit-color ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 5 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Unit-color ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Format Utility Program 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Main-fmt ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 0 resource
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Main-fmt ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX TeX Main Program 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Main-tex ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 31 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Main-tex ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Scanner 32 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Scanner32 ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Scanner32 ---
[INFO] Nothing to compile - all classes are up to date
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Unit Image 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-Unit-image ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 0 resource
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-Unit-image ---
[INFO] Nothing to compile - all classes are up to date
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary:
[INFO] 
[INFO] Command Line Interface ............................ SUCCESS [0.497s]
[INFO] ExTeX Framework and Resource Component ............ SUCCESS [0.823s]
[INFO] ExTeX ............................................. SUCCESS [0.000s]
[INFO] ExTeX core components ............................. SUCCESS [0.603s]
[INFO] ExTeX Font System API ............................. SUCCESS [0.414s]
[INFO] ExTeX Scanner API ................................. SUCCESS [0.450s]
[INFO] ExTeX Typesetter API .............................. SUCCESS [1.408s]
[INFO] ExTeX Interpreter API ............................. SUCCESS [0.868s]
[INFO] ExTeX Scanner ..................................... SUCCESS [0.108s]
[INFO] ExTeX Typesetter TeX .............................. SUCCESS [0.424s]
[INFO] ExTeX base ........................................ SUCCESS [1.383s]
[INFO] ExTeX Backend Dump ................................ SUCCESS [0.133s]
[INFO] ExTeX Backend DVI ................................. SUCCESS [0.217s]
[INFO] ExTeX Backend DVIx ................................ SUCCESS [0.119s]
[INFO] ExTeX Backend PS .................................. SUCCESS [0.293s]
[INFO] ExTeX Backend RTF ................................. SUCCESS [0.168s]
[INFO] ExTeX Backend SVG ................................. SUCCESS [0.111s]
[INFO] ExTeX Backend Text ................................ SUCCESS [0.114s]
[INFO] ExTeX Backend XML ................................. SUCCESS [0.156s]
[INFO] ExTeX Typesetter core ............................. SUCCESS [0.093s]
[INFO] ExTeX Interpreter max ............................. SUCCESS [0.095s]
[INFO] ExTeX base test ................................... SUCCESS [0.218s]
[INFO] ExTeX Unit TeX .................................... SUCCESS [2.014s]
[INFO] ExTeX Unit Native ................................. SUCCESS [0.189s]
[INFO] ExTeX Base Ext .................................... SUCCESS [0.685s]
[INFO] ExTeX Font Test Support ........................... SUCCESS [0.105s]
[INFO] ExTeX AFM Font System ............................. SUCCESS [0.185s]
[INFO] ExTeX TFM Font System ............................. SUCCESS [0.610s]
[INFO] ExTeX OTF Font System ............................. SUCCESS [0.991s]
[INFO] ExTeX Font Utilities .............................. SUCCESS [0.224s]
[INFO] ExTeX Unit eTeX ................................... SUCCESS [1.115s]
[INFO] ExTeX Unit Namespace .............................. SUCCESS [0.195s]
[INFO] ExTeX Unit ExTeX .................................. SUCCESS [0.239s]
[INFO] ExTeX Pdf API ..................................... SUCCESS [0.356s]
[INFO] ExTeX Unit Pdftex ................................. SUCCESS [0.174s]
[INFO] ExTeX OCPware ..................................... SUCCESS [0.785s]
[INFO] ExTeX Unit omega .................................. SUCCESS [0.454s]
[INFO] ExTeX Unit Color .................................. SUCCESS [0.155s]
[INFO] ExTeX Format Utility Program ...................... SUCCESS [0.105s]
[INFO] ExTeX TeX Main Program ............................ SUCCESS [0.366s]
[INFO] ExTeX Scanner 32 .................................. SUCCESS [0.125s]
[INFO] ExTeX Unit Image .................................. SUCCESS [0.097s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 18.210s
[INFO] Finished at: Mon Apr 25 09:39:36 CEST 2011
[INFO] Final Memory: 5M/15M
[INFO] ------------------------------------------------------------------------
```

After a successful completion of the command the class files can be
found in the directories `target/classes` in the respective module
directories.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

Licenses -- Legal Issues
------------------------

εχ[T]{.t}[e]{.e}X is developed under an Open Source license. It is meant
to be free. Thus the [GNU Library General Public License](LICENSE.txt)
has been chosen.

εχ[T]{.t}[e]{.e}X uses some libraries. They usually come with a license
of their own. Thus watch out to respect those licenses as well.

File Organization
-----------------

::: {.dir}
![](src/images/folder-magenta.png) Backend

::: {.dir2}
![](src/images/folder-blue.png)
[ExTeX-Backend-dump](Backend/ExTeX-Backend-dump/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Backend-dvi](Backend/ExTeX-Backend-dvi/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Backend-dvix](Backend/ExTeX-Backend-dvix/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Backend-iText](Backend/ExTeX-Backend-iText/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Backend-pdfbox](Backend/ExTeX-Backend-pdfbox/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Backend-ps](Backend/ExTeX-Backend-ps/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Backend-rtf](Backend/ExTeX-Backend-rtf/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Backend-svg](Backend/ExTeX-Backend-svg/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Backend-text](Backend/ExTeX-Backend-text/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Backend-xml](Backend/ExTeX-Backend-xml/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[Test-Backend-iText](Backend/Test-Backend-iText/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[Test-Backend-xml](Backend/Test-Backend-xml/)
:::
:::

::: {.dir}
![](src/images/folder-blue.png) [ExTeX-base](ExTeX-base/)
:::

::: {.dir}
![](src/images/folder-blue.png) [ExTeX-base-ext](ExTeX-base-ext/)
:::

::: {.dir}
![](src/images/folder-blue.png) [ExTeX-base-test](ExTeX-base-test/)
:::

::: {.dir}
![](src/images/folder-blue.png) [ExTeX-core](ExTeX-core/)
:::

::: {.dir}
![](src/images/folder-magenta.png) Font

::: {.dir}
![](src/images/folder-blue.png) [ExTeX-Font-API](Font/ExTeX-Font-API/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Font-API-Test](Font/ExTeX-Font-API-Test/)
:::

::: {.dir}
![](src/images/folder-blue.png) [ExTeX-Font-afm](Font/ExTeX-Font-afm/)
:::

::: {.dir}
![](src/images/folder-blue.png) [ExTeX-Font-otf](Font/ExTeX-Font-otf/)
:::

::: {.dir}
![](src/images/folder-blue.png) [ExTeX-Font-tfm](Font/ExTeX-Font-tfm/)
:::

::: {.dir}
![](src/images/folder-blue.png) [ExTeX-fontware](Font/ExTeX-fontware/)
:::

::: {.dir}
![](src/images/folder-blue.png) [Test-Font-API](Font/Test-Font-API/)
:::
:::

::: {.dir}
![](src/images/folder-magenta.png) Interpreter

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Interpreter-API](Interpreter/ExTeX-Interpreter-API/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Interpreter-max](Interpreter/ExTeX-Interpreter-max/)
:::
:::

::: {.dir}
![](src/images/folder-blue.png) [ExTeX-Main-fmt](ExTeX-Main-fmt/)
:::

::: {.dir}
![](src/images/folder-blue.png) [ExTeX-Main-tex](ExTeX-Main-tex/)
:::

::: {.dir}
![](src/images/folder-blue.png) [ExTeX-ocpware](ExTeX-ocpware/)
:::

::: {.dir}
![](src/images/folder-blue.png) [ExTeX-Pdf-API](ExTeX-Pdf-API/)
:::

::: {.dir}
![](src/images/folder-magenta.png) Scanner

::: {.dir}
![](src/images/folder-blue.png) [ExTeX-Scanner](Scanner/ExTeX-Scanner/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Scanner-API](Scanner/ExTeX-Scanner-API/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Scanner32](Scanner/ExTeX-Scanner32/)
:::
:::

::: {.dir}
![](src/images/folder-magenta.png) Typesetter

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Typesetter-API](Typesetter/ExTeX-Typesetter-API/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Typesetter-core](Typesetter/ExTeX-Typesetter-core/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Typesetter-tex](Typesetter/ExTeX-Typesetter-tex/)
:::
:::

::: {.dir}
![](src/images/folder-magenta.png) Unit

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Unit-color](Unit/ExTeX-Unit-color/)
:::

::: {.dir}
![](src/images/folder-blue.png) [ExTeX-Unit-etex](Unit/ExTeX-Unit-etex/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Unit-extex](Unit/ExTeX-Unit-extex/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Unit-image](Unit/ExTeX-Unit-image/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Unit-namespace](Unit/ExTeX-Unit-namespace/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Unit-native](Unit/ExTeX-Unit-native/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Unit-omega](Unit/ExTeX-Unit-omega/)
:::

::: {.dir}
![](src/images/folder-blue.png)
[ExTeX-Unit-pdftex](Unit/ExTeX-Unit-pdftex/)
:::

::: {.dir}
![](src/images/folder-blue.png) [ExTeX-Unit-tex](Unit/ExTeX-Unit-tex/)
:::
:::

© 2009-2011 [The εχ[T]{.t}[e]{.e}X Group](mailto:extex@dante.de)
