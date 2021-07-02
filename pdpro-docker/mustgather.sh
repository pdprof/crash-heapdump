#!/bin/bash
cd /pdprof/dump
/config/linperf.sh 1
SVCMD=`which server`
if [ -f $SVCMD ]; then
    $SVCMD dump defaultServer --archive=/pdprof/dump/defaultServer.dump.zip
fi
