![](src/images/ExBib-Groovy-side.png){.left}

The εχBib Groovy
================

εχBib is a bibliography processor in the spirit of
B[IB]{.small}[T]{.t}[e]{.e}X. The component which provides access to the
core functionality via the BSF framework is contained in this module.
This is demonstrated by the use of Groovy as extension language instead
of the BST language.

Compiling εχBib Groovy
----------------------

εχBib Groovy can be created in this directory with the help of Maven:

      # (cd ../../ExTeX-resource; mvn install)
      # (cd ../ExBib-core; mvn install)
      # mvn package

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

``` {.output}
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Building ExBib groovy
[INFO]    task-segment: [package]
[INFO] ------------------------------------------------------------------------
[INFO] [resources:resources]
[INFO] Using default encoding to copy filtered resources.
[INFO] [compiler:compile]
[INFO] Nothing to compile - all classes are up to date
[INFO] [resources:testResources]
[INFO] Using default encoding to copy filtered resources.
[INFO] [compiler:testCompile]
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESSFUL
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 8 seconds
[INFO] Finished at: Sat Sep 13 18:36:24 CEST 2008
[INFO] Final Memory: 11M/23M
[INFO] ------------------------------------------------------------------------
```

After a successful completion of the command the documentation can be
found as the file `target/ExBib-groovy-0.1-SNAPSHOT.jar`.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

εχBib Groovy is released under the [GNU Library General Public
License](LICENSE.html). If you are using εχBib Groovy you have to be
aware that some additional modules are needed which come with their own
licenses. Mainly the Apache License, Version 2.0 and the BSD license are
involved.

© 2008-2011 [The εχ[T]{.t}[e]{.e}X Group](mailto:extex@dante.de)
