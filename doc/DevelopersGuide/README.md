![](src/images/dev-guide-side.png){.left}

The εχ[T]{.t}[e]{.e}X Developer\'s Guide
========================================

The Directory Structure
-----------------------

This module follows the Maven directory structure. The inputs for
compilation are somewhere under the directory `src/main`. The compiled
results can be found in the directory `target`.

``` {.directory}
 -+— src/
  |    +— images/
  |    |    +— ...
  |    +— main/
  |         +— image/
  |         |    +— ...
  |         +— tex/
  |              +— dev-guide/
  |                   +— ...
  +— target/
  +— LICENSE.html
  +— pom.xml
  +— README.html
```

Some common files are taken from a similar directory structure in the
*parent* directory:

``` {.directory}
 -+— ../
        -+— src/
              +— main/
                   +— bibtex/
                   |    +— references.bib
                   +— tex/
                   |    +— license/
                   |         +— ...
                   +— texinputs/
                        +— bibtex/
                        |    +— alpha.bst
                        +— makeindex/
                        |    +— doc.ist
                        +— extex-doc.cls
```

Creating the Developer\'s Guide
-------------------------------

Currently the building of the Developer\'s Guide relies on an externally
installed T[e]{.e}X system. The commands `pdftex` and `makeindex` are
used. εχBib is used as bibliography processor. Other components of
εχ[T]{.t}[e]{.e}X will be plugged in as soon as they are ready.

The εχ[T]{.t}[e]{.e}X developer\'s guide can be created in this
directory with the help of [Maven](http://maven.apache.org). As a
prerequisite the Maven module `ExBib-Main` needs to be accessible at
least from the local repository. This is accomplished by

      # cd ../../ExBib
      # mvn install

The next prerequisite are the εχ[T]{.t}[e]{.e}X doc tools. You can put
it into the local repository with

      # cd ../../tools/doc-tools
      # mvn install

These commands require that [Maven3](http://maven.apache.org) is
properly installed on your system and the executable can be found on the
search path. In addition an Internet connection is required to download
all artifacts which are not present locally.

If all prerequisites are satisfied then the PDF file can be generated
with the following command:

      # mvn compile

After a successful completion of the command the document can be found
as the file
[`target/extex-developers.pdf`](target/extex-developers.pdf).

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

The εχ[T]{.t}[e]{.e}X documentation is released under the [GNU Free
Documentation License](LICENSE.html).

© 2011 [The εχ[T]{.t}[e]{.e}X Group](mailto:extex@dante.de)
