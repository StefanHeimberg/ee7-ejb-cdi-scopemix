#!/usr/bin/env bash

set -e

__dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd ${__dir}

if [ ! -f "deployments/ee7-ejb-cdi-scopemix-ear.ear" ]; then
    echo "ERROR: run ./build.sh first"
    exit 1
fi

if [ -f "logs/example.log" ]; then
    echo "" > logs/example.log
fi

docker run -i -t --rm \
    --name ee7-ejb-cdi-scopemix-weblogic \
    -p 8001:8001 \
    -p 8080:8001 \
    -v ${__dir}/deployments:/u01/oracle/user_projects/domains/base_domain/autodeploy \
    -v ${__dir}/logs:/u01/oracle/user_projects/domains/base_domain/logs \
    -e PRODUCTION_MODE=false \
    -e DOMAIN_PRODUCTION_MODE=false \
    iatebes/weblogic-1221-generic-domain:latest
