#!/bin/bash
binDir=`dirname ${0}`
${binDir}/help-kubeadmin.sh

$HOME/kubeadmin

oc registry login
docker login `oc registry info`

mvn package

# pull openliberty docker repository
docker pull openliberty/open-liberty:kernel-java8-openj9-ubi

TAG_NAME=crash-heapdump
BUILD_NAME=`date "+%Y%m%d-%H%M%S"`
docker build -t ${TAG_NAME}:1.0-SNAPSHOT .
docker tag ${TAG_NAME}:1.0-SNAPSHOT $(oc registry info)/$(oc project -q)/${TAG_NAME}:${BUILD_NAME}
docker push $(oc registry info)/$(oc project -q)/${TAG_NAME}:${BUILD_NAME}

sed -i s/image-registry.openshift-image-registry.svc:5000/default-route-openshift-image-registry.apps-crc.testing/g kubernetes.yaml
sed -i s/"\[project-name\]"/$(oc project -q)/g kubernetes.yaml
sed -i s/"\[tag-name\]"/${TAG_NAME}/g kubernetes.yaml
sed -i s/"\[build-name\]"/${BUILD_NAME}/g kubernetes.yaml

oc delete secret docker-user-secret
oc create secret generic docker-user-secret --from-file=.dockerconfigjson=$HOME/.docker/config.json --type=kubernetes.io/dockerconfigjson
oc apply -f kubernetes.yaml
