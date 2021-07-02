#!/bin/bash
if [ ! -d /pdprof/dump ]; then
	mkdir -p /pdprof/dump
fi

cd /pdprof/dump
/config/linperf.sh 1
SVCMD=`which server`
if [ -f $SVCMD ]; then
    $SVCMD dump defaultServer --archive=/pdprof/dump/defaultServer.dump.zip
fi
cp -R -p /logs/* /pdprof/dump
