

The εχBib Documentation
=======================

εχBib is a bibliography processor in the spirit of
B[IB]{.small}TeX. It comes with some documentation. This
documentation is packaged in this module.

Creating the εχBib Documentation
--------------------------------

The εχBib documentation can be created in this directory with the help
of Maven:

      # mvn compile

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

In addition a working TeX system is needed. The commands
must be found on the search path. Step by step the components of
εχTeX might be plugged in to remove the dependency on a
separate TeX system.

``` {.output}
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Building ExBib documentation
[INFO]    task-segment: [compile]
[INFO] ------------------------------------------------------------------------
[INFO] [antrun:run {execution: default}]
[INFO] Executing tasks
     [move] Moving 8 files to /home/gene/src/ExTeX/ExBib/ExBib-doc/target
[INFO] Executed tasks
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESSFUL
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 6 seconds
[INFO] Finished at: Wed Apr 30 20:27:11 CEST 2008
[INFO] Final Memory: 2M/5M
[INFO] ------------------------------------------------------------------------
```

After a successful completion of the command the documentation can be
found as the file `target/exbib-manual.pdf`.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

The εχBib documentation is released under the [GNU Free Documentation
License](LICENSE.html).

© 2008-2011 [The εχTeX Group](mailto:extex@dante.de)
