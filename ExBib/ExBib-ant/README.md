

The εχBib Ant
=============

εχBib is a bibliography processor in the spirit of
B[IB]{.small}TeX. The component which provides access to the
core functionality via the BSF framework is contained in this module.
This can be used to write bibliography styles in other languages beside
the BST language.

Compiling εχBib Ant
-------------------

εχBib Ant can be created in this directory with the help of Maven:

      # (cd ../../ExTeX-resource; mvn install)
      # (cd ../ExBib-core; mvn install)
      # mvn package

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

``` {.output}
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Building ExBib Ant
[INFO]    task-segment: [install]
[INFO] ------------------------------------------------------------------------
[INFO] [resources:resources]
[INFO] Using default encoding to copy filtered resources.
[INFO] [compiler:compile]
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESSFUL
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 6 seconds
[INFO] Finished at: Tue Sep 30 13:23:49 CEST 2008
[INFO] Final Memory: 8M/14M
[INFO] ------------------------------------------------------------------------
```

After a successful completion of the command the documentation can be
found as the file `target/ExBib-ant-0.1-SNAPSHOT.jar`.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

εχBib Ant is released under the [GNU Library General Public
License](LICENSE.md). If you are using εχBib Ant you have to be aware
that some additional modules are needed which come with their own
licenses. Mainly the [Apache License, Version
2.0](http://www.apache.org/licenses/LICENSE-2.0.html) and the BSD
license are involved.

© 2008-2011 [The εχTeX Group](mailto:extex@dante.de)
