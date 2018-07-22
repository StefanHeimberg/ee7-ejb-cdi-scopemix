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
    --name ee7-ejb-cdi-scopemix-payara \
    -p 8080:8080 \
    -p 8181:8181 \
    -p 4848:4848 \
    -v ${__dir}/deployments:/opt/payara41/deployments \
    -v ${__dir}/logs:/opt/payara41/logs \
    payara/server-full:4.181
