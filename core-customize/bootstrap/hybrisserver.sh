#!/bin/sh
ORIGINAL_LOCATION=`pwd`
cd ../hybris/bin/platform
./hybrisserver.sh "$@"
cd $ORIGINAL_LOCATION
