εχTeX Build
=================

Getting Started
-------------------------------

* ![](src/images/folder-blue.png) [build](build/)
contains the [Maven](http://maven.apache.org)-based build system. It
also contains some fragments from the old
[Ant](http://ant.apache.org)-based build system which is now outdated,
does not work any more, and will sooner or later be overcome completely.

* ![](src/images/folder-blue.png) [develop](develop/)
contains configurations and tools for the development.

* ![](src/images/folder-blue.png) [maven2](maven2/)
contains [Maven](http://maven.apache.org) projects used in
εχTeX which do not have a public site of their own.

* ![](src/images/folder-blue.png) [src](src/) contains additional
sources. Thus we follow the conventions of Maven.

Compiling εχTeX
-------------------------------------------

εχTeX is organized in a bunch of separate Maven modules.
This is driven by the design goal of configurability: The modules can be
be assembled in various ways.

The downside is that these modules need to be compiled. This is
accomplished by Maven.

As a result the compiled classes can be found in the various
subdirectories `target/classes` of the Maven modules.

In a similar way you can get the jar files for all modules with the
following command:

    mvn package

Compiling Sub-Projects
-------------------------------------------

εχTeX is organized into the sub-projects `ExTeX`, `ExBib`,
and `ExIndex`. These sub-projects are organized in a way that they can
be compiled independently. Consult the README files in the sub-projects
to get instructions to compile them separately.

Testing εχTeX
-------------------------------------------

![Warning:](src/images/warn.png) εχTeX is still in
development. Thus you should not be surprised to get test failures when
running the tests.

The test failures are an indication, where the development needs to
continue. If you are willing to continue instruct Maven to ignore them.

