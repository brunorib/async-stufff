#!/bin/bash -e

VERSION=$(./gradlew properties -q | grep "version:" | awk '{print $2}')
TARGET_IMAGE_VERSIONED="${DOCKER_REPO}:${VERSION}"

./gradlew build
./gradlew bootBuildImage --imageName="${TARGET_IMAGE_VERSIONED}"