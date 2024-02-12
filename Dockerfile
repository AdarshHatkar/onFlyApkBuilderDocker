FROM ubuntu:focal

WORKDIR /app

COPY . .
RUN chmod +x setup_environment.sh


ENTRYPOINT [ "/app/setup_environment.sh" ]