#!/bin/bash

. ~/.env



SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"
echo "This script located at ${SCRIPT_DIR}"

RELATIVE_DIR="../aws/sam-app"
SAM_APP_DIR="$( cd ${SCRIPT_DIR}/${RELATIVE_DIR} >/dev/null && pwd )"
if [ ! -d "$SAM_APP_DIR" ]; then
    printf "Error - ${SAM_APP_DIR} should be a valid dir!\n"
    exit 3
fi

printf "SAM app directory at ${SAM_APP_DIR}\n"

pushd ${SAM_APP_DIR}

cp ../../logger/target/logger-1.0-SNAPSHOT.jar .

sam package \
   --template-file sam-app.yml \
   --output-template-file /tmp/packaged.yaml \
   --s3-bucket ${STAGING_S3}


popd
