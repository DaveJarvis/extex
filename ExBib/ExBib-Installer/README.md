

The εχBib Installer
===================

εχBib is a bibliography processor in the spirit of
B[IB]{.small}TeX. εχBib is delivered in form of an
installer. This installer is created with the help of
[IzPack](http://izpack.org).

IzPack is used via the Maven plug-in
[izpack-maven-plugin](http://izpack.codehaus.org/izpack-maven-plugin/)
which downloads the required files from the net when needed. Thus a
working connection to the Internet might be necessary to create the
installer -- unless all dependencies can be resolved from the local
Maven repository.

Creating the εχBib Installer
----------------------------

The εχBib installer can be created in this directory with the help of
Maven:

      # mvn

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

After a successful completion of the command the installer can be found
as the file
[`target/ExBib-Installer-0.1.jar`](target/ExBib-Installer-0.1.jar). This
installer includes everything needed to get started. It just requires a
proper Java installation (Java 5 or higher).

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

The εχBib installer is released under the [GNU Library Public
License](LICENSE.md). The installer utilizes software distributed
under the [Apache License
2.0](http://www.apache.org/licenses/LICENSE-2.0.html).

© 2008-2011 [The εχTeX Group](mailto:extex@dante.de)
