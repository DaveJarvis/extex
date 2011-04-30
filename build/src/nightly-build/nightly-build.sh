#!/bin/bash
#--------------------------------------------------------------------
# Main driver for the nightly build process
#
# Assumption: The current directory when this script is run has to be
#             the build directory in the ExTeX root (trunk)
#
# (c) 2003-2011 Gerd Neugebauer (gene@gerd-neugebauer.de)
#

#if [ "$LOG" == "" ]; then
#    echo "LOG is undefined"
#    exit 1
#fi
if [ "$JAVA_HOME" == "" ]; then
    echo "JAVA_HOME is undefined"
    exit 1
fi
if [ "$INSTALLDIR" == "" ]; then
    echo "INSTALLDIR is undefined"
    exit 1
fi

export MAVEN_OPTS="-Xmx640m"

cd ..

echo '########## install ##########' `date`
mvn install	                           	|| echo "*** FAILURE ***"

echo '########## ExBib ##########' `date`
(cd ExBib;		mvn install)		|| true

echo '########## ExTeX ##########' `date`
(cd ExTeX;		mvn -Dmaven.test.skip=true package)	|| true

echo '########## ExBib site ##########' `date`
(cd ExBib;		mvn site:site site:stage site:deploy)	|| true

echo '########## site ##########' `date`
#mvn -Dmaven.test.skip=true site		|| true
(cd site;               mvn compile)            || true

echo '########## www ##########' `date`
(cd www; ant install )

#--------------------------------------------------------------------
