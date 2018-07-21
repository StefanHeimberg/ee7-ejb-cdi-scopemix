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
    -p 8080:8080 \
    -p 8181:8181 \
    -p 4848:4848 \
    -v ${__dir}/deployments:/opt/payara41/deployments \
    -v ${__dir}/logs:/opt/payara41/logs \
    payara/server-full:4.181
