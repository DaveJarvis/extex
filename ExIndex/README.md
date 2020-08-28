Building ExIndex
================

ExIndex uses Maven for its build system. Thus some pieces of software
needs to be installed before ExIndex can be build.

Prerequisites
-------------

The following software needs to be installed and properly configured:

-   Java (<http://java.sun.com/javase/downloads/index_jdk5.jsp>) at
    least in Version 5
-   Maven (<http://maven.apache.org/>)

The configuration for short:

-   Java (java) needs to be on the `PATH`
-   The environment variable `JAVA_HOME` needs to point to the
    installation directory of Java.
-   Maven (mvn) needs to be on the `PATH`
-   The environment variable `MAVEN_HOME` needs to point to the
    installation directory of Maven.
-   Maven needs to be configured to have access to the internet
    (especially proxy settings if required).

Creating the Installer
----------------------

It is assumed that the current directory is ExIndex and some kind of
shell is at hand. Then submit the following command:

    mvn package   

The resulting installer is called `ExIndex-installer-0.1.jar`. It can be
foun din the directory `ExIndex-Main/target`.

If some of the tests fail you can omit the testing phase:

    mvn -Dmaven.test.skip=true package   

Running the Installer
=====================

The installer can be run with the following command line invocation:

    java  ExIndex-installer-0.1.jar  

Alternatively double-clicking on the jar may have the same effect. This
depends on the system you are using and its configuration.

The installer has a grafical user interface which leads you through the
further installation steps.

Running ExIndex
===============

ExIndex is a command line program which comes with wrapper scripts for
Unix and Windows.

    exindex --help  

    exindex.bat --help  

© 2007 [Gerd Neugebauer](mailto:gene@gerd-neugebauer.de)
