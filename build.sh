#!/bin/bash -e

./gradlew build
./gradlew bootBuildImage --imageName="${DOCKER_REPO}"