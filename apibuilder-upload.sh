#!/bin/bash

if [ -z "$1" ]
  then
    echo "Missing version argument. Please enter the api version to be uploaded."
fi

apibuilder upload faberoh order-api api/api.json --version "$1"

mkdir -p ./src/main/scala/com/faberoh/order/generated
mkdir -p ./src/test/scala/com/faberoh/order/generated

apibuilder update

