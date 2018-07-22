#!/usr/bin/env bash

set -e

__dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd ${__dir}

if [ -f "logs/example.log" ]; then
    ts=$(date "+%Y%m%d-%H%M%S")
    mv -v logs/example.log logs/example-$ts.log
fi

if [ -d "deployments/" ]; then
    find deployments/ ! -name 'ee7-ejb-cdi-scopemix-ear.ear' -type f -exec rm -f {} +
fi

docker run -i -t --rm \
    --name ee7-ejb-cdi-scopemix-wildfly \
    -p 8080:8080 \
    -p 9990:9990 \
    -v ${__dir}/deployments:/opt/jboss/wildfly/standalone/deployments \
    -v ${__dir}/logs:/opt/jboss/logs \
    jboss/wildfly:13.0.0.Final \
    /opt/jboss/wildfly/bin/standalone.sh \
    -bmanagement 0.0.0.0 \
    -b=0.0.0.0
