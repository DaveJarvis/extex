![](src/images/ExTeX-Unit-omega-side.png){.left}

The εχT[e]{.e}X Unit Ω
======================

The εχT[e]{.e}X Unit Ω provides a loadable εχT[e]{.e}X module defining a
set of primitives and registers. The primitives defined are mainly those
present in Ω:

`\DefaultInputMode`, `\DefaultInputTranslation`, `\DefaultOutputMode`,
`\DefaultOutputTranslation`, `\InputMode`, `\InputTranslation`,
`\OutputMode`, `\OutputTranslation`, `\addafterocplist`,
`\addbeforeocplist`, `\clearocplists`, `\glueshrinkorder`,
`\gluestretchorder`, `\hfi`, `\localbrokenpenalty`,
`\localinterlinepenalty`, `\localleftbox`, `\localrightbox`, `\mathdir`,
`\maxError`, `\maxRegister`, `\naturaldir`, `\noDefaultInputMode`,
`\noDefaultInputTranslation`, `\noDefaultOutputMode`,
`\noDefaultOutputTranslation`, `\nullocplist`, `\ocp`, `\ocplist`,
`\odelcode`, `\odelmiter`, `\omathaccent`, `\omathchar`,
`\omathchardef`, `\omathcode`, `\oradical`, `\pagedir`, `\pagedirHL`,
`\pagedirHR`, `\pardirHL`, `\pardirHR`, `\popocplist`, `\pushocplist`,
`\removeafterocplist`, `\removebeforeocplist`, `\textdir`,
`\unnaturaldir`, `\vfi`

Compiling and Packaging the εχT[e]{.e}X Unit Ω
----------------------------------------------

The εχT[e]{.e}X Ω can be created in this directory with the help of
Maven. To resolve all dependencies it is best to compile in the
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
[INFO] Building ExTeX Unit Omega 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
...
```

After a successful completion of the command the jar can be found as the
file `target/ExTeX-Unit-omega-0.1-SNAPSHOT.jar`.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

The εχT[e]{.e}X Unit Ω is released under the [GNU Library General Public
License](LICENSE.html).

© 2011 [The εχ[T]{.t}[e]{.e}X Group](mailto:extex@dante.de)
