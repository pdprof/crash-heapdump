#!/bin/bash
if [ ! -d /pdprof/share ]; then
	mkdir -p /pdprof/share
fi

cd /pdprof/share
/pdprof/linperf.sh 1
SVCMD=`which server`
if [ -f $SVCMD ]; then
    $SVCMD dump defaultServer --archive=/pdprof/share/defaultServer.dump.zip
fi
cp -R -p /logs/* /pdprof/share
