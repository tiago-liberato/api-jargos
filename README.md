# Assistente Virtual Jargos

API de assistente financeiro que usa **comandos de voz** e IA para registrar e consultar transações financeiras.

## Como funciona

1. Recebe um áudio (ex: "gastei 15 reais numa padaria")
2. Transcreve o áudio em texto
3. Usa IA para entender a intenção e executar a ação (salvar ou consultar)
4. Devolve a resposta em texto ou áudio

Arquitetura em camadas (domínio, aplicação, infraestrutura), mantendo a lógica de negócio independente de banco de dados e provedor de IA.

## Como executar

### Pré-requisitos
- Java 21+
- Docker rodando na máquina (o container do banco sobe automaticamente via `spring-boot-docker-compose`)
- Chave de API da OpenAI

### Passos

1. Clone o repositório
2. Copie o `.env.example` para `.env` e preencha com suas credenciais do banco. Essas variáveis são lidas automaticamente pelo Docker Compose e repassadas ao container.
3. Configure a variável de ambiente `OPENAI_API_KEY` com sua chave da OpenAI. Escolha uma das opções abaixo:

   - **Pelo terminal / variável de ambiente do sistema operacional** (recomendado se for rodar via Maven):
```bash
     # Linux/macOS (válida apenas na sessão atual do terminal)
     export OPENAI_API_KEY=sua-chave-aqui

     # Para persistir entre sessões, adicione a linha acima ao seu
     # ~/.bashrc, ~/.zshrc ou equivalente e recarregue o terminal
```
     No Windows (PowerShell):
```powershell
     setx OPENAI_API_KEY "sua-chave-aqui"
```
     (é necessário abrir um novo terminal após o `setx` para a variável valer)

- **Pelo IntelliJ** (necessário mesmo com a variável já definida no SO, caso a IDE não herde variáveis do shell): em **Run > Edit Configurations > Environment variables**, adicione `OPENAI_API_KEY=sua-chave-aqui`.

4. Certifique-se de que o Docker está em execução na máquina. Não é necessário rodar `docker compose up` manualmente: a aplicação usa a dependência `spring-boot-docker-compose`, que detecta o arquivo `docker-compose.yml` na raiz do projeto e gerencia o container do banco de dados automaticamente ao iniciar.
5. Rode a aplicação: `./mvnw spring-boot:run` (ou pela Run Configuration do IntelliJ)
6. Acesse a documentação interativa em `http://localhost:8080/swagger-ui.html`
7. Para o modo IA, gere áudios em sites como `https://elevenlabs.io/app/speech-synthesis/text-to-speech`, descrevendo uma compra ou especificando um tipo de consulta.

## Endpoints

| Endpoint | Entrada | IA | Resposta |
|---|---|---|---|
| `POST /Transacoes` | JSON | Não | JSON (transação criada) |
| `POST /Transacoes/query` | Áudio | Extrai filtros de busca | JSON (lista de transações) |
| `POST /Transacoes/assistant` | Áudio | Decide e executa a ação (salvar ou consultar) | Áudio (resposta falada) |

**`/Transacoes`** — criação tradicional de transação, sem IA.

**`/Transacoes/query`** — a IA só extrai filtros (categoria, período, valores) do áudio e devolve uma resposta em JSON.

**`/Transacoes/assistant`** — o endpoint mais autônomo: a IA interpreta o comando, decide se deve salvar ou consultar (via Tool Calling), e responde em linguagem natural, convertida em áudio.


## Como testar

A forma mais fácil de testar é pelo Swagger, em `http://localhost:8080/swagger-ui.html`. Também é possível testar diretamente pelo Postman, enviando os áudios em `multipart/form-data` para os endpoints `/assistant` e `/query`, ou um JSON para o endpoint `/Transacoes`.

## Tecnologias usadas

- **Java 21** e **Spring Boot** — base da aplicação
- **Flyway** - Usado para migração do banco de dados
- **Spring Data JPA** + **MySQL** — persistência das transações
- **Spring Boot Docker Compose** — integra o ciclo de vida do container do banco ao ciclo de vida da aplicação, subindo e derrubando o `docker-compose.yml` automaticamente
- **Spring AI** — camada de integração com o modelo de IA
- **OpenAI** — provedor de IA usado para transcrição de áudio, interpretação de linguagem natural e text-to-speech
- **Lombok** — para reduzir código repetitivo nas classes
- **Springdoc OpenAPI (Swagger)** — documentação interativa dos endpoints


## O que aprendi durante o desafio

Esse projeto me ajudou a entender, na prática, vários conceitos que antes eram só teoria:

- **Separação de camadas na prática**: como manter a lógica de negócio (domínio e aplicação) isolada de detalhes técnicos como banco de dados e provedor de IA, permitindo trocar esses detalhes sem afetar as regras da aplicação

- **Dois níveis de autonomia da IA no mesmo domínio**: percebi que dava pra usar a mesma stack (Spring AI + Tool Calling) de duas formas bem diferentes — um endpoint (`/assistant`) onde a IA decide e executa ações sozinha, e outro (`/query`) onde ela só extrai dados estruturados, sem tomar decisões. Essa distinção deixou a API mais previsível pra quem só precisa dos dados brutos, e mais natural pra quem quer conversar com o assistente

- **Gerenciamento automático de containers com `spring-boot-docker-compose`**: entendi como essa dependência elimina a necessidade de subir o banco manualmente, atrelando o ciclo de vida do container ao ciclo de vida da aplicação

- **Segurança de credenciais em múltiplas camadas**: as credenciais do banco (via `.env`, consumidas pelo `docker-compose.yml`) e a chave da OpenAI (via variável de ambiente do SO) nunca tocam o código-fonte, o que exigiu entender como cada camada (Docker Compose, Spring Data JPA, JVM) resolve essas variáveis em tempos diferentes

- **Tool Calling como ponte entre linguagem natural e ações reais**: como o Spring AI permite que o modelo não só responda em texto, mas efetivamente chame métodos Java (`consultTransactions`, `persistTransactionUseCase`) com base na intenção identificada na fala do usuário