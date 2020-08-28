

The εχTeX Backend dump
============================

This component contains a document writer which simply writes the nodes
to an output file. This is meant for debugging and testing.

Compiling εχTeX Backend dump
----------------------------------

The εχTeX Backend dump component can be created in this directory
with the help of Maven:

      # mvn package

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

``` {.output}
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Backend Dump 0.1-SNAPSHOT
[INFO]    task-segment: [package]
[INFO] ------------------------------------------------------------------------
...
```

After a successful completion of the command the jar can be found as the
file `target/ExTeX-Backend-dump-0.1-SNAPSHOT.jar`.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

εχTeX CLI is released under the [GNU Library General Public
License](LICENSE.md).

© 2011 [The εχTeX Group](mailto:extex@dante.de)
