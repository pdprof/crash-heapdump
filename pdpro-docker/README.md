# PDPro Application

## Requirements

- [Docker](https://www.docker.com/)

## Test on Docker

### Build docker image

```
setup-docker.sh
```

### Start docker 
```
mkdir ~/pdprof
docker run --rm -p 9080:9080 -v ~/pdprof:/pdprof pdpro:20110516
```

Now you can access http://localhost:9080/top

### Find dump

See `~/pdprof/share` after crash or out of memory error.


## Test on OpenShift

After you setup CRC described at [icp4a-helloworld](https://github.com/pdprof/icp4a-helloworld)

You can use following script. 
```
setup-openliberty.sh
```

Now you can access to http://pdpro-route-default.apps-crc.testing/top

Other test steps are same with docker.
