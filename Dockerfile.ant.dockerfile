FROM openjdk:8-jdk

RUN apt-get update && \
apt-get install -y wget unzip ant && \
apt-get clean

WORKDIR /app