FROM openjdk:11

WORKDIR /app

RUN apt-get update
RUN apt-get -y install maven
RUN apt-get -y install pandoc

COPY . /app

RUN mvn package
RUN export PORT=5001
RUN sh target/bin/simplewebapp
