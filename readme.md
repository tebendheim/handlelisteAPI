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
#### ./kcadm.sh create realms -f /opt/keycloak/imports/realm-export.json


## Custom Theme
Dette handler kun om hvordan login siden ser ut.  Det er mulig å bruke standard theme, eller man kan endre det som er her.


# info
I imports ligger realm import for keycloak. Dette den ene "realm-export.json" er full eksport av et real  som er satt opp med de viktigste funksjonene 
og med navn slik at kotlin programet fungerer med det.

## CustomTheme
Dette er i utgangspunktet hentet ut fra keylcoak sit base theme. Deretter er login sin template.ftl og login.ftl satt opp med tailwind for å kunne style som man vil.

theme og imports lastes inn i docker containeren for keycloak (se docker-compose.yml)
I docker-compose.yml lastes også en init-realm.sh opp.. Dette er bare ett bash script som setter opp handleliste realm fra realm-export.json i docker containeren hver gang.


## kjøring
### slette containere fra compose fil. slette volume (pga eventuelle lagrede ting som ikke skal være noe mer. og lager og starter nye containere med nytt volume.)
docker compose down && docker volume rm keycloak_volume && docker compose up -d

### kjøre shell script inne i keycloak containeren.
docker exec -it keycloak /opt/keycloak/init-realm.sh  

## Oppdatere en fil i docker container
docker cp ./CustomTheme/login/template.ftl keycloak:/opt/keycloak/themes/CustomTheme/login/template.ftl

Dette kopierer inn filen fra lokal maskin til docker containeren.
Dersom filen allerede eksisterer i docker containere, så blir filen erstattet med den nye

### Fungerer slik
docker cp <path_to_local_file> <Container>:<Path_to_container_placement>