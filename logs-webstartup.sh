#!/usr/bin/env bash

set -e

__dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd ${__dir}

if [ ! -f "logs/example.log" ]; then
    echo "ERROR: run ./run-*.sh first"
    exit 1
fi

grep "ExampleWebStartup" logs/example.log