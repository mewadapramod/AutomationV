#!/bin/bash

for entry in `find $1 -maxdepth 1 -mtime -1 | grep $2 | awk -F/ '{print $2}'`; do
   A=$(echo $entry | awk -F"_2" '{print $1}')
   strlength=$(echo $A | awk -F'_' '{print $1}' | wc -m)
   A=${A:$strlength}
   wd=$(pwd)
   echo "$wd/$1$entry"
   cp -r "$wd/$1$entry" /var/run
   docker run --rm -v "/var/run/$entry:/gatling/report/" tmhub.io/aws/aws-cli:1.10.64 s3 sync /gatling/report/ s3://prd00000121.gatling.prod1.us-east-1.tmaws-report/$2-$A-$(date +%F-%T)-$RANDOM --grants read=id=03de85ae46e0bd6886fd7d3ac848326d0e70de7ff2d291310c26e0b45ebba038
done
