#!/bin/sh

NOW=$(date +"%d-%m-%Y %T %Z")

git add -A
git status
git commit -m "$NOW"
git push origin jni
