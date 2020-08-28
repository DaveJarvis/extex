

εχBib Main
==========

εχBib is a bibliography processor in the spirit of
B[IB]{.small}TeX. This component provides a
B[IB]{.small}TeX compatible command line interface.

Compiling εχBib Main
--------------------

εχBib Main can be created in this directory with the help of Maven:

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
file `target/ExBib-groovy-0.1-SNAPSHOT.jar`.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

εχBib Main is released under the [GNU Library General Public
License](LICENSE.html).

© 2010-2011 [The εχTeX Group](mailto:extex@dante.de)
