#!/bin/sh


cd ../../../
# Wait until Keycloak is up
cd /opt/keycloak/bin

sleep 5

./kcadm.sh config credentials --server http://keycloak:8080/ --realm master --user admin --password admin



./kcadm.sh create realms -f /opt/keycloak/imports/realm-export.json