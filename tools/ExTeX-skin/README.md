![](images/ExTeX-skin-side.png){.left}

εχTeX Maven Skin
================

The εχTeX Maven Skin provides a Maven skin for generating a Maven
site with the
[maven-site-plugin](http://maven.apache.org/plugins/maven-site-plugin/).
All artifacts and scripts needed are contained.

Installing εχTeX Maven Skin
---------------------------

εχTeX Maven Skin can be installed in the local repository with the help
of Maven:

      # mvn install

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

``` {.output}
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Maven Skin 1.1
[INFO] ------------------------------------------------------------------------
[INFO] 
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.429s
[INFO] Finished at: Thu May 19 07:22:14 CEST 2011
[INFO] Final Memory: 4M/15M
[INFO] ------------------------------------------------------------------------
```

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

εχT[eX Maven Skin is released under the [Apache License
2.0](LICENSE.txt). ]{.e}

© 2010-2011 [The εχTeX Group](mailto:extex@dante.de)
