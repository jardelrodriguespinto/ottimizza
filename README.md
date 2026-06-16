# 📋 Kanban Todo API

Uma API RESTful robusta e de alta performance para gerenciamento de quadros Kanban, construída com **Java 21**, **Spring Boot 3** e aderindo aos princípios de **Clean Architecture**, **Clean Code** e **TDD**.

---

## 🚀 Tecnologias e Ferramentas

- **Linguagem:** Java 21 (Virtual Threads / Project Loom)
- **Framework:** Spring Boot 3.4+
- **Banco de Dados:** PostgreSQL 16 (Relacional)
- **Cache:** Redis (Performance de leitura)
- **Mensageria:** RabbitMQ (Eventos assíncronos)
- **Segurança:** Spring Security + JWT (Stateless)
- **Documentação:** Swagger/OpenAPI 3.0
- **Observabilidade:** Grafana, Loki, Micrometer, Actuator
- **Infraestrutura:** Docker, Docker Compose, Nginx (Load Balancer - Round Robin)
- **CI/CD:** GitHub Actions

---

## 🏗️ Arquitetura e Padrões

O projeto segue a **Clean Architecture**, garantindo que as regras de negócio sejam o núcleo do sistema, independentes de detalhes técnicos:

1.  **Domain:** Entidades, Objetos de Valor e interfaces de Repositório.
2.  **Application:** Casos de uso (Services) e DTOs (Records).
3.  **Infrastructure:** Implementações técnicas (JPA, Redis, Security).
4.  **Interface/Web:** Controllers REST e Exception Handlers.

### Metodologia de Desenvolvimento
- **SDD (Spec-Driven Development):** Implementação modular baseada em sessões.
- **TDD (Test-Driven Development):** Ciclo Red-Green-Refactor obrigatório.
- **Clean Code:** SOLID, DRY, KISS e padrões idiomáticos do Java 21.

---

## 📚 Documentação Detalhada

Para detalhes técnicos sobre camadas, padrões de projeto, segurança e infraestrutura, consulte a especificação mestra:
👉 [**Especificação Técnica Completa (docs/spec.md)**](./docs/spec.md)

---

## 🛠️ Como Executar

### Pré-requisitos
- Docker e Docker Compose instalados.
- Java 21+ (opcional para rodar via Docker).

### Passo a Passo
1. Clone o repositório.
2. Suba o ambiente completo (DB, Cache, Broker, LB e API):
   ```bash
   docker-compose up --build
   ```
3. Acesse a documentação interativa:
   - **Swagger UI:** `http://localhost/swagger-ui.html` (via Nginx)
   - **Health Check:** `http://localhost/actuator/health`

---

## ✅ Endpoints Principais

| Recurso | Método | Endpoint | Descrição |
| :--- | :--- | :--- | :--- |
| **Board** | `GET` | `/api/v1/board` | Listar todos os quadros |
| **Board** | `POST` | `/api/v1/board` | Criar um novo quadro |
| **Column** | `GET` | `/api/v1/column/from/{id}` | Listar colunas de um quadro |
| **Task** | `POST` | `/api/v1/task` | Criar nova tarefa |

*Consulte o Swagger para a lista completa e payloads.*

---

## 🧪 Testes

Para executar a suíte de testes unitários e de integração:
```bash
./gradlew test
```

---

## 📈 Observabilidade

O sistema está configurado para exportar logs estruturados em JSON e métricas para:
- **Grafana:** Dashboard de monitoramento.
- **Loki:** Agregação de logs centralizada.

---

## 📄 Licença

Este projeto é para fins de avaliação técnica e estudo de arquitetura.
