#!/usr/bin/env bash

set -e

__dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd ${__dir}

if [ -d "deployments/" ]; then
    rm -r deployments/
fi

if [ -d "logs/" ]; then
    rm -r logs/
fi

./mvnw clean
