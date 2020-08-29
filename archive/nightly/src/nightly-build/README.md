The εχTeX Build System
==================================

The εχTeX build system is run on a regularily basis to regenerate
all files presented on the εχTeX Web site
[www.extex.org](http://www.extex.org).

Prerequisites
-------------

There are a few things to do before the build can be used.

-   The command `svn` needs to be present on the user's execution path.
    This is the case when Subversion has been installed properly.
-   The command `make` needs to be present on the user's execution
    path. This is the case when the development tools on Linux have been
    installed.
-   The command `perl` needs to be present on the user's execution
    path.
-   The command `java` needs to be present on the path. For this a JDK
    has to be present. Currently Java 1.6.0 is used.
-   The command `mvn` needs to be present on the path. This is the case
    after Maven has been installed. Currently the version 3.0.3 is used.
-   The commands `pdftex` and `makeindex` need to be present on the path
    (for the time being).

Getting Things Started
----------------------

To get things started you need to check out or update the appropriate
sources from the repository. Next you set some environment variables.
And finally you execute a script retrieved from the repository. These
tasks are automated in the following script:

    #!/bin/bash
    #--------------------------------------------------------------------
    # LOCALDIR contains the location where the build resides.
    # Here all temporary files are created.
    LOCALDIR=/serv/extex-project/build
    #--------------------------------------------------------------------
    # INSTALLDIR contains the directory to store the web site in.
    INSTALLDIR=/serv/www/extex
    #--------------------------------------------------------------------
    # Point to the Java SDK
    if test -e /serv/extex-project/lib/jdk; then
        export JAVA_HOME=/serv/extex-project/lib/jdk
    fi
    #--------------------------------------------------------------------
    # Point to Ant
    #export ANT_HOME=/serv/extex-project/lib/ant/
    #--------------------------------------------------------------------
    #
    LOGS=$LOCALDIR/logs
    BUILD_LOG=$LOGS/build.log
    #--------------------------------------------------------------------

    if test ! -e $JAVA_HOME/bin/java; then
        echo "Error: JAVA_HOME is not defined correctly." 1>&2
        echo "       Create a symlink $JAVA_HOME pointing to the right location." 1>&2
        exit 1
    fi

    #--------------------------------------------------------------------
    #
    export PATH=$JAVA_HOME/bin:$ANT_HOME/bin:$HOME/bin:$PATH

    if test ! -d $LOCALDIR; then
        mkdir -p $LOCALDIR
    fi
    if test ! -d $LOGS; then
        mkdir -p $LOGS
    fi

    cd $LOCALDIR

    if test ! -e trunk; then
        svn co http://svn.berlios.de/svnroot/repos/extex/trunk >$BUILD_LOG 2>&1
        cd $LOCALDIR/trunk
    else
        cd $LOCALDIR/trunk
        svn update >$BUILD_LOG 2>&1
    fi

    if test -n "`grep 'At revision' $BUILD_LOG`"; then
        date >>$BUILD_LOG 2>&1
        echo "All quiet on the western front" >> $BUILD_LOG
        exit 0
    fi

    date >>$BUILD_LOG 2>&1

    cd build
    source src/nightly-build/nightly-build.sh >>$BUILD_LOG 2>&1

    if test -n "`grep '[ERROR] BUILD ERROR' $BUILD_LOG`"; then
        cat $BUILD_LOG
    fi

This script can be found in the repository in
`build/src/nightly-build/bootstrap.sh`. Since this script carries the
configuration parameters it needs to be copied onto the build machine
and adapted. The comments above should be enough to fill in the
appropriate values.

Nightly Build
-------------

The nightly build is run on `comedy.dante.de`. It is started by a cron
script. This cron job is declared in the following manner:

    42 1 * * * /serv/extex-project/bin/bootstrap.sh

To list the cron configuration you can use `crontab -l`. To edit the
cron configuration you can use `crontab -e`.

Build Host
----------

Currently the build system for the nightly build is hosted by [DANTE
e.V.](http://www.dante.de) The host used is comedy.dante.de.

© 2006-2011 [Gerd Neugebauer](mailto:gene@gerd-neugebauer.de)
