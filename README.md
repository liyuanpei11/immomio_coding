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

## Starten der Spring Boot Anwendung
Die Spring Boot Anwendung kann auf zwei verschiedene Wege gestartet werden. Die erste Möglichkeit ist das Ausführen der
`main` Methode in der `de.example.immomio_coding.ImmomioCodingApplication` Klasse über die IDE.

Die zweite Möglichkeit ist das Starten über das [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) 
mit folgenden CMD:
```shell
mvn spring-boot:run
```
Nach dem Start der Spring Boot Anwendung werden alle 60 Sekunden Daten von 10 Interpreten und deren Alben über die
Spotify-API heruntergeladen und in die Datenbank gespeichert/aktualisiert.

## CRUD-Endpoints

### Artists endpoints
<details>
<summary><code>GET</code> <code><b>/</b></code> <code>(get all artists)</code></summary>

##### Parameters

> None

</details>

<details>
<summary><code>GET</code> <code><b>/{id}</b></code> <code>(get artist by its id)</code></summary>

##### Parameters

> | name |  type     | data type | description          |
> |------|-----------|-----------|----------------------|
> | `id` |  required | string    | The id of the artist |

</details>

<details>
<summary><code>PUT</code> <code><b>/{id}</b></code> <code>(update artist by its id)</code></summary>

##### Parameters

> | name |  type     | data type | description          |
> |------|-----------|-----------|----------------------|
> | `id` |  required | string    | The id of the artist |

##### Request Body

> The request body must be an object of `de.example.immomio_coding.entities.Artist`

</details>

<details>
<summary><code>DELETE</code> <code><b>/{id}</b></code> <code>(delete artist by its id)</code></summary>

##### Parameters

> | name |  type     | data type | description          |
> |------|-----------|-----------|----------------------|
> | `id` |  required | string    | The id of the artist |

</details>

<details>
<summary><code>POST</code> <code><b>/create</b></code> <code>(create a new artist)</code></summary>

##### Parameters

> None

##### Request Body

> The request body must be an object of `de.example.immomio_coding.entities.Artist`

</details>

<details>
<summary><code>GET</code> <code><b>/search</b></code> <code>(search for artists)</code></summary>

##### Parameters

> None

##### Query parameters

> | name    |  type     | data type | description      |
> |---------|-----------|-----------|------------------|
> | `query` |  required | string    | string to search |

</details>

### Album endpoints
<details>
<summary><code>GET</code> <code><b>/</b></code> <code>(get all albums)</code></summary>

##### Parameters

> None

</details>

<details>
<summary><code>GET</code> <code><b>/{id}</b></code> <code>(get album by its id)</code></summary>

##### Parameters

> | name |  type     | data type | description          |
> |------|-----------|-----------|----------------------|
> | `id` |  required | string    | The id of the artist |

</details>

<details>
<summary><code>PUT</code> <code><b>/{id}</b></code> <code>(update album by its id)</code></summary>

##### Parameters

> | name |  type     | data type | description          |
> |------|-----------|-----------|----------------------|
> | `id` |  required | string    | The id of the artist |

##### Request Body

> The request body must be an object of `de.example.immomio_coding.entities.Album`

</details>

<details>
<summary><code>DELETE</code> <code><b>/{id}</b></code> <code>(delete album by its id)</code></summary>

##### Parameters

> | name |  type     | data type | description          |
> |------|-----------|-----------|----------------------|
> | `id` |  required | string    | The id of the artist |

</details>

<details>
<summary><code>POST</code> <code><b>/create</b></code> <code>(create a new album)</code></summary>

##### Parameters

> None

##### Request Body

> The request body must be an object of `de.example.immomio_coding.entities.Album` 

</details>

<details>
<summary><code>GET</code> <code><b>/search</b></code> <code>(search for album)</code></summary>

##### Parameters

> None

##### Query parameters

> | name    |  type     | data type | description      |
> |---------|-----------|-----------|------------------|
> | `query` |  required | string    | string to search |

</details>




