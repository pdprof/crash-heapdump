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
RUN dnf install -y make procps-ng net-tools
ADD --chown=1001:0 https://www.ibm.com/support/pages/system/files/inline-files/\$FILE/linperf.sh /config/
RUN mkdir -p /pdprof/dump \
    && chown -R 1001:0 /pdprof && chmod 755 /config/linperf.sh
COPY --chown=1001:0 mustgather.sh /config/
COPY --chown=1001:0 trapit /config/
COPY --chown=1001:0 config/server.xml /config/
COPY --chown=1001:0 config/jvm.options /config/
COPY --chown=1001:0 apps/pdpro.20110516.ear /config/apps/
COPY --chown=1001:0 libs/libpdpro.native.so /config/libs/

USER 1001
