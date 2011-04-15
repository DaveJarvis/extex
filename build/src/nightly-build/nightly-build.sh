#!/bin/bash
#--------------------------------------------------------------------
# Main driver for the nightly build process
#
# Assumption: The current directory when this script is run has to be
#             the build directory in the ExTeX root (trunk)
#
# (c) 2003-2011 Gerd Neugebauer (gene@gerd-neugebauer.de)
#

if [ "$LOG" == "" ]; then
    echo "LOG is undefined"
    exit 1
fi
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

echo '########## develop ##########'
(cd develop;		mvn install)			|| true
echo '########## tools/doc-tools ##########'
(cd tools/doc-tools;	mvn install)			|| true
echo '########## tools/ExTeX-skin ##########'
(cd tools/ExTeX-skin;    mvn install)                   || true
echo '########## ExBib ##########'
(cd ExBib;		mvn install)			|| true
echo '########## ExBib-Installer ##########'
(cd ExBib/ExBib-Installer; mvn package)   		|| true

echo '########## ExTeX ##########'
(cd ExTeX;		mvn -Dmaven.test.skip=true package)	|| true

echo '########## install ##########'
mvn -Dmaven.test.skip=true install			|| true

echo '########## ExBib site ##########'
(cd ExBib;		mvn site:stage site:deploy)	|| true

echo '########## site ##########'
mvn -Dmaven.test.skip=true site				|| true


#####################################################################
#if [ "$ANT_HOME" == "" ]; then
#    echo "ANT_HOME is undefined"
#    exit 1
#fi
#$ANT_HOME/bin/ant               \
#    -f nightly-build.xml        \
#    -keep-going                 \
#    -noinput                    \
#    -Ddeploy.dir=$INSTALLDIR    \
#    all deploy
#####################################################################

##--------------------------------------------------------------------
##
#export PATH=${JAVA_HOME}/bin:$PATH
#
#mkdir -p $LOCALDIR $LOG $INSTALLDIR $INSTALLDIR/snapshot
#
##--------------------------------------------------------------------
##
#cd $LOCALDIR/ExTeX
#echo > .extex-test<<EOF
##
#texinputs=develop/lib/texmf
#extex.fonts=develop/test/font
##
#EOF
#date >$LOG/installer.log
#sh build installer >>$LOG/installer.log 2>&1
#if [ -e target/ExTeX-setup.jar ]
#then
#    cp target/ExTeX-setup.jar $INSTALLDIR/snapshot
#else
#    cat $LOG/installer.log
#fi
#
#
##--------------------------------------------------------------------
##
#cd $LOCALDIR/ExTeX
#date >$LOG/javadoc.log
#sh build javadoc >>$LOG/javadoc.log 2>&1
#cp -r target/javadoc $INSTALLDIR/snapshot
#
#
##--------------------------------------------------------------------
##
#cd $LOCALDIR/ExTeX
#date >$LOG/test.log
#sh build clean junitreport >> $LOG/test.log 2>&1
#cp -r target/www/reports $INSTALLDIR/
#
#
##--------------------------------------------------------------------
##
#cd $LOCALDIR/ExTeX/doc/DevelopersGuide
#date >$LOG/doc.log
#make >> $LOG/doc.log 2>&1
#cd $LOCALDIR/ExTeX/doc/UsersGuide
#make >> $LOG/doc.log 2>&1
#
#
##--------------------------------------------------------------------
##
#date >$LOG/www.log
#cd $LOCALDIR/ExTeX/www
#make >>$LOG/www.log 2>&1
#cp -r ../target/www/* $INSTALLDIR/
#
#
#rm -r $LOCALDIR/ExTeX/target
##
