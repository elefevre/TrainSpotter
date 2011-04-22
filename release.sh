#!/bin/sh

rm -Rf trainspotter-prod*
play war ./trainspotter -o trainspotter-prod --zip
