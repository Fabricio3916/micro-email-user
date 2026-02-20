# Micro Email User

Projeto de estudo com **arquitetura de microserviços** em Java/Spring Boot.

A aplicação é composta por dois serviços:

- `user`: recebe requisições HTTP para cadastro/listagem/remoção de usuários.
- `email`: consome eventos da fila e envia email de boas-vindas.

## Arquitetura

Fluxo principal:

1. Cliente faz `POST /user` no serviço `user`.
2. O `user` salva no PostgreSQL (`ms-user-db`).
3. O `user` publica uma mensagem na fila RabbitMQ (`email-queue`).
4. O `email` consome a mensagem.
5. O `email` envia o email via SMTP e salva o status no PostgreSQL (`ms-email-db`).

## Tecnologias usadas

- Java 17
- Spring Boot 3.5.10
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring AMQP (RabbitMQ)
- Spring Mail
- PostgreSQL 16
- Docker / Docker Compose
- Maven Wrapper (`./mvnw`)
- Lombok

## Estrutura do repositório

```text
.
├── user/
│   ├── docker-compose.yml
│   ├── pom.xml
│   └── src/
└── email/
    ├── docker-compose.yml
    ├── pom.xml
    └── src/
```

## Portas e serviços

- `user` API: `8081`
- `email` API: `8082`
- PostgreSQL `user`: `5435`
- PostgreSQL `email`: `5433`
- RabbitMQ: conexão externa via variáveis de ambiente (`RABBIT_*`)

## Variáveis de ambiente (segurança)

O projeto usa **variáveis de ambiente** para dados sensíveis (credenciais de SMTP e RabbitMQ).

Isso evita hardcode de segredos no código-fonte e facilita rotação de credenciais.

### Variáveis necessárias

Nos dois serviços:

- `RABBIT_ADDRESS`
- `RABBIT_USERNAME`
- `RABBIT_PASSWORD`

No serviço `email`:

- `EMAIL_HOST`
- `EMAIL_USERNAME`
- `EMAIL_PASSKEY`

> Importante: nunca versione credenciais reais. Mesmo com `.env` no `.gitignore`, mantenha segredos apenas no ambiente local ou em um cofre de segredos.

## Pré-requisitos

- JDK 17
- Docker + Docker Compose
- (Opcional) Maven instalado globalmente, embora o projeto já inclua `./mvnw`

## Como rodar o projeto

### 1) Subir os bancos PostgreSQL

No serviço `user`:

```bash
cd user
docker compose up -d
```

No serviço `email`:

```bash
cd ../email
docker compose up -d
```

### 2) Configurar variáveis de ambiente

Defina as variáveis do RabbitMQ para ambos os serviços e as variáveis de email para o serviço `email`.

Você pode configurar no seu terminal/IDE (Run Configuration) antes de iniciar cada aplicação.

### 3) Subir os microserviços

Em um terminal:

```bash
cd user
./mvnw spring-boot:run
```

Em outro terminal:

```bash
cd email
./mvnw spring-boot:run
```

## Endpoints

### `user`

- `POST /user` cria usuário
- `GET /user` lista usuários
- `DELETE /user/{id}` remove usuário

Exemplo de criação:

```bash
curl -X POST http://localhost:8081/user \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Fabricio",
    "email": "fabricio@example.com"
  }'
```

## Observações

- A fila usada é `email-queue`.
- O status do envio de email é persistido no serviço `email` com estados como `PENDING`, `SENT` e `FAILED`.
- O RabbitMQ não está dockerizado neste repositório; é necessário informar credenciais válidas (`RABBIT_*`).
