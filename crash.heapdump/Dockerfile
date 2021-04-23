FROM openliberty/open-liberty:kernel-java8-openj9-ubi

ARG VERSION=1.0
ARG REVISION=SNAPSHOT

LABEL \
  org.opencontainers.image.authors="PD Professional"
  org.opencontainers.image.vendor="Open Liberty" \
  org.opencontainers.image.url="local" \
  org.opencontainers.image.source="https://github.com/pdprof/crash-heapdump.git" \
  org.opencontainers.image.version="$VERSION" \
  org.opencontainers.image.revision="$REVISION" \
  vendor="Open Liberty" \
  name="crash-heapdump" \
  version="$VERSION-$REVISION" \
  summary="Hands-on application to investigate crash heapdump problem " \
  description="Hands-on application to recreate crash and OutOfMemoryError problems and investigate documents"

COPY --chown=1001:0 src/main/liberty/config /config/
COPY --chown=1001:0 target/crash.heapdump.war /config/apps

RUN configure.sh
