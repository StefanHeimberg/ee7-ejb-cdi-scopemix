#!/usr/bin/env bash

set -e

__dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd ${__dir}

if [ -d "deployments/" ]; then
    rm -vr deployments/
fi

mkdir -v deployments/

./mvnw clean install

cp -v ee7-ejb-cdi-scopemix-ear/target/ee7-ejb-cdi-scopemix-ear.ear deployments/
