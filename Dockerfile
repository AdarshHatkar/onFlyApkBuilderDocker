FROM ubuntu:focal

WORKDIR /app

COPY . .

RUN  bash setup_environment.sh