# Jenkins

My own Jenkins Docker image with automation for security and configuration built in.

## Deploy Jenkins

```bash
docker swarm init

export JENKINS_PASSWORD=$(openssl rand -base64 27 | tr -dc A-Za-z0-9)
echo "admin" | docker secret create jenkins-username -
echo "${JENKINS_PASSWORD}" | docker secret create jenkins-password -

docker stack deploy -c deploy.yml jenkins
```

## Create new jobs

1. Open Blue Ocean
1. Create a new Pipeline
1. GitHub
1. Pick organization
1. Pick repository
1. Create Pipeline

## Create credentials

1. Credentials
1. System
1. Global Credentials
1. Add Credentials
  1. Kind: Username with password
  1. Scope: Global
  1. Username: [Docker Hub Username]
  1. Password: [Docker Hub Password]
  1. ID: docker-hub
  1. Description: Docker Hub
1. OK


## List plugins

```bash
curl -s -k "http://admin:$JENKINS_PASSWORD@localhost:8080/pluginManager/api/json?depth=1" | \
  jq -r '.plugins[].shortName' | sort > \
  plugins.txt
```

## Build

```bash
docker image build -t etoews/jenkins:0.1 .
docker image push etoews/jenkins:0.1

docker service update --force jenkins_jenkins
```

## Troubleshoot

```bash
JENKINS_ID=$(docker container ls -q -f "label=com.docker.swarm.service.name=jenkins_jenkins")

docker logs $JENKINS_ID
docker container exec -it $JENKINS_ID bash
```

## Remove

```bash
docker stack rm jenkins
docker volume rm jenkins_jenkins
docker image rm etoews/jenkins
```
