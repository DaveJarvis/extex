![](src/images/ExTeX-site-builder-side.png){.left}

The εχ[T]{.t}[e]{.e}X Site Builder
==================================

The site for εχ[T]{.t}[e]{.e}X is created from a set of sources. This
creation utilizes [Apache velocity](http://velocity.apache.org) as
templating engine.

The following kind of files are created:

-   The HTML files
-   The site map
-   The RSS files

Compiling the Site Builder
--------------------------

The εχ[T]{.t}[e]{.e}X site builder can be compiled in this directory
with the help of [Maven](http://maven.apache.org):

      # mvn package

This command requires that [Maven3](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

After a successful completion of the command the jar can be found in the
file
[`target/extex-site-builder-1.0.jar`](target/extex-site-builder-1.0.jar).

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

The εχ[T]{.t}[e]{.e}X Site Builder is released under the [GNU Library
General Public License](LICENSE.html).

© 2008-2011 [The εχ[T]{.t}[e]{.e}X Group](mailto:extex@dante.de)
