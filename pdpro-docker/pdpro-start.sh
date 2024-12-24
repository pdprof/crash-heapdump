#!/bin/bash
# If you want to see a dmp file without copy it to the docker host, pleaes use following
# command (-v) to share directory between docker host and client.
#mkdir -p ~/pdprof/share-pdpro
#docker run --rm -p 9080:9080 -p 9443:9443 -v ~/pdprof/share-pdpro:/pdprof/share --name pdpro pdpro
docker run -d -p 9080:9080 -p 9443:9443 --name pdpro pdpro
