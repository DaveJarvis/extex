![](src/images/LaTeXmojo-side.png){.left}

The L[a]{.la}[T]{.t}[e]{.e}X Mojo
=================================

The L[a]{.la}[T]{.t}[e]{.e}X mojo tries to create a document from its
source files.

Compiling the L[a]{.la}[T]{.t}[e]{.e}X Mojo
-------------------------------------------

The L[a]{.la}[T]{.t}[e]{.e}X mojo can be created in this directory with
the help of Maven2:

      # mvn package

This command requires that [Maven2](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

``` {.output}
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Building LaTeX mojo
[INFO]    task-segment: [package]
[INFO] ------------------------------------------------------------------------
[INFO] [plugin:helpmojo {execution: generated-helpmojo}]
[INFO] Using 2 extractors.
[INFO] Applying extractor for language: java
[INFO] Extractor for language: java found 3 mojo descriptors.
[INFO] Applying extractor for language: bsh
[INFO] Extractor for language: bsh found 0 mojo descriptors.
[INFO] [plugin:descriptor]
[INFO] Using 2 extractors.
[INFO] Applying extractor for language: java
[INFO] Extractor for language: java found 3 mojo descriptors.
[INFO] Applying extractor for language: bsh
[INFO] Extractor for language: bsh found 0 mojo descriptors.
[INFO] [resources:resources]
[INFO] Using default encoding to copy filtered resources.
[INFO] [compiler:compile]
...
[INFO] [plugin:addPluginArtifactMetadata]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESSFUL
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 5 seconds
[INFO] Finished at: Sat Feb 21 23:30:59 CET 2009
[INFO] Final Memory: 13M/24M
[INFO] ------------------------------------------------------------------------
```

After a successful completion of the command the jar can be found as the
file `target/maven-latex-plugin-1.0-SNAPSHOT.jar`.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

The L[a]{.la}[T]{.t}[e]{.e}X mojo is released under the [GNU Library
General Public License](LICENSE.html).

© 2009 [The εχ[T]{.t}[e]{.e}X Group](mailto:extex@dante.de)
