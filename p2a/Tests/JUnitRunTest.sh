#!/bin/bash
TESTCLASSNAME=$((cd $TESTDIR && ls *.java) | sed 's/\.java$//g')
JUNITPATH="$TESTDIR/../../lib"

set -o pipefail

java -jar $JUNITPATH/*.jar execute\
     --disable-banner\
     --disable-ansi-colors\
     --fail-if-no-tests\
     --include-engine=junit-jupiter\
     -cp ../bin\
     -c $TESTCLASSNAME\
    | sed '/^Test run finished/,$ d'
