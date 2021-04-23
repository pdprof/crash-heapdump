#!/bin/bash
# setup mvn and ant 
#

mvn package

# pull openliberty docker repository
docker pull openliberty/open-liberty:kernel-java8-openj9-ubi

TAG_NAME=crash-heapdump
docker build -t ${TAG_NAME}:1.0-SNAPSHOT .
docker tag ${TAG_NAME}:1.0-SNAPSHOT $(oc registry info)/$(oc project -q)/${TAG_NAME}:1.0-SNAPSHOT
docker push $(oc registry info)/$(oc project -q)/${TAG_NAME}:1.0-SNAPSHOT

sed -i s/image-registry.openshift-image-registry.svc:5000/default-route-openshift-image-registry.apps-crc.testing/g kubernetes.yaml
sed -i s/"\[project-name\]"/$(oc project -q)/g kubernetes.yaml
sed -i s/"- containerPort: 9080"/"- containerPort: 9080\n      imagePullSecrets:\n      - name: docker-user-secret"/g kubernetes.yaml

oc create secret generic docker-user-secret --from-file=.dockerconfigjson=$HOME/.docker/config.json --type=kubernetes.io/dockerconfigjson
oc apply -f kubernetes.yaml