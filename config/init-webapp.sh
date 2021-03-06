#!/bin/bash

# TEST_PROPS=testmine/dbmodel/resources/testmodel.properties

# for dep in keytool; do
#   if test -z $(which $dep); then
#     echo "ERROR: $dep not found - cannot continue"
#     exit 1
#   fi
# done

# Create a java keystore with a generated key-pair
# if [ -z $KEYSTORE ]; then
#     echo '[ERROR]: No key store location configured'
#     exit 1
# fi
# echo "#----> Creating keystore $KEYSTORE"
# keytool -genkey -noprompt \
#     -keysize 2048 \
#     -alias SELF \
#     -keyalg RSA \
#     -dname "CN=intermine-ci, C=GB" \
#     -storepass intermine \
#     -keypass intermine \
#     -keystore $KEYSTORE

# Make it available to the web-app
# cp $KEYSTORE ${HOME}/.intermine/testmodel-keystore.jks.demo

# Add necessary keys to the test properties.
# echo 'i.am.a.dev = true'                        >> $TEST_PROPS # Show 500 error messages.
# echo 'security.keystore.password = intermine'   >> $TEST_PROPS
# echo 'security.privatekey.password = intermine' >> $TEST_PROPS
# echo 'security.privatekey.alias = SELF'         >> $TEST_PROPS
# echo 'jwt.verification.strategy = ANY'          >> $TEST_PROPS
# echo 'jwt.publicidentity = ci'                  >> $TEST_PROPS
sh testmine/setup.sh & # requires PSQL_USER to be set correctly.
sleep 20 # wait for the webapp to come on line

# For now, just shut it down again
cd testmine
./gradlew appStop
