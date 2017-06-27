#!/bin/bash

 ##
 # Copyright (C) 2017  Ardika Rommy Sanjaya
 ##

NOW=$(date +"%d-%m-%Y %T %Z")
BRANCH="development"
git checkout "$BRANCH"
git add -A
git status
git commit -m "$NOW"
git push origin "$BRANCH"

