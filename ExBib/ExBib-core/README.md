

The εχBib Core
==============

εχBib is a bibliography processor in the spirit of
B[IB]{.small}TeX. The core component is contained in this
module.

Compiling the εχBib Core
------------------------

The εχBib core can be created in this directory with the help of Maven:

      # (cd ../../ExTeX-resource; mvn install)
      # mvn compile

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

``` {.output}
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Building ExBib core
[INFO]    task-segment: [package]
[INFO] ------------------------------------------------------------------------
[INFO] [resources:resources]
[INFO] Using default encoding to copy filtered resources.
[INFO] [compiler:compile]
[INFO] Nothing to compile - all classes are up to date
[INFO] [resources:testResources]
[INFO] Using default encoding to copy filtered resources.
[INFO] [compiler:testCompile]
[INFO] Nothing to compile - all classes are up to date
[INFO] [surefire:test]
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESSFUL
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 9 seconds
[INFO] Finished at: Sat Sep 06 13:28:32 CEST 2008
[INFO] Final Memory: 4M/8M
[INFO] ------------------------------------------------------------------------
```

After a successful completion of the command the jar can be found as the
file `target/ExBib-core-0.1-SNAPSHOT.jar`.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

εχBib is released under the [GNU Library General Public
License](LICENSE.md).

© 2008-2011 [The εχTeX Group](mailto:extex@dante.de)
