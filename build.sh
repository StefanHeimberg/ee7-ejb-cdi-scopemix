#!/usr/bin/env bash

set -e

__dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd ${__dir}

if [ ! -d "deployments/" ]; then
    mkdir -v deployments/
fi

if [ -f "deployments/ee7-ejb-cdi-scopemix-ear.ear" ]; then
    rm -v deployments/ee7-ejb-cdi-scopemix-ear.ear
fi

./mvnw clean install

cp -v ee7-ejb-cdi-scopemix-ear/target/ee7-ejb-cdi-scopemix-ear.ear deployments/
