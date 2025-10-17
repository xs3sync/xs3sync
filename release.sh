#!/bin/bash

./gradlew clean
./gradlew installDist

if test -d "$HOME/opt"; then
  cp -r app/build/install/xs3sync "$HOME/opt/xs3sync"
fi