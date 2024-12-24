FROM openliberty/open-liberty:full-java11-openj9-ubi

ARG VERSION=1.0
ARG REVISION=SNAPSHOT

LABEL \
  org.opencontainers.image.authors="Your Name" \
  org.opencontainers.image.vendor="IBM" \
  org.opencontainers.image.url="local" \
  org.opencontainers.image.source="https://github.com/OpenLiberty/guide-docker" \
  org.opencontainers.image.version="$VERSION" \
  org.opencontainers.image.revision="$REVISION" \
  vendor="Open Liberty" \
  name="system" \
  version="$VERSION-$REVISION" \
  summary="The system microservice from the Docker Guide" \
  description="This image contains the system microservice running with the Open Liberty runtime."

USER root
# make is for snapit
# porcps-ng is for vmstat, ps, top (in linperf.sh)
# net-tools is for netstat (in inperf.sh)
# gdb is for gcore
RUN dnf install -y make procps-ng net-tools gdb
RUN mkdir -p /pdprof/share \
    && chown -R 1001:0 /pdprof && mkdir -p /config/libs
COPY --chown=1001:0 libs/libpdpro.native.so /config/libs
COPY --chown=1001:0 apps/trapit /pdprof/
COPY --chown=1001:0 apps/linperf.sh /pdprof/
COPY --chown=1001:0 apps/mustgather.sh /pdprof/
COPY --chown=1001:0 config/server.xml /config/
COPY --chown=1001:0 config/jvm.options /config/
COPY --chown=1001:0 apps/pdpro.ear /config/apps/

USER 1001
