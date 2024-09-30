# Eksempel på .env fil
## /.env ->
KEYCLOAK_CLIENT_SECRET= rq9XM8f6DN0UYLj7iQyRPhwvmzN00huB
POSTGRES_DB=keycloak
POSTGRES_USER=keycloak_user
POSTGRES_PASSWORD=your_password
KEYCLOAK_ADMIN=admin
KEYCLOAK_ADMIN_PASSWORD=admin

# Videre må man laste opp ett realm direkte i keycloak


### login directly to keycloak db
#### psql -U keycloak_user -d keycloak

## Logge inn på keyclaok i terminal
#### cd /opt/keycloak/bin
### ./kcadm.sh config credentials --server http://localhost:8080/ --realm master --user admin --password admin

## import realm
#### ./kcadm.sh create realms -f /opt/keycloak/realm.json


