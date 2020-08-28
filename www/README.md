

The εχTeX Web Site
==============================

This tree contains a simple generator for a Web site written in Perl.

The aim is the ease of maintenance for normal content of pages. They are
stored as simple HTML files and augmented automatically upon
publication.

The layout is separated form the content and stored in several files.
This makes it very easy to adapt the appearance without touching the
contents.

The sources are kept in the sub-directory `src`. The generated files are
written into the sub-directory `target/site`. Both locations can be
configured.

To generate the Web site run the following command:

            ant

This command creates a complete directory hierarchy with all
sub-directories in `../target/www`. An exception are the directories
named `CVS` and `.svn`. Those directories are ignored.

The files starting with . or ending in \~ or in .bak are also ignored.
The files not ending in .html are copied into the destination tree. The
files ending in .html are processed as follows:

-   Text is inserted before the \</head\> tag from the file .headEnd.
-   Text is inserted after the \<body\> tag from the file .bodyStart.
-   Text is inserted before the \</body\> tag from the file .bodyEnd.

The text to be inserted is sought in the current directory and in case
of failure upwards in the super-directories until it is found. In the
inserted files the following tags are replaced:

&top;
:   this is the relative path to the top directory.

&year;
:   this is the current year when generating.

&month;
:   this is the current month when generating.

&day;
:   this is the current day when generating.

\<tabs/\>
:   this is replaced by the contents of the file .tabs.

\<navigation/\>
:   this is replaced by the contents of the file .navigation.

\<info/\>
:   this is replaced by the contents of the file .info.

The current layout has the following scheme:

Header

Tab Bar

Navigation Area

Main Text Area

Info Area

The header contains the right aligned Logo only. It is the same on all
pages.

The Tab bar shows the topmost navigation items with the Tab metaphor.

The Navigation Area shows all navigation items. It is the same on all
pages.

The Info Area shows some info items specific for the current navigation
item.

The Main Text Area contains the contents of the page. This is maintained
by the site authors.

The Directory Structure
-----------------------

This module follows the Maven directory structure. The inputs for
compilation are somewhere under the directory `src`. The compiled
results can be found in the directory `target`.

``` {.directory}
 -+— src/
  |    +— main/
  |    |    +— images/
  |    |    |    +— ...
  |    +— news/
  |         +— ...
  |    +— perl/
  |         +— ...
  |    +— site/
  |         +— ...
  +— target/
  +— build.xml
  +— LICENSE.html
  +— pom.xml
  +— README.html
```

License
-------

The εχBib documentation is released under the [GNU Library General
Public License](LICENSE.html).

© 2006-2011 [The ExTeX Group](mailto:extex@dante.de)
