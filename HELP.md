# Hotelaria
Projeto do desafio backend.

## Requirements
- Java 17
- Gradle 8.2.1
- Docker 20+
- Docker-compose 1.21.0+
- Postgres

##  Build and Run
Para buidar o o projeto bastar executar o comdando

```./gradlew bootjar```

Para subir a aplicação local com bancos de dados postgres usar o comando

```docker-compose up -d```

Após o uso usar o comando abaixo para desligar a aplicação

```docker-compose down```
