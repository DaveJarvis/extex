

The εχTeX User\'s Guide
===================================

The Directory Structure
-----------------------

This module follows the Maven directory structure. The inputs for
compilation are somewhere under the directory `src`. The compiled
results can be found in the directory `target`.

``` {.directory}
 -+— src/
  |    +— main/
  |         +— image/
  |         |    +— ...
  |         +— tex/
  |              +— extex-users/
  |                   +— ...
  +— target/
  +— LICENSE.html
  +— pom.xml
  +— README.html
```

Some common files are taken from a similar directory structure in the
parent directory:

``` {.directory}
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

Creating the User\'s Guide
--------------------------

Currently the building of the User\'s Guide relies on an externally
installed T[e]{.e}X system. The commands `pdftex` and `makeindex` are
used. εχBib is used as bibliography processor. Other components of
εχT[e]{.e}X will be plugged in as soon as they are ready.

The εχTeX developer\'s guide can be created in this
directory with the help of [Maven2](http://maven.apache.org). As a
prerequisite the Maven module `ExBib-Main` needs to be accessible at
least from the local repository. This is accomplished by

      # cd ../../ExBib
      # mvn install

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path. In addition an Internet connection is required to download all
artifacts not present locally.

Then the PDF file can be generated with the following command:

      # mvn compile

After a successful completion of the command the document can be found
as the file [`target/ExTeX-users.pdf`](target/ExTeX-users.pdf).

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

The εχBib documentation is released under the [GNU Free Documentation
License](LICENSE.html).

© 2008-2011 [The ExTeX Group](mailto:extex@dante.de)
