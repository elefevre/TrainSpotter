#!/bin/sh

cd TrainSpotter
play dependencies --sync
play eclipsify
cd ..
cp eclipse_settings/*.prefs TrainSpotter/.settings

