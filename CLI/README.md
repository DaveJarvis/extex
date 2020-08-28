![](src/images/ExTeX-CLI-side.png){.left}

The εχT[e]{.e}X CLI -- the Command Line Interface
=================================================

This component contains the command line interface support developed
within the εχT[e]{.e}X project. Never­the­less it does not contain any
de­pen­den­cies to other com­po­nents or spe­cifics of εχT[e]{.e}X. If
is a general purpose component for command line parsing.

It has been necessary to develop an own component since some specifics
of the command line parsing of T[e]{.e}X needed to be preserved.

Compiling εχT[e]{.e}X CLI
-------------------------

The εχT[e]{.e}X command line interface component can be created in this
directory with the help of Maven:

      # mvn package

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

``` {.output}
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building Command Line Interface 0.1
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ CLI ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 9 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ CLI ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:testResources (default-testResources) @ CLI ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 0 resource
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:testCompile (default-testCompile) @ CLI ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-surefire-plugin:2.7.2:test (default-test) @ CLI ---
[INFO] Surefire report directory: /home/gene/src/ExTeX-workspace/extex/CLI/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running org.extex.cli.CLITest
Tests run: 61, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.194 sec
Running org.extex.logging.LogFormatterTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.031 sec

Results :

Tests run: 64, Failures: 0, Errors: 0, Skipped: 0

[INFO] 
[INFO] --- maven-jar-plugin:2.3.1:jar (default-jar) @ CLI ---
[INFO] Building jar: /home/gene/src/ExTeX-workspace/extex/CLI/target/CLI-0.1.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 4.616s
[INFO] Finished at: Mon Apr 25 08:58:28 CEST 2011
[INFO] Final Memory: 4M/15M
[INFO] ------------------------------------------------------------------------
```

After a successful completion of the command the jar can be found as the
file `target/CLI-0.1.jar`.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

εχT[e]{.e}X CLI is released under the [GNU Library General Public
License](LICENSE.html).

© 2009-2011 [The εχ[T]{.t}[e]{.e}X Group](mailto:extex@dante.de)
