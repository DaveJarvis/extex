

The εχT[e]{.e}X Unit [pdf]{style="text-variant:small-caps;"}T[e]{.e}X
=====================================================================

The εχT[e]{.e}X Unit [pdf]{style="text-variant:small-caps;"}T[e]{.e}X
provides a loadable εχT[e]{.e}X module defining a set of primitives and
registers. The primitives defined are mainly those present in
[pdf]{style="text-variant:small-caps;"}T[e]{.e}X:

`\efcode`, `\font`, `\pdfadjustspacing`, `\pdfannot`, `\pdfcatalog`,
`\pdfcompresslevel`, `\pdfdecimaldigits`, `\pdfdest`, `\pdfendlink`,
`\pdfendthread`, `\pdffontname`, `\pdffontobjnum`, `\pdfhorigin`,
`\pdfimageresolution`, `\pdfincludechars`, `\pdfinfo`, `\pdflastannot`,
`\pdflastobj`, `\pdflastxform`, `\pdflastximage`, `\pdflinkmargin`,
`\pdfliteral`, `\pdfmovechars`, `\pdfnames`, `\pdfobj`, `\pdfoutline`,
`\pdfoutput`, `\pdfpageattr`, `\pdfpageheight`, `\pdfpagesattr`,
`\pdfpagewidth`, `\pdfpkresolution`, `\pdfrefobj`, `\pdfrefxform`,
`\pdfrefximage`, `\pdfstartlink`, `\pdftexrevision`, `\pdftexversion`,
`\pdfthread`, `\pdfthreadhoffset`, `\pdfthreadmargin`,
`\pdfthreadvoffset`, `\pdfvorigin`, `\pdfxform`, `\pdfximage`

Compiling and Packaging the εχT[e]{.e}X Unit [pdf]{style="text-variant:small-caps;"}T[e]{.e}X
---------------------------------------------------------------------------------------------

The εχT[e]{.e}X Unit [pdf]{style="text-variant:small-caps;"}T[e]{.e}X
can be created in this directory with the help of Maven. To resolve all
dependencies it is best to compile in the parent\'s parent directory:

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
[INFO] Building ExTeX Unit pdfTeX 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
...
```

After a successful completion of the command the jar can be found as the
file `target/ExTeX-Unit-pdftex-0.1-SNAPSHOT.jar`.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

The εχT[e]{.e}X Unit [pdf]{style="text-variant:small-caps;"}T[e]{.e}X is
released under the [GNU Library General Public License](LICENSE.md).

© 2011 [The εχTeX Group](mailto:extex@dante.de)
