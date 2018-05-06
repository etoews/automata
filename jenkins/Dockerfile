FROM jenkins/jenkins:lts-alpine

ENV DOCKER_VERSION=18.03.0
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

USER root

# Docker
RUN curl -sLo /tmp/docker.tgz https://download.docker.com/linux/static/stable/x86_64/docker-${DOCKER_VERSION}-ce.tgz
RUN tar --extract --file /tmp/docker.tgz --strip-components 1 --directory /usr/local/bin/

COPY init.groovy.d/*.groovy /usr/share/jenkins/ref/init.groovy.d/

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt
