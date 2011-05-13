#!/bin/bash
#--------------------------------------------------------------------
# (c) 2003-2011 Gerd Neugebauer (gene@gerd-neugebauer.de)
#
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
#    /usr/bin/mail -s 'ExTeX build failed' extex-build@lists.berlios.de < $LOG
    cat $BUILD_LOG
fi

#
