#!/usr/bin/env bash

set -e

__dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd ${__dir}

docker exec -i -t ee7-ejb-cdi-scopemix-wildfly /opt/jboss/wildfly/bin/add-user.sh admin admin --silent
