![](src/images/develop-side.png){.left}

The εχT[e]{.e}X Development Resources
=====================================

This module contains some configuration files for development. Some of
those configuration files are used via the Maven build path. Others can
directly be incorporated into the IDE.

Using the Development Resources
-------------------------------

The εχT[e]{.e}X development resources can be deployed from this
directory with the help of Maven:

      # mvn install

This command requires that [Maven](http://maven.apache.org) is properly
installed on your system and the executable can be found on the search
path.

``` {.output}
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building ExTeX Development Resources 1.3
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:resources (default-resources) @ develop ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] Copying 3 resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ develop ---
[INFO] No sources to compile
[INFO] 
[INFO] --- maven-resources-plugin:2.4.3:testResources (default-testResources) @ develop ---
[INFO] Using 'ISO-8859-1' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory C:\Users\gne\src\ExTeX-workspace\extex\develop\src\test\resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:testCompile (default-testCompile) @ develop ---
[INFO] No sources to compile
[INFO] 
[INFO] --- maven-surefire-plugin:2.7.2:test (default-test) @ develop ---
[INFO] No tests to run.
[INFO] Surefire report directory: C:\Users\gene\src\ExTeX-workspace\extex\develop\target\surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
There are no tests to run.

Results :

Tests run: 0, Failures: 0, Errors: 0, Skipped: 0

[INFO] 
[INFO] --- maven-jar-plugin:2.3.1:jar (default-jar) @ develop ---
[INFO] 
[INFO] --- maven-install-plugin:2.3.1:install (default-install) @ develop ---
[INFO] Installing C:\Users\gene\src\ExTeX-workspace\extex\develop\target\develop-1.3.jar to E:\maven-repository\org\extex\develop\1.3\develop-1.3.jar
[INFO] Installing C:\Users\gene\src\ExTeX-workspace\extex\develop\pom.xml to E:\maven-repository\org\extex\develop\1.3\develop-1.3.pom
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 4.171s
[INFO] Finished at: Sun Jun 05 08:53:52 CEST 2011
[INFO] Final Memory: 4M/15M
[INFO] ------------------------------------------------------------------------
```

After a successful completion of the command the jar has been deployed
to the local Maven repository.

If the build fails the verbose log messages of Maven can be used to
determine the problem. Usually just reading the messages leads you into
the right direction.

License
-------

εχT[e]{.e}X Development Resources are released under the [GNU Library
General Public License](LICENSE.html).

© 2011 [The εχTeX Group](mailto:extex@dante.de)
