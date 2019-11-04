#!/bin/bash

DOCKER_IMAGE=tmhub.io/nam/nam-automation:base
docker run -it -v "$PWD":/usr/src -w /usr/src $DOCKER_IMAGE $1
