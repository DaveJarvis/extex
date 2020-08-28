

εχBib Styles
============

The εχBib styles are packaged into a jar. This jar is prepared to be
used with the ClasspathArchiveFinder.

The εχBib styles can be created in this directory with the help of
Maven:

      # (cd ../../ExTeX-resource; mvn install)
      # mvn package

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

After a successful completion of the command the styles can be found as
the file `ExBib-texmf/target/ExBib-styles.jar`. This jar includes
everything needed to get started.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

The εχBib styles are released under the [GNU Library General Public
License](LICENSE.md).

© 2008-2011 [The ExTeX Group](mailto:extex@dante.de)
