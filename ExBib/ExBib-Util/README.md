![](../ExBib-core/src/images/ExBib-side.png){.left}

εχBib Util
==========

εχBib is a bibliography processor in the spirit of
B[IB]{.small}TeX. This component provides a command line
interface for the utilities provided.

εχBibUtil a bibliography processor utility. This program read one or
more [BIB]{.small} files and produces a combined output. It validates
the syntax and optionally transforms it into one of the formats
supported by εχBib.

This program can also be fed with an aux file. In this case it reads all
[BIB]{.small} files referenced and extracts only those entries cited.
The program takes care to recursively consider aux files included within
the aux file -- usually produced by `\include`.

Compiling εχBib Util
--------------------

εχBib Util can be created in this directory with the help of Maven:

      # (cd ../../ExTeX-resource; mvn install)
      # (cd ../../CLI; mvn install)
      # (cd ..; mvn install)
      # mvn package

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

``` {.output}
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESSFUL
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 8 seconds
[INFO] Finished at: Sat Nov 13 18:36:24 CEST 2010
[INFO] Final Memory: 11M/23M
[INFO] ------------------------------------------------------------------------
```

After a successful completion of the command the jar can be found as the
file `target/ExBib-Util-0.1-SNAPSHOT.jar`.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

εχBib Main is released under the [GNU Library General Public
License](LICENSE.md).

© 2010-2011 [The εχTeX Group](mailto:extex@dante.de)
