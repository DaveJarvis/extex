

The εχTeX Back-End RTF
============================

The εχTeX Back-End RTF provides a εχTeX document writer
module producing Rich Text Format.

Compiling and Packaging the εχTeX Back-End RTF
----------------------------------------------------

The εχTeX Back-End RTF can be created in this directory with the
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
[INFO] Building ExTeX Backend RTF 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
...
```

After a successful completion of the command the jar can be found as the
file `target/ExTeX-Backend-rtf-0.1-SNAPSHOT.jar`.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

The εχTeX Back-End RTF is released under the [GNU Library General
Public License](LICENSE.md).

© 2011 [The εχTeX Group](mailto:extex@dante.de)
