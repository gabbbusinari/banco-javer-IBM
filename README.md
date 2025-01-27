# Projeto Banco JAVER – Ecossistema de Microsserviços com Spring Boot

Bem-vindo(a) ao projeto **Banco JAVER**, que consiste em duas aplicações Spring Boot para demonstrar uma arquitetura de microsserviços:

- **Aplicação 2: Serviço de Armazenamento** – Responsável pelas operações CRUD em um banco de dados H2.
- **Aplicação 1: API Gateway/Cliente** – Responsável por expor endpoints ao usuário final, delegando as operações via Feign Client para a Aplicação 2. Também possui um endpoint extra de cálculo de score (`saldoCc * 0.1`).

Este README fornece instruções para clonar, executar e testar ambas as aplicações.

---

## Sumário

1. [Tecnologias e Pré-Requisitos](#tecnologias-e-pré-requisitos)
2. [Arquitetura do Projeto](#arquitetura-do-projeto)
3. [Como Executar](#como-executar)
   - [Executando a Aplicação 2 (Serviço de Armazenamento)](#executando-a-aplicação-2-serviço-de-armazenamento)
   - [Executando a Aplicação 1 (API Gateway/Cliente)](#executando-a-aplicação-1-api-gatewaycliente)
4. [Acessando a API](#acessando-a-api)
   - [Endpoints Principais](#endpoints-principais)
   - [Exemplos de Requisição](#exemplos-de-requisição)

---

## Tecnologias e Pré-Requisitos

- **Java 17** (ou superior)
- **Maven 3.6+**
- **Spring Boot** (versões 2.x ou 3.x, conforme configurado no `pom.xml`)
- **Banco de Dados H2** (in memory)
- **OpenFeign** (Comunicação entre microsserviços)
- **JUnit 5 / Mockito** (Testes Unitários)

---

## Arquitetura do Projeto

Este projeto segue uma arquitetura de microsserviços:

### **Aplicação 2 – Serviço de Armazenamento**
- Porta padrão: **8081** (ajustável via `application.properties`).
- Possui:
  - Entidade `Cliente`.
  - `ClienteRepository` (JPA).
  - `ClienteService`.
- Endpoints CRUD disponíveis em `/clientes`.
- Banco H2 (console acessível em `/h2-console`, se ativado).

### **Aplicação 1 – API Gateway/Cliente**
- Porta padrão: **8080** (ajustável via `application.properties`).
- Exibe endpoints CRUD, internamente delegando as operações ao Serviço de Armazenamento via **Feign Client**.
- Endpoint adicional para cálculo de score: `GET /clientes/{id}/calcularScore`.

Cada aplicação possui seu próprio `pom.xml` e `application.properties` para configuração.

---

## Como Executar

### **Executando a Aplicação 2 (Serviço de Armazenamento)**

1. Clone ou baixe a pasta da Aplicação 2.
2. Na raiz do projeto, execute:

```bash
mvn clean install
mvn spring-boot:run
```

3. A aplicação iniciará em **http://localhost:8081**.
   - Verifique o endpoint: `GET http://localhost:8081/clientes` (deve retornar uma lista vazia).
   - O console do banco H2 está disponível em **http://localhost:8081/h2-console** (caso habilitado no `application.properties`).

### **Executando a Aplicação 1 (API Gateway/Cliente)**

1. Clone ou baixe a pasta da Aplicação 1.
2. Na raiz do projeto, execute:

```bash
mvn clean install
mvn spring-boot:run
```

3. A aplicação iniciará em **http://localhost:8080**.
   - Certifique-se de que o arquivo `application.properties` contém a URL correta da Aplicação 2:

```properties
javer.storage-service.url=http://localhost:8081
```

   - Ajuste a porta, se necessário.

---

## Acessando a API

- **Aplicação 1 (API Gateway):** `http://localhost:8080`
- **Aplicação 2 (Serviço de Armazenamento):** `http://localhost:8081`

### **Endpoints Principais**

| Método | Endpoint                          | Descrição                                            |
|--------|-----------------------------------|----------------------------------------------------|
| POST   | `/clientes`                       | Cria um novo cliente                                |
| GET    | `/clientes`                       | Lista todos os clientes                             |
| GET    | `/clientes/{id}`                  | Busca um cliente pelo ID                            |
| PUT    | `/clientes/{id}`                  | Atualiza os dados de um cliente                     |
| DELETE | `/clientes/{id}`                  | Deleta um cliente                                   |
| GET    | `/clientes/{id}/calcularScore`    | Calcula o score de crédito (`saldoCc * 0.1`)       |

---

## Exemplos de Requisição

### **Criar Cliente**

```bash
POST http://localhost:8080/clientes
Content-Type: application/json

{
  "nome": "Maria Souza",
  "telefone": 2199999999,
  "correntista": true,
  "saldoCc": 1000.0
}
```

### **Listar Todos os Clientes**

```bash
GET http://localhost:8080/clientes
```

### **Buscar Cliente por ID**

```bash
GET http://localhost:8080/clientes/1
```

### **Atualizar Cliente**

```bash
PUT http://localhost:8080/clientes/1
Content-Type: application/json

{
  "nome": "Maria Correia",
  "telefone": 21988888888,
  "correntista": true,
  "saldoCc": 1500.0
}
```

### **Deletar Cliente**

```bash
DELETE http://localhost:8080/clientes/1
```
