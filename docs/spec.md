# 📑 Projeto: Kanban Todo API - Especificação Técnica Mestra (SDD + TDD)

Este documento segue rigorosamente as metodologias **Spec-Driven Development (SDD)** e **Test-Driven Development (TDD)**. Ele é baseado na especificação original do produto (Todo API) e detalha todos os requisitos técnicos e de negócio.

---

## 🧪 Sessão Especial: Princípio TDD (Test-First)
*Responsabilidade: Definir a disciplina de desenvolvimento.*

**O desenvolvimento de qualquer funcionalidade DEVE seguir o ciclo Red-Green-Refactor:**

1.  **🔴 Red (Falhar):** Escrever um teste automatizado (Unitário ou de Integração) que defina uma melhoria desejada ou uma nova funcionalidade. O teste **deve falhar**, pois a funcionalidade ainda não existe.
2.  **🟢 Green (Passar):** Escrever o código de produção mínimo necessário para fazer o teste passar o mais rápido possível.
3.  **🔵 Refactor (Refatorar):** Eliminar duplicidade, melhorar a legibilidade e garantir que o código siga os padrões de **Clean Code** e **Clean Architecture**, mantendo todos os testes passando.

**Regra de Ouro:** Nenhum código de produção deve ser escrito sem que haja um teste falhando que justifique sua existência.

---

## 🎯 Sessão 0: Regras de Negócio e Domínio Kanban
*Responsabilidade: Mapear o funcionamento do produto conforme a especificação de referência.*

### 0.1 Entidades do Sistema (Contracts)
*   **Board (Quadro):**
    *   `id`: UUID (String)
    *   `name`: String
*   **Column (Coluna):**
    *   `id`: UUID (String)
    *   `name`: String
    *   `position`: Integer
    *   `boardId`: UUID (String)
*   **Task (Tarefa):**
    *   `id`: UUID (String)
    *   `name`: String
    *   `position`: Integer
    *   `createdAt`: OffsetDateTime (ISO 8601)
    *   `dueDate`: OffsetDateTime (ISO 8601)
    *   `completed`: Boolean
    *   `tags`: List<String>
    *   `columnId`: UUID (String)

### 0.2 Funcionalidades Requeridas
1.  **Quadros (Boards):** Listar, criar, alterar e excluir.
2.  **Colunas (Columns):** 
    *   Listar (ordenadas por `position`).
    *   Criar, alterar e excluir.
    *   Mover horizontalmente (alterar `position` no mesmo quadro).
3.  **Tarefas (Tasks):**
    *   Listar (ordenadas por `position`).
    *   Criar, alterar e excluir.
    *   Mover verticalmente (alterar `position` na mesma coluna).
    *   Mover horizontalmente (mudar de `columnId`).

### 0.3 Endpoints (API v1)
*   **Board:**
    *   `GET /api/v1/board`: Listar todos.
    *   `POST /api/v1/board`: Criar.
    *   `PUT /api/v1/board/{board_id}`: Atualizar.
    *   `DELETE /api/v1/board/{board_id}`: Remover.
*   **Column:**
    *   `GET /api/v1/column/from/{board_id}`: Listar colunas de um quadro.
    *   `POST /api/v1/column`: Criar.
    *   `PUT /api/v1/column/{column_id}`: Atualizar/Mover.
    *   `DELETE /api/v1/column/{column_id}`: Remover.
*   **Task:**
    *   `GET /api/v1/task/from/{column_id}`: Listar tarefas de uma coluna.
    *   `POST /api/v1/task`: Criar.
    *   `PUT /api/v1/task/{task_id}`: Atualizar/Mover.
    *   `DELETE /api/v1/task/{task_id}`: Remover.

---

## 🏗️ Sessão 1: Arquitetura Limpa (Clean Architecture)
*Responsabilidade: Garantir desacoplamento e testabilidade.*

*   **Domain Layer:** Entidades ricas e interfaces de repositório.
*   **Application Layer:** Use Cases (Services) e DTOs (Records).
*   **Infrastructure Layer:** Persistência (JPA/Postgres/Flyway), Segurança (JWT), Cache (Redis), Mensageria (RabbitMQ).
*   **Interface Layer:** Controllers REST, Handlers de Exceção e Mappers.

---

## ☕ Sessão 2: Padrões Java 21 e Clean Code
*Responsabilidade: Definir a qualidade e o estilo do código.*

*   **Java 21:** Virtual Threads, Records, Pattern Matching.
*   **Estilo:** Clean Code, Lambdas (Java 8+), Streams, Imutabilidade.

---

## 🛠️ Sessão 3: Design Patterns Mandatários
*Responsabilidade: Strategy (reordenação), Builder (testes/entidades), Factory.*

---

## 💾 Sessão 4: Persistência e Versionamento (Flyway)
*Responsabilidade: Gestão de esquema com scripts versionados em `db/migration`.*

---

## 🔐 Sessão 5: Segurança (JWT + Spring Security)
*Responsabilidade: Proteção stateless da API.*

---

## 🚀 Sessão 6: Performance (Virtual Threads + Redis)
*Responsabilidade: Alta performance e cache de listagens.*

---

## 📩 Sessão 7: Mensageria (RabbitMQ)
*Responsabilidade: Eventos assíncronos.*

---

## 🌐 Sessão 8: Infraestrutura (Docker + Nginx)
*Responsabilidade: Nginx Round Robin Load Balancer (porta 80 -> 8009).*

---

## 📈 Sessão 9: Observabilidade (Grafana + Loki)
*Responsabilidade: Monitoramento de métricas e centralização de logs.*

---

## 🧪 Sessão 10: Estratégia de Testes (JUnit 5 + Mockito)
*Responsabilidade: Cobertura total de unitários (Domínio/Aplicação) e integração (Web).*

---

## 🛤️ Sessão 11: Roadmap de Execução (Uma Sessão por Fase)
*Abordagem: TDD Rigoroso em cada fase.*

1.  **Fase 1 (Sessão 0):** Domínio Kanban (Entities, DTOs, Business Rules).
2.  **Fase 2 (Sessão 1):** Estrutura Clean Architecture.
3.  **Fase 3 (Sessão 2):** Padrões Java 21 e Estilo.
4.  **Fase 4 (Sessão 3):** Design Patterns.
5.  **Fase 5 (Sessão 4):** Persistência e Flyway.
6.  **Fase 6 (Sessão 5):** Segurança e JWT.
7.  **Fase 7 (Sessão 6):** Performance (Virtual Threads/Redis).
8.  **Fase 8 (Sessão 7):** Mensageria (RabbitMQ).
9.  **Fase 9 (Sessão 8):** Infraestrutura (Docker/Nginx).
10. **Fase 10 (Sessão 9):** Observabilidade (Grafana/Loki).
11. **Fase 11 (Sessão 10):** Consolidação de Testes.
12. **Fase 12 (Sessão 12):** Documentação Swagger.

---

## 📖 Sessão 12: Documentação (Swagger/OpenAPI 3.0)
*Responsabilidade: Interface Swagger-UI ativa.*
