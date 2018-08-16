#!/bin/bash

#openssl aes-256-cbc -a -e -nosalt -in ../gradle/resources/gpg/secring.gpg -out ../gradle/resources/gpg/secring.gpg.enc -iv 0 -k $SIGN_KEY
#openssl aes-256-cbc -a -d -nosalt -in ../gradle/resources/gpg/secring.gpg.enc -out ../gradle/resources/gpg/secring.gpg.dec -iv 0 -k $SIGN_KEY

gpg --batch --yes --passphrase=$SIGN_KEY --output ../gradle/resources/gpg/secring.gpg.enc --symmetric --cipher-algo AES256 ../gradle/resources/gpg/secring.gpg
gpg --batch --yes --passphrase=$SIGN_KEY --output ../gradle/resources/gpg/secring.gpg.dec --decrypt --cipher-algo AES256 ../gradle/resources/gpg/secring.gpg.enc

shasum ../gradle/resources/gpg/secring.gpg
shasum ../gradle/resources/gpg/secring.gpg.dec
shasum ../gradle/resources/gpg/secring.gpg.enc

rm ../gradle/resources/gpg/secring.gpg.dec

