

The εχT[e]{.e}X Back-End DVIx
=============================

The εχT[e]{.e}X Back-End DVIx provides a εχT[e]{.e}X document writer
module producing the DeVice Independent file format as defined by Donald
Knuth.

Compiling and Packaging the εχT[e]{.e}X Back-End DVIx
-----------------------------------------------------

The εχT[e]{.e}X Back-End DVIx can be created in this directory with the
help of Maven. To resolve all dependencies it is best to compile in the
parent\'s parent directory:

      # (cd ../..; mvn compile)

Or the following command to compile and package:

      # (cd ../..; mvn package)

These commands require that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path of the shell.

``` {.output}
[INFO] Scanning for projects...
...                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Backend dvix 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
...
```

After a successful completion of the command the jar can be found as the
file `target/ExTeX-Backend-dvix-0.1-SNAPSHOT.jar`.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

The εχT[e]{.e}X Back-End DVIx is released under the [GNU Library General
Public License](LICENSE.html).

© 2011 [The εχTeX Group](mailto:extex@dante.de)
