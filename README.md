# Projeto Banco JAVER – Ecossistema de Microsserviços com Spring Boot

Bem-vindo(a) ao **projeto Banco JAVER**, que consiste em **duas aplicações** Spring Boot para demonstrar uma arquitetura de microsserviços:

1. **Aplicação 2**: Serviço de Armazenamento – Responsável pelas operações CRUD em um banco de dados H2.  
2. **Aplicação 1**: API Gateway/Cliente – Responsável por expor endpoints ao usuário final, delegando as operações via Feign Client para a Aplicação 2. Também possui um endpoint extra de cálculo de score (`saldoCc * 0.1`).

Este README fornece **instruções** para clonar, executar e testar ambas as aplicações.

---

## Sumário

- [Tecnologias e Pré-Requisitos](#tecnologias-e-pré-requisitos)
- [Arquitetura do Projeto](#arquitetura-do-projeto)
- [Como Executar](#como-executar)
  - [Executando a Aplicação 2 (Serviço de Armazenamento)](#executando-a-aplicação-2-serviço-de-armazenamento)
  - [Executando a Aplicação-1 (API-Gateway/Cliente)](#executando-a-aplicação-1-api-gatewaycliente)
- [Acessando a API](#acessando-a-api)
  - [Endpoints Principais](#endpoints-principais)
  - [Exemplos de Requisição (via Postmancurl)](#exemplos-de-requisição-via-postmancurl)

---

## Tecnologias e Pré-Requisitos

- **Java 11+** (ou superior)
- **Maven 3.6+**
- **Spring Boot** (versões 2.x ou 3.x, conforme configurado no pom.xml)
- **Banco de Dados H2** (in memory)
- **OpenFeign** (Comunicação entre microsserviços)
- **JUnit 5** / **Mockito** (Testes Unitários)
- **JaCoCo** (Cobertura de testes, se desejado)

---

## Arquitetura do Projeto

Este projeto segue uma **arquitetura de microsserviços**:

1. **Aplicação 2 – Serviço de Armazenamento**  
   - **Porta** padrão: 8081 (ajustável via `application.properties`).  
   - Possui a entidade `Cliente`, o `ClienteRepository` (JPA) e o `ClienteService`.  
   - Exponibiliza endpoints CRUD em `/clientes` e utiliza o banco H2 (console em `/h2-console` se ativado).

2. **Aplicação 1 – API Gateway/Cliente**  
   - **Porta** padrão: 8080 (ajustável via `application.properties`).  
   - Exibe endpoints CRUD (espelhando as operações da Aplicação 2), porém internamente faz a chamada ao Serviço de Armazenamento via **Feign Client**.  
   - Possui um endpoint adicional para **cálculo de score**: `GET /clientes/{id}/calcularScore`.

Cada aplicação tem seu próprio **pom.xml** e seu **application.properties** para configuração.

---

## Como Executar

### Executando a Aplicação 2 (Serviço de Armazenamento)

1. **Clone** ou baixe a pasta da Aplicação 2.  
2. Na raiz do projeto, execute:
   ```bash
   mvn clean install
   mvn spring-boot:run

    A aplicação iniciará em http://localhost:8081.
    Para verificar se está no ar, consulte GET http://localhost:8081/clientes (deve retornar uma lista vazia).
    Se quiser visualizar o banco H2, confira em http://localhost:8081/h2-console (caso habilitado no application.properties).

Executando a Aplicação 1 (API-Gateway/Cliente)

    Clone ou baixe a pasta da Aplicação 1.
    Na raiz do projeto, execute:

mvn clean install
mvn spring-boot:run

A aplicação iniciará em http://localhost:8080.
Verifique no application.properties (ou application.yml) se a URL da Aplicação 2 está correta:

    javer.storage-service.url=http://localhost:8081

    Ajuste conforme a porta em que a Aplicação 2 estiver rodando.

Acessando a API

Assumindo que as portas padrão não foram alteradas:

    Aplicação 1 (API Gateway): http://localhost:8080
    Aplicação 2 (Serviço de Armazenamento): http://localhost:8081

Endpoints Principais
Método	Endpoint	Descrição
POST	/clientes	Cria um novo cliente
GET	/clientes	Lista todos os clientes
GET	/clientes/{id}	Busca um cliente pelo ID
PUT	/clientes/{id}	Atualiza os dados de um cliente
DELETE	/clientes/{id}	Deleta um cliente
GET	/clientes/{id}/calcularScore	(Somente Aplicação 1) Calcula o score de crédito (saldoCc * 0.1)
Exemplos de Requisição (via Postman/cURL)

    Criar Cliente

POST http://localhost:8080/clientes
Content-Type: application/json

{
  "nome": "Maria Souza",
  "telefone": 2199999999,
  "correntista": true,
  "saldoCc": 1000.0
}

Listar Todos os Clientes

GET http://localhost:8080/clientes

Buscar Cliente por ID

GET http://localhost:8080/clientes/1

Atualizar Cliente

PUT http://localhost:8080/clientes/1
Content-Type: application/json

{
  "nome": "Maria Correia",
  "telefone": 21988888888,
  "correntista": true,
  "saldoCc": 1500.0
}

Deletar Cliente

DELETE http://localhost:8080/clientes/1

Calcular Score

GET http://localhost:8080/clientes/1/calcularScore
