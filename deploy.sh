#!/usr/bin/env bash

set -e

__dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd ${__dir}

if [ ! -f "ee7-ejb-cdi-scopemix-ear/target/ee7-ejb-cdi-scopemix-ear.ear" ]; then
    echo "ERROR: run ./build.sh first"
    exit 1
fi

if [ -d "deployments/" ]; then
    rm -fvr deployments/*
else
    mkdir deployments/
fi

cp -v ee7-ejb-cdi-scopemix-ear/target/ee7-ejb-cdi-scopemix-ear.ear deployments/
