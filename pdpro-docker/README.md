# PDPro Application

## Requirements

- [Docker](https://www.docker.com/)

## Build

```
docker build . -t pdpro-app
```

## Run

```
mkdir ~/dump
docker run --rm -p 30001:9080 -v ~/dump:/dump  pdpro-app
```

Now you can access http://localhost:30001/top

## Find dump

See `~/dump` after crash or out of memory error.
