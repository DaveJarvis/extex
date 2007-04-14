#!/bin/bash
#--------------------------------------------------------------------
# This is the cron script used to start the nightly build.
#
# (c) 2003-2007 Gerd Neugebauer (gene@gerd-neugebauer.de)
#--------------------------------------------------------------------
#
# CONFIGURATION SECTION
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
export JAVA_HOME=/serv/extex-project/lib/jdk/
#--------------------------------------------------------------------
# Point to Ant
export ANT_HOME=/serv/extex-project/lib/ant/
#--------------------------------------------------------------------
#
# END OF CONFIGURATION SECTION
#
LOG=$LOCALDIR/log
TRUNK=$LOCALDIR/trunk
export PATH=${JAVA_HOME}/bin:${ANT_HOME}/bin:$PATH

cd $TRUNK
date >$LOG/export.log >>$LOG/export.log 2>&1

svn update >>$LOG/export.log 2>&1

cd build
source bin/nightly-build.sh

#
