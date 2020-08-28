![](src/installer/images/ExBib-side.png){.left}

The εχIndex Installer
=====================

εχIndex is an index processor. εχIndex is delivered in form of an
installer. This installer is created with the help of
[IzPack](http://izpack.org).

IzPack is included in the folder `src/izPack-install-*` since currently
no reliable Maven2 package for IzPack could be found.

Creating the εχIndex Installer
------------------------------

The εχIndex installer can be created in this directory with the help of
Maven2:

      # mvn package

This command requires that [Maven2](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

After a successful completion of the command the installer can be found
as the file `target/ExIndex-setup.jar`. This installer includes
everything needed to get started. It just requires a proper Java
installation (Java 5 or higher).

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

The εχIndex installer is released under the [GNU Library Public
License](LICENSE.html). The installer utilizes software distributed
under the Apache License 2.0.

© 2008 [The εχ[T]{.t}[e]{.e}X Group](mailto:extex@dante.de)
