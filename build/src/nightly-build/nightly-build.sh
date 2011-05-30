#!/bin/bash
#--------------------------------------------------------------------
# Main driver for the nightly build process
#
# Assumption: The current directory when this script is run has to be
#             the build directory in the ExTeX root (trunk)
#
# (c) 2003-2011 Gerd Neugebauer (gene@gerd-neugebauer.de)
#

if [ "$JAVA_HOME" == "" ]; then
    echo "JAVA_HOME is undefined"
    exit 1
fi
if [ "$INSTALLDIR" == "" ]; then
    echo "INSTALLDIR is undefined"
    exit 1
fi

export MAVEN_OPTS="-Xmx720m"

cd ..

function run {
    echo "########## $1 ##########" `date`
    (cd $2; mvn ${@:3}) || echo "*** FAILURE ***"
    echo "########## /$1 ##########" `date`
}

run install . install

run 'clean ExBib' ExBib clean
#run ExBib ExBib install
run ExBib ExBib/ExBib-Installer package

run 'clean ExTeX' ExTeX clean
run ExTeX ExTeX \
         -Dmaven.test.failure.ignore=true \
         -Dmaven.test.error.ignore=true   \
         package

run 'ExBib site' ExBib site:site site:stage site:deploy

run site site compile

echo '########## www ##########' `date`
(cd www; ant install )

#--------------------------------------------------------------------
