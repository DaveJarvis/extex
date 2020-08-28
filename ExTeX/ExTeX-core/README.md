![](src/images/ExTeX-core-side.png){.left}

The εχTeX Core
==========================

εχTeX is a typesetter in the spirit of TeX. The
core component is contained in this module. The core component provides
the definition of registers and some fundamental support classes.

Compiling and Packaging the εχTeX Core
--------------------------------------------------

The εχTeX core can be created in this directory with the
help of Maven:

      # mvn package

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

``` {.output}
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Core 0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ ExTeX-core ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 31 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ ExTeX-core ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:testResources (default-testResources) @ ExTeX-core ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:testCompile (default-testCompile) @ ExTeX-core ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-surefire-plugin:2.7.2:test (default-test) @ ExTeX-core ---
[INFO] Surefire report directory: /home/gene/src/ExTeX-workspace/extex/ExTeX/ExTeX-core/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running org.extex.core.count.CountConstantTest
Tests run: 24, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.092 sec
Running org.extex.core.count.CountTest
Tests run: 43, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.027 sec
Running org.extex.core.dimen.DimenTest
Tests run: 41, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.037 sec
Running org.extex.core.LocatorTest
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.004 sec
Running org.extex.core.SwitchTest
Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.011 sec
Running org.extex.core.UnicodeCharTest
Tests run: 22, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.137 sec
Running org.extex.logging.LogFormatterTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.032 sec

Results :

Tests run: 148, Failures: 0, Errors: 0, Skipped: 0

[INFO] 
[INFO] --- maven-jar-plugin:2.3.1:jar (default-jar) @ ExTeX-core ---
[INFO] Building jar: /home/gene/src/ExTeX-workspace/extex/ExTeX/ExTeX-core/target/ExTeX-core-0.1-SNAPSHOT.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 4.500s
[INFO] Finished at: Mon Apr 25 14:20:45 CEST 2011
[INFO] Final Memory: 4M/15M
[INFO] ------------------------------------------------------------------------
```

After a successful completion of the command the jar can be found as the
file `target/ExTeX-core-0.1-SNAPSHOT.jar`.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

εχTeX is released under the [GNU Library General Public
License](LICENSE.html).

© 2011 [The εχTeX Group](mailto:extex@dante.de)
