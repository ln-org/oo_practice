#!/bin/bash
TESTFILE=$(find $TESTDIR -name '*.java')
JUNITPATH="$TESTDIR/../../lib"

javac -d ../bin -cp "$JUNITPATH/*":. $TESTFILE
