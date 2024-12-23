#!/bin/bash
if [ ! -f apps/trapit ]; then
     echo "===================="
     echo "Please download latest trapit from https://www.ibm.com/support/pages/node/709009 and put it in the apps directory."
     echo "===================="
     cp apps/trapit-4.2.sh apps/trapit
fi

if [ ! -f apps/linpers.sh ]; then
     echo "===================="
     echo "Please download latest trapit from https://www.ibm.com/support/pages/node/72419 and put it in the apps directory."
     echo "===================="
     cp apps/linperf.20241213.sh apps/linperf.sh
fi

docker build -t was90-pdpro -f Dockerfile.was90-pdpro .
