# Immomio coding challenge Java - Backend

## Aufgabenstellung:
Aufgabe dieser Coding-Challenge war es, eine Spring Boot Anwendung zu entwickeln, die einige Interpreten und die Alben 
über die Spotify-API runterzuladen und dann in einer Datenbank zu sichern. Zusätzlich dazu, soll es möglich sein, diese 
Daten einzusehen, zu bearbeiten und zu löschen. Außerdem sollen geändert Daten nicht vom wiederholenden Update aus der
Spotify-API überschrieben werden.

## Datenbank:
Die Datenbank ist eine PostgreSQL Datenbank, die in einem Docker-Container läuft. 

Vorab sollte Docker bereits installiert und gestartet sein. Außerdem wird die Datenbank mit Flyway aufgebaut, 
daher muss vor dem Ausführen der Anwendung die Datenbank bereits vorhanden sein.

### Docker CMDs:

Herunterladen des aktuellen PostgreSQL Docker image:
```shell
docker pull postgres
```

Erstellen eines PostgreSQL Docker Containers mit entsprechenden Parametern:
```shell
docker run --name dev-postgres -p 5432:5432 -e POSTGRES_PASSWORD=passwort1234 -d postgres
```

Starten des PostgreSQL Docker Containers:
```shell
docker start dev-postgres
```

Erstellen einer Datenbank:
```shell
docker exec dev-postgres psql -U postgres -c "CREATE DATABASE spotifydb" postgres
```

Anhalten des Docker Containers:
```shell
docker stop dev-postgres
```

## CRUD-Endpoints
Nach dem Start der Spring Boot Anwendung werden alle 60 Sekunden Daten von 10 Interpreten und deren Alben über die 
Spotify-API heruntergeladen und in die Datenbank gespeichert/aktualisiert. Außerdem kann über 
http://localhost:8080/swagger-ui/index.html auf eine Übersicht aller CRUD-Endpoints zugegriffen werden.


