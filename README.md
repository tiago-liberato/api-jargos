# Assistente Virtual Jargos

Uma API de orçamento pessoal que usa **comandos de voz** para registrar e consultar transações financeiras, usando Inteligência Artificial.

## O que o projeto faz

A ideia central é simples: em vez de preencher formulários pra registrar um gasto, comandos de voz podem ser enviados para a API. A aplicação:

1. Recebe um áudio (ex: "gastei 15 reais numa padaria")
2. Transforma esse áudio em texto
3. Usa um modelo de IA pra entender a intenção do comando
4. Executa a ação real no sistema (salvar ou consultar transações)
5. Devolve uma resposta pra pessoa, em texto ou em áudio

Por trás dos panos, a aplicação é construída em camadas bem separadas (domínio, aplicação, infraestrutura), pra manter a lógica de negócio independente de detalhes técnicos como banco de dados ou provedor de IA.

## Como executar a aplicação

### Pré-requisitos
- Java 21+
- Docker e Docker Compose
- Uma chave de API gratuita da OpenAI (usada pra transcrição de áudio e interpretação dos comandos).

### Passos

1. Clone o repositório
2. Copie o arquivo `.env.example` para `.env` e preencha com sua chave da API
3. Suba o banco de dados: `docker compose up -d`
4. Rode a aplicação: `./mvnw spring-boot:run`
5. Acesse a documentação interativa em `http://localhost:8080/swagger-ui.html`
6. Para o modo IA gere áudios em sites como `https://elevenlabs.io/app/speech-synthesis/text-to-speech`, descrevendo uma compra ou especificando um tipo de consulta.


## Como testar o fluxo principal

A forma mais fácil de testar é pelo Swagger, em `http://localhost:8080/swagger-ui.html`:

1. **Usar o recurso de Áudio com IA**: envie um arquivo de áudio no endpoint de transcrição, dizendo algo como "gastei 20 reais com transporte". A aplicação transcreve, entende a intenção e salva ou consulta a transação automaticamente.
2. **Consultar transações por categoria ou por periodo**: use o endpoint de listagem, informando a categoria desejada ou mês e ano.
3. **Criar transação por formulário**:Envie um formulário com os dados da transação realizada para ser salva no banco.
Também é possível testar diretamente pelo Postman, enviando arquivos de áudio em `multipart/form-data`.

## Melhoria implementada

Além do fluxo principal (registrar transações por voz), adicionei a possibilidade de **consultar as transações de um mês específico por áudio**.

A pessoa grava um áudio perguntando, por exemplo, "quais foram meus gastos em agosto de 2026?", e a aplicação:
- Transcreve o áudio
- Busca as transações daquele mês
- Monta uma resposta com as descrições e valores encontrados
- Devolve essa resposta em **áudio** (ou em texto, dependende de como o Cliente quer apresentar os dados)

Essa funcionalidade reaproveita a mesma estrutura já usada para registrar transações, só que aplicada a uma nova intenção: **consultar**, em vez de **criar**.

## Tecnologias usadas

- **Java 21** e **Spring Boot** — base da aplicação
- **Flyway** - Usado para migração do banco de dados
- **Spring Data JPA** + **MySQL** — persistência das transações
- **Docker** e **Docker Compose** — para subir o banco de dados de forma padronizada, sem precisar instalar nada manualmente
- **Spring AI** — camada de integração com o modelo de IA
- **OpenAI** — provedor de IA usado para transcrição de áudio e interpretação de linguagem natural
- **Lombok** — para reduzir código repetitivo nas classes
- **Springdoc OpenAPI (Swagger)** — documentação interativa dos endpoints


## O que aprendi durante o desafio

Esse projeto me ajudou a entender, na prática, vários conceitos que antes eram só teoria:

- Como separar responsabilidades em camadas (domínio, aplicação e infraestrutura), mantendo a lógica de negócio isolada de detalhes técnicos e garantindo o baixo acoplamento
- Como o Spring injeta dependências automaticamente, e a diferença entre criar um bean manualmente e só recebê-lo via construtor
- Como configurar e usar o Docker para rodar um banco de dados sem precisar instalar nada na máquina
- Boas práticas de segurança, como nunca deixar senhas e chaves de API expostas no código(Utilização de variáveis de ambiente)
- Como integrar um modelo de IA a uma aplicação real, usando o Spring AI para transcrever áudio e interpretar comandos em linguagem natural
- Como o **Tool Calling** permite que a IA mapeie execute funções reais do sistema, em vez de só gerar texto solto
  
