![](src/images/ExTeX-base-side.png){.left}

The εχ[T]{.t}[e]{.e}X Base
==========================

εχ[T]{.t}[e]{.e}X is a typesetter in the spirit of [T]{.t}[e]{.e}X. The
base com­po­nent is contained in this module. The base com­po­nent is
the central link for all other parts.

Unfortunately this module contains a collection of different pieces. A
further sub­division should be en­visaged in the future.

Compiling and Packaging the εχ[T]{.t}[e]{.e}X Base
--------------------------------------------------

As a prerequisite all required modules need to be installed in the local
repository (with `mvn install`).

The εχ[T]{.t}[e]{.e}X base can be created in this directory with the
help of Maven:

      # mvn package

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

``` {.output}
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Base 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
...
[INFO] ------------------------------------------------------------------------
```

After a successful completion of the command the jar can be found as the
file `target/ExTeX-base-0.1-SNAPSHOT.jar`.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

εχ[T]{.t}[e]{.e}X base is released under the [GNU Library General Public
License](LICENSE.html).

&copy: 2011 [The εχ[T]{.t}[e]{.e}X Group](mailto:extex@dante.de)
