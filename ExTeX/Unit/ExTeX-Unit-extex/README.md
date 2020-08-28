

The εχT[e]{.e}X Unit εχT[e]{.e}X
================================

The εχT[e]{.e}X Unit εχT[e]{.e}X provides a loadable εχT[e]{.e}X module
defining a set of primitives and registers. The primitives defined are
the following ones:

`\writerType`, `\everydisplayend`, `\everymathend`, `\hyphenation`,
`\ignorevoid`, `\integer`, `\lang`, `\mediaheight`, `\mediawidth`

and in the embedded unit ensureloaded the primitive

`\ensureloaded`,

Compiling the εχT[e]{.e}X Unit εχT[e]{.e}X
------------------------------------------

The εχT[e]{.e}X Unit εχT[e]{.e}X can be created in this directory with
the help of Maven.

      # mvn package

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path of the shell.

It also requires that all dependent modules have been installed in your
local Maven repository (with `mvn install`).

``` {.output}
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Unit ExTeX 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
...
```

After a successful completion of the command the documentation can be
found as the file `target/ExTeX-Unit-extex-0.1-SNAPSHOT.jar`.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

The εχT[e]{.e}X Unit T[e]{.e}X is released under the [GNU Library
General Public License](LICENSE.html).

© 2011 [The εχTeX Group](mailto:extex@dante.de)
