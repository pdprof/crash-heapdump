#!/bin/bash
cd /dump
/config/linperf.sh 1
SVCMD=`which server`
if [ -f $SVCMD ]; then
    $SVCMD dump defaultServer --archive=/dump/defaultServer.dump.zip
fi
