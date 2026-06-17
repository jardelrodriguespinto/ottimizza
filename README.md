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

O projeto segue a **Clean Architecture**, garantindo que as regras de negócio sejam o núcleo do sistema, independentes de detalhes técnicos.

### Metodologia de Desenvolvimento
- **SDD (Spec-Driven Development):** Implementação modular baseada na `spec.md`.
- **TDD (Test-Driven Development):** Ciclo Red-Green-Refactor obrigatório.
- **Clean Code:** SOLID, DRY, KISS e padrões idiomáticos do Java 21.

---

## 🛠️ Como Executar

### Pré-requisitos
- Docker e Docker Compose instalados.
- Java 21+ (opcional para rodar testes localmente).

### Configuração de Ambiente
1. Copie o arquivo de exemplo de ambiente:
   ```bash
   cp .env.example .env
   ```
2. Edite o arquivo `.env` gerado e ajuste as credenciais e portas conforme sua necessidade (especialmente `JWT_SECRET_KEY`).

### Passo a Passo
1. Suba o ambiente completo (DB, Cache, Broker, LB e API):
   ```bash
   docker-compose up --build
   ```
2. Acesse a documentação interativa (Swagger UI): `http://localhost/swagger-ui.html` (via Nginx).

---

## 📚 Documentação de API (Endpoints)

Para detalhes completos de todos os endpoints, payloads de requisição e formatos de resposta, consulte a especificação técnica detalhada:
👉 [**Especificação Técnica Completa (docs/spec.md)**](./docs/spec.md)

---

## 🧪 Desenvolvimento e Testes

### Executando Testes
Para executar a suíte de testes unitários e de integração localmente:
```bash
./gradlew test
```

### Workflow de Desenvolvimento
1. **Nova funcionalidade/correção:** Consulte os requisitos em `spec.md`.
2. **TDD:** Escreva o teste unitário primeiro (em `src/test/java/...`).
3. **Implementação:** Desenvolva a funcionalidade seguindo a Clean Architecture (Domain -> Application -> Infrastructure).
4. **Verificação:** Execute os testes novamente para garantir que tudo passe.

---

## 📈 Observabilidade
O sistema exporta logs e métricas. Acesse os serviços configurados (Grafana/Loki) conforme definido no `docker-compose.yaml`.

---

## 📄 Licença
Este projeto é para fins de avaliação técnica e estudo de arquitetura.
