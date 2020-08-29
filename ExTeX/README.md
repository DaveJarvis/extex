εχTeX
=================

εχTeX aims at the development of a high-quality typesetting
system. This development is based on the experiences with the
typesetting system TeX. Despite of its age TeX can still be
considered a very good choice. Nevertheless design decisions which where
reasonable at the time of the writing of TeX can nowadays no
longer be considered as state of the art.

The stability of TeX is one of its virtues. On the other side it
makes it hard to improve the system -- even in the few areas which
deserve improvements. The new millenium needs a system which is open for
further development and at the same time compatible with TeX as
much as reasonable.

εχTeX on the Web
----------------------------

εχTeX is present on the Web with the Web site at
[www.extex.org](http://www.extex.org). This Web site is hosted by
[DANTE e.V.](http://www.dante.de)

Getting Started -- with Reading
-------------------------------

If you are interested in using εχTeX you should have a look
at the *εχTeX User\'s Guide*. The source code for the
user\'s guide can be found in the directory
[`../doc/UsersGuide`](../doc/UsersGuide).

Compiling εχTeX
---------------------------

εχTeX can be created in this directory with the help of
Maven:

      # mvn compile

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

After a successful completion of the command the class files can be
found in the directories `target/classes` in the respective module
directories.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

Licenses -- Legal Issues
------------------------

εχTeX is developed under an Open Source license. It is meant
to be free. Thus the [GNU Library General Public License](LICENSE.md)
has been chosen.

εχTeX uses some libraries. They usually come with a license
of their own. Thus watch out to respect those licenses as well.

File Organization
-----------------


![](src/images/folder-magenta.png) Backend


![](src/images/folder-blue.png)
[ExTeX-Backend-dump](Backend/ExTeX-Backend-dump)



![](src/images/folder-blue.png)
[ExTeX-Backend-dvi](Backend/ExTeX-Backend-dvi)



![](src/images/folder-blue.png)
[ExTeX-Backend-dvix](Backend/ExTeX-Backend-dvix)



![](src/images/folder-blue.png)
[ExTeX-Backend-iText](Backend/ExTeX-Backend-iText)



![](src/images/folder-blue.png)
[ExTeX-Backend-pdfbox](Backend/ExTeX-Backend-pdfbox)



![](src/images/folder-blue.png)
[ExTeX-Backend-ps](Backend/ExTeX-Backend-ps)



![](src/images/folder-blue.png)
[ExTeX-Backend-rtf](Backend/ExTeX-Backend-rtf)



![](src/images/folder-blue.png)
[ExTeX-Backend-svg](Backend/ExTeX-Backend-svg)



![](src/images/folder-blue.png)
[ExTeX-Backend-text](Backend/ExTeX-Backend-text)



![](src/images/folder-blue.png)
[ExTeX-Backend-xml](Backend/ExTeX-Backend-xml)



![](src/images/folder-blue.png)
[Test-Backend-iText](Backend/Test-Backend-iText)



![](src/images/folder-blue.png)
[Test-Backend-xml](Backend/Test-Backend-xml)




![](src/images/folder-blue.png) [ExTeX-base](ExTeX-base)



![](src/images/folder-blue.png) [ExTeX-base-ext](ExTeX-base-ext)



![](src/images/folder-blue.png) [ExTeX-base-test](ExTeX-base-test)



![](src/images/folder-blue.png) [ExTeX-core](ExTeX-core)



![](src/images/folder-magenta.png) Font


![](src/images/folder-blue.png) [ExTeX-Font-API](Font/ExTeX-Font-API)



![](src/images/folder-blue.png)
[ExTeX-Font-API-Test](Font/ExTeX-Font-API-Test)



![](src/images/folder-blue.png) [ExTeX-Font-afm](Font/ExTeX-Font-afm)



![](src/images/folder-blue.png) [ExTeX-Font-otf](Font/ExTeX-Font-otf)



![](src/images/folder-blue.png) [ExTeX-Font-tfm](Font/ExTeX-Font-tfm)



![](src/images/folder-blue.png) [ExTeX-fontware](Font/ExTeX-fontware)



![](src/images/folder-blue.png) [Test-Font-API](Font/Test-Font-API)




![](src/images/folder-magenta.png) Interpreter


![](src/images/folder-blue.png)
[ExTeX-Interpreter-API](Interpreter/ExTeX-Interpreter-API)



![](src/images/folder-blue.png)
[ExTeX-Interpreter-max](Interpreter/ExTeX-Interpreter-max)




![](src/images/folder-blue.png) [ExTeX-Main-fmt](ExTeX-Main-fmt)



![](src/images/folder-blue.png) [ExTeX-Main-tex](ExTeX-Main-tex)



![](src/images/folder-blue.png) [ExTeX-ocpware](ExTeX-ocpware)



![](src/images/folder-blue.png) [ExTeX-Pdf-API](ExTeX-Pdf-API)



![](src/images/folder-magenta.png) Scanner


![](src/images/folder-blue.png) [ExTeX-Scanner](Scanner/ExTeX-Scanner)



![](src/images/folder-blue.png)
[ExTeX-Scanner-API](Scanner/ExTeX-Scanner-API)



![](src/images/folder-blue.png)
[ExTeX-Scanner32](Scanner/ExTeX-Scanner32)




![](src/images/folder-magenta.png) Typesetter


![](src/images/folder-blue.png)
[ExTeX-Typesetter-API](Typesetter/ExTeX-Typesetter-API)



![](src/images/folder-blue.png)
[ExTeX-Typesetter-core](Typesetter/ExTeX-Typesetter-core)



![](src/images/folder-blue.png)
[ExTeX-Typesetter-tex](Typesetter/ExTeX-Typesetter-tex)




![](src/images/folder-magenta.png) Unit


![](src/images/folder-blue.png)
[ExTeX-Unit-color](Unit/ExTeX-Unit-color)



![](src/images/folder-blue.png) [ExTeX-Unit-etex](Unit/ExTeX-Unit-etex)



![](src/images/folder-blue.png)
[ExTeX-Unit-extex](Unit/ExTeX-Unit-extex)



![](src/images/folder-blue.png)
[ExTeX-Unit-image](Unit/ExTeX-Unit-image)



![](src/images/folder-blue.png)
[ExTeX-Unit-namespace](Unit/ExTeX-Unit-namespace)



![](src/images/folder-blue.png)
[ExTeX-Unit-native](Unit/ExTeX-Unit-native)



![](src/images/folder-blue.png)
[ExTeX-Unit-omega](Unit/ExTeX-Unit-omega)



![](src/images/folder-blue.png)
[ExTeX-Unit-pdftex](Unit/ExTeX-Unit-pdftex)



![](src/images/folder-blue.png) [ExTeX-Unit-tex](Unit/ExTeX-Unit-tex)



© 2009-2011 [The εχTeX Group](mailto:extex@dante.de)
