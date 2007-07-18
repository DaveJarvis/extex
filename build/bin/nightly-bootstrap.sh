#!/bin/bash
#--------------------------------------------------------------------
# (c) 2003-2007 Gerd Neugebauer (gene@gerd-neugebauer.de)
#
#--------------------------------------------------------------------
# LOCALDIR contains the location where the build resides.
# Here all temporary files are created.
LOCALDIR="/serv/extex-project/build"
#--------------------------------------------------------------------
# INSTALLDIR contains the directory to store the web site in.
INSTALLDIR=/serv/www/extex
#--------------------------------------------------------------------
# Point to the Java SDK
export JAVA_HOME=/serv/extex-project/lib/jdk
#--------------------------------------------------------------------
# Point to Ant
export ANT_HOME=/serv/extex-project/lib/ant/
#--------------------------------------------------------------------
#
LOG=$LOCALDIR/logs/build.log
#--------------------------------------------------------------------
#
export PATH=$JAVA_HOME/bin:$ANT_HOME/bin:$HOME/bin:$PATH

cd $LOCALDIR/trunk
date >$LOG 2>&1

svn update >>$LOG 2>&1

cd build
source bin/nightly-build.sh >>$LOG 2>&1

if test $? != "0"; then
    mail -s 'ExTeX build failed' gene@gerd-neugebauer.de < $LOG
fi

#
