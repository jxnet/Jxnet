#!/bin/bash

 ##
 # Copyright (C) 2017  Ardika Rommy Sanjaya
 ##

git fetch origin
git checkout -b development origin/development
git merge master

git checkout master
git merge --no-ff development
git push origin master

git checkout development
