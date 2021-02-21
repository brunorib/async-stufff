#!/bin/bash -e

. env.sh

./gradlew build
./gradlew bootBuildImage --imageName="${DOCKER_REPO}"