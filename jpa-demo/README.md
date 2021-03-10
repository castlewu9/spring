# DB

## Postgresql
Pull [postgresql docker image](https://hub.docker.com/_/postgres) and then setup a local database for testing
```
$ docker pull postgres
$
$ docker run -p 5432:5432 -e POSTGRES_PASSWORD=<password> -e POSTGRES_USER=<username> -e POSTGRES_DB=<dbname> --name <container-name> -d postgres
```
## Psql
* Check the docker container is running
* Run `docker exec` to run psql in the container
* Run `psql`
```
$ docker ps
$
$ docker exec -it <container-name> bash
$
/# psql -d <dbname> -U <username>
```
