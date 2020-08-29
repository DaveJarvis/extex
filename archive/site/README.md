

εχTeX Site
======================

Thus module contains the site builder and the source file for the
εχTeX site.

Creating the εχTeX Site
-----------------------------------

The εχTeX site can be created in this directory with the
help of Maven3:

    # mvn compile

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

The progress log appears on the standard output:

    [INFO] Scanning for projects...
    [INFO] ------------------------------------------------------------------------
    [INFO] Reactor Build Order:
    [INFO] 
    [INFO] ExTeX Site Builder
    [INFO] ExTeX Site
    [INFO]                                                                         
    [INFO] ------------------------------------------------------------------------
    [INFO] Building ExTeX Site Builder 1.0
    [INFO] ------------------------------------------------------------------------
    [INFO] 
    ...
    [INFO] ------------------------------------------------------------------------
    [INFO] Reactor Summary:
    [INFO] 
    [INFO] ExTeX Site Builder ................................ SUCCESS [1.236s]
    [INFO] ExTeX Site ........................................ SUCCESS [4.432s]
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 5.825s
    [INFO] Finished at: Sat Apr 23 19:15:00 CEST 2011
    [INFO] Final Memory: 4M/15M
    [INFO] ------------------------------------------------------------------------

After a successful completion of the command the site can be found as
directory [`target/www`](target/www).

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

The εχTeX site is released under the [GNU Library Public
License](LICENSE.md).

© 2011 [The εχTeX Group](mailto:extex@dante.de)
