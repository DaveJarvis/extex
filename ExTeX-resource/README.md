

εχTeX resource
==========================

εχTeX resource is a base component which contains various
helpful classes for dealing with components.

Compiling εχTeX resource
------------------------------------

εχTeX resource can be created in this directory with the
help of Maven:

      # mvn package

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

``` {.output}
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX resource component
[INFO]    task-segment: [package]
[INFO] ------------------------------------------------------------------------
[INFO] [resources:resources {execution: default-resources}]
[WARNING] Using platform encoding (Cp1252 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] Copying 19 resources
[INFO] [compiler:compile {execution: default-compile}]
[INFO] Compiling 59 source files to c:\home\gene\src\ExTeX-Workspace\extex\ExTeX-resource\target\classes
[INFO] [resources:testResources {execution: default-testResources}]
[WARNING] Using platform encoding (Cp1252 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] Copying 5 resources
[INFO] [compiler:testCompile {execution: default-testCompile}]
[INFO] Compiling 5 source files to c:\home\gene\src\ExTeX-Workspace\extex\ExTeX-resource\target\test-classes
[INFO] [surefire:test {execution: default-test}]
[INFO] Surefire report directory: c:\home\gene\src\ExTeX-Workspace\extex\ExTeX-resource\target\surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running org.extex.resource.ClasspathArchiveFinderTest
Tests run: 7, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.171 sec
Running org.extex.resource.UrlFinderTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.079 sec
Running org.extex.resource.tool.TocIndexTest
Tests run: 18, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.14 sec
Running org.extex.framework.configuration.impl.TexConfigurationTest
Tests run: 34, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.11 sec
Running org.extex.framework.configuration.ConfigurationFactoryTest
[Fatal Error] :1:1: Premature end of file.
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.171 sec

Results :

Tests run: 67, Failures: 0, Errors: 0, Skipped: 2

[INFO] [jar:jar {execution: default-jar}]
[INFO] Building jar: c:\home\gene\src\ExTeX-Workspace\extex\ExTeX-resource\target\ExTeX-resource-0.1.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESSFUL
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 12 seconds
[INFO] Finished at: Sun Oct 31 22:26:40 CET 2010
[INFO] Final Memory: 12M/21M
[INFO] ------------------------------------------------------------------------
```

After a successful completion of the command the documentation can be
found as the file `target/ExTeX-resource-0.1.jar`.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

εχTeX resource is released under the [GNU Library General
Public License](LICENSE.md).

© 2003-2011 [The εχTeX Group](mailto:extex@dante.de)
