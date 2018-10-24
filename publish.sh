#!/usr/bin/env bash
. ~/.bash_profile
./gradlew clean
./gradlew assembleDebug
./gradlew install

./gradlew runtime-permission:install
./gradlew runtime-permission:bintrayUpload -x runtime-permission-kotlin:bintrayUpload -x runtime-permission-rx:bintrayUpload

./gradlew runtime-permission-kotlin:install
./gradlew runtime-permission-kotlin:bintrayUpload -x runtime-permission-rx:bintrayUpload -x runtime-permission:bintrayUpload

./gradlew runtime-permission-rx:install
./gradlew runtime-permission-rx:bintrayUpload  -x runtime-permission:bintrayUpload -x runtime-permission-kotlin:bintrayUpload