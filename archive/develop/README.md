The εχTeX Development Resources
=====================================

This module contains some configuration files for development. Some of
those configuration files are used via the Maven build path. Others can
directly be incorporated into the IDE.

Using the Development Resources
-------------------------------

The εχTeX development resources can be deployed from this
directory with the help of Maven:

      # mvn install

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system, and the executable can be found on the search
path.

After a successful completion of the command the jar has been deployed
to the local Maven repository.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

εχTeX Development Resources are released under the [GNU Library
General Public License](LICENSE.md).

© 2011 [The εχTeX Group](mailto:extex@dante.de)
