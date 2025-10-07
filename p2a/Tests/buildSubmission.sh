#!/bin/bash
JUNITPATH="$TESTDIR/../lib"
FILES=$(find . -name '*.java')

javac -d ../bin -cp "$JUNITPATH/*":. $FILES
