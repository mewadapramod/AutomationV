#!/bin/bash

for entry in `ls -1 $1`; do
   A=$(echo $entry | awk -F"_2" '{print $1}')
   strlength=$(echo $A | awk -F'_' '{print $1}' | wc -m)
   A=${A:$strlength}
   /usr/bin/docker run --rm -v $1$entry:/gatling/report/ tmhub.io/aws/aws-cli:1.10.64 s3 sync /gatling/report/ s3://prd00000121.gatling.prod1.us-east-1.tmaws-report/$2-$A-$(date +%F-%T)-$RANDOM --grants read=id=03de85ae46e0bd6886fd7d3ac848326d0e70de7ff2d291310c26e0b45ebba038
done
