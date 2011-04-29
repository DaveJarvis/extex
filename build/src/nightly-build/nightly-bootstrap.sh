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
LOG=$LOCALDIR/logs/build.log
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
if test ! -d $LOCALDIR/logs; then
    mkdir -p $LOCALDIR/logs
fi

cd $LOCALDIR

if test ! -e trunk; then
    svn co http://svn.berlios.de/svnroot/repos/extex/trunk >>$LOG 2>&1
fi

cd $LOCALDIR/trunk
date >$LOG 2>&1

svn update >>$LOG 2>&1

cd build
source src/nightly-build/nightly-build.sh >>$LOG 2>&1

if test -n "`grep '[ERROR] BUILD ERROR' $LOG`"; then
#    /usr/bin/mail -s 'ExTeX build failed' extex-build@lists.berlios.de < $LOG
    /usr/bin/mail -s 'ExTeX build' gene@gerd-neugebauer.de < $LOG
fi

#
