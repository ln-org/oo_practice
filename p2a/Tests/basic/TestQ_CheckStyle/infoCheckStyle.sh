#!/bin/bash
if java -jar $TESTDIR/checkstyle-10.*-all.jar -c "$TESTDIR/checkstyle-config.xml" . | tee | grep 'WARN'; then
    exit 1
fi
