#!/usr/bin/env bash
set -x

SCRIPTDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

APIURL=${APIURL:-http://localhost:3000}
USERNAME=${USERNAME:-u`date +%s`}
EMAIL=${EMAIL:-$USERNAME@mail.com}
PASSWORD=${PASSWORD:-password}

DELAY_REQUEST=${DELAY_REQUEST:-"500"}

# 禁用代理，避免 localhost 请求被代理拦截
unset http_proxy
unset https_proxy
unset HTTP_PROXY
unset HTTPS_PROXY

npx newman run $SCRIPTDIR/Conduit.postman_collection.json \
  --delay-request "$DELAY_REQUEST" \
  --global-var "APIURL=$APIURL" \
  --global-var "USERNAME=$USERNAME" \
  --global-var "EMAIL=$EMAIL" \
  --global-var "PASSWORD=$PASSWORD" \
  "$@"
