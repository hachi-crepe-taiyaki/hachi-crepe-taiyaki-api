# Hachi Crepe e Taiyaki - API

API REST do sistema de fidelidade da **Hachi Crepe e Taiyaki**, desenvolvida pela equipe **H8**, como **Projeto de Extensão** do 2º Período do Curso de Ciência da Computação da Faculdade São Paulo Tech School ([SPTech](https://sptech.school/)).

## Sumário

- [Sobre o projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Tecnologias utilizadas](#tecnologias-utilizadas)
- [Arquitetura do projeto](#arquitetura-do-projeto)
- [Pré-requisitos](#pre-requisitos)
- [Instalação e configuração](#instalacao-e-configuracao)
- [Executando a aplicação](#executando-a-aplicacao)
- [Documentação da API](#documentacao-da-api)
- [Testes](#testes)
- [Estrutura de diretórios](#estrutura-de-diretorios)
- [Variáveis de ambiente](#variaveis-de-ambiente)
- [Segurança](#seguranca)
- [Contribuição](#contribuicao)
- [Licença](#licenca)
- [Estratégia de Branches](#estrategia-de-branches)

## Sobre o Projeto

O sistema de fidelidade da Hachi Crepe e Taiyaki tem como objetivo promover o relacionamento com os clientes por meio de um programa de recompensas e cardápio digital integrado.

Esta API é responsável por disponibilizar todos os serviços de backend necessários para o ecossistema, incluindo autenticação com cookies seguros, catálogo digital de itens, geração e resgate transacional de selos de compras, resgate de produtos e coleta de avaliações dos clientes.

### Objetivos principais

- Centralizar as regras de negócio do programa de fidelidade e cardápio digital.
- Disponibilizar endpoints REST seguros para consumo das aplicações web e mobile.
- Garantir a integridade atômica nas operações de acúmulo e débito de selos.
- Oferecer controle granular de acesso baseado em papéis (Cliente, Funcionário e Administrador).
- Facilitar a manutenção e a evolução contínua da base de código.

## Funcionalidades

- [x] Cadastro público de clientes com perfil predefinido e senha protegida por BCrypt.
- [x] Autenticação via login com emissão de token JWT armazenado em cookie HttpOnly.
- [x] Encerramento de sessão (logout) com expiração controlada do cookie de autenticação.
- [x] Consulta de usuários com suporte a filtros dinâmicos e combinados via AND (nome, e-mail, telefone, data de nascimento, cidade e região).
- [x] Atualização de perfil cadastral com restrições por perfil e proteção de dados sensíveis.
- [x] Exclusão lógica (soft delete) de contas para preservação do histórico de transações.
- [x] Cardápio digital com leitura pública de categorias e produtos.
- [x] Gerenciamento administrativo completo (CRUD) de categorias com trava de integridade para categorias com produtos associados.
- [x] Gerenciamento administrativo completo (CRUD) de produtos com validação de preços, disponibilidade e categorias existentes.
- [x] Geração de códigos alfanuméricos únicos de compra exclusiva para funcionários em compras elegíveis (a partir de R$ 40,00).
- [x] Resgate de selos por código com validação transacional e garantia de uso único.
- [x] Resgate de recompensas (item cortesia) mediante saldo mínimo acumulado de 12 selos.
- [x] Registro e listagem de feedbacks/avaliações com filtros por usuário e intervalo de datas.
- [x] Tratamento centralizado de exceções com respostas padronizadas em português.
- [x] Documentação interativa e especificação da API via Swagger / OpenAPI 3.

## Tecnologias utilizadas

| Tecnologia | Finalidade |
| --- | --- |
| Java 21 | Linguagem de programação principal da aplicação |
| Spring Boot 4.1.1 (Spring MVC) | Framework base para desenvolvimento do ecossistema backend e APIs REST |
| Spring Data JPA / Hibernate | Camada de persistência, mapeamento objeto-relacional e execução de consultas |
| H2 Database | Banco de dados relacional em memória utilizado para desenvolvimento e testes |
| Spring Security | Framework de segurança para controle de acesso, filtros e proteção de rotas |
| Spring Security Crypto | Algoritmo de hashing BCrypt para armazenamento seguro de senhas |
| JJWT (io.jsonwebtoken 0.12.6) | Geração, assinatura criptográfica (HMAC-SHA) e validação de tokens JWT |
| Jakarta Validation | Validação declarativa de entrada de dados (Bean Validation) |
| Springdoc OpenAPI (Swagger UI 3.1.1) | Geração de especificação OpenAPI 3 e interface Swagger para testes |
| Apache Maven | Gerenciamento de dependências e automação de build do projeto |

## Arquitetura do projeto

A aplicação adota o padrão de arquitetura em camadas (Layered Architecture), garantindo separação clara de responsabilidades:

- **Controller (`com.hachi.api.controller`):** Exposição dos endpoints REST, recepção de requisições HTTP, validação preliminar de payload e documentação OpenAPI.
- **DTO (`com.hachi.api.dto`):** Objetos de transferência de dados que definem os contratos de entrada e saída da API, contendo anotações de validação (`@NotBlank`, `@Min`, `@Size`, etc.).
- **Mapper (`com.hachi.api.mapper`):** Componentes responsáveis pela conversão entre entidades de banco de dados e DTOs.
- **Service (`com.hachi.api.service`):** Concentração das regras de negócio, validações de domínio, controle de autorização em tempo de execução e demarcação transacional (`@Transactional`).
- **Repository (`com.hachi.api.repository`):** Interfaces Spring Data JPA para acesso a dados, aproveitando consultas derivadas e JPQL customizado.
- **Entity (`com.hachi.api.Entity`):** Entidades JPA mapeadas para o modelo relacional (`Usuario`, `Categoria`, `Produto`, `Codigo`, `ResgateSelo`, `ResgateItem`, `Feedback`).
- **Config (`com.hachi.api.config`):** Classes de configuração de infraestrutura, incluindo segurança, CORS, filtros de autenticação JWT e documentação Swagger.

## Pré-requisitos

Para compilar e executar o projeto localmente, certifique-se de possuir:

- Java Development Kit (JDK) versão 21 ou superior instalado e configurado no `PATH`.
- Git instalado.
- Maven 3.9+ (opcional, pois o repositório já inclui o Maven Wrapper `./mvnw`).
- Navegador web para interagir com a interface do Swagger UI e console H2.

## Instalação e configuração

1. Clone o repositório para o seu ambiente local:

```bash
git clone https://github.com/seu-usuario/hachi-crepe-taiyaki-api.git
cd hachi-crepe-taiyaki-api
```

2. Certifique-se de que o script do Maven Wrapper possui permissão de execução (sistemas Unix/Linux/macOS):

```bash
chmod +x mvnw
```

3. Baixe as dependências e faça a compilação do projeto:

```bash
./mvnw clean compile
```

## Executando a aplicação

Para iniciar a API com as configurações de desenvolvimento locais:

- **Linux / macOS:**

```bash
./mvnw spring-boot:run
```

- **Windows:**

```cmd
mvnw.cmd spring-boot:run
```

Após a inicialização, a API estará acessível em:
`http://localhost:8080`

Interfaces de apoio disponíveis em desenvolvimento:
- **Swagger UI (Documentação):** `http://localhost:8080/swagger-ui.html`
- **Console do Banco H2:** `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Usuário: `sa`
  - Senha: *(deixar em branco)*

## Documentação da API

Abaixo estão listados todos os endpoints expostos pela API. A autenticação é realizada através de cookie `HttpOnly` com o token JWT.

### Autenticação e Usuários

| Método | Endpoint | Acesso | Descrição |
| --- | --- | --- | --- |
| POST | `/api/usuarios` | Público | Cadastro de novo usuário cliente (funcao = 0). |
| POST | `/api/usuarios/login` | Público | Autenticação via e-mail e senha com emissão de cookie JWT. |
| POST | `/api/usuarios/logout` | Autenticado | Encerramento de sessão com invalidação do cookie. |
| GET | `/api/usuarios` | Autenticado | Listagem de usuários com filtros opcionais combinados via query params. |
| PUT | `/api/usuarios/{id}` | Próprio usuário ou Admin | Atualização dos dados cadastrais do usuário. |
| DELETE | `/api/usuarios/{id}` | Próprio usuário ou Admin | Exclusão lógica (desativação) da conta de usuário. |

### Cardápio Digital (Categorias e Produtos)

| Método | Endpoint | Acesso | Descrição |
| --- | --- | --- | --- |
| GET | `/api/categorias` | Público | Lista todas as categorias cadastradas no cardápio. |
| GET | `/api/categorias/{id}` | Público | Obtém os detalhes de uma categoria específica. |
| POST | `/api/categorias` | Administrador | Cadastra uma nova categoria no cardápio. |
| PUT | `/api/categorias/{id}` | Administrador | Atualiza os dados de uma categoria existente. |
| DELETE | `/api/categorias/{id}` | Administrador | Remove uma categoria (bloqueado se houver produtos vinculados). |
| GET | `/api/produtos` | Público | Lista todos os produtos cadastrados com categoria vinculada. |
| GET | `/api/produtos/{id}` | Público | Obtém os detalhes de um produto específico. |
| POST | `/api/produtos` | Administrador | Cadastra um novo produto vinculado a uma categoria existente. |
| PUT | `/api/produtos/{id}` | Administrador | Atualiza as informações de um produto existente. |
| DELETE | `/api/produtos/{id}` | Administrador | Remove um produto do cardápio. |

### Programa de Fidelidade e Recompensas

| Método | Endpoint | Acesso | Descrição |
| --- | --- | --- | --- |
| POST | `/api/codigos/gerar` | Funcionário | Gera um código alfanumérico único para compras a partir de R$ 40,00. |
| POST | `/api/usuarios/{id}/selos` | Próprio Cliente | Resgata um selo de compra utilizando um código de uso único válido. |
| POST | `/api/usuarios/{id}/resgatarItem` | Próprio Cliente | Resgata uma recompensa (item cortesia) debitando 12 selos do saldo. |

### Avaliações e Feedbacks

| Método | Endpoint | Acesso | Descrição |
| --- | --- | --- | --- |
| POST | `/api/usuarios/feedback` | Cliente Autenticado | Envia uma avaliação com nota (0 a 5) e comentário descritivo. |
| GET | `/api/feedbacks` | Autenticado | Consulta feedbacks ordenados por data com filtros opcionais por usuário e período. |

## Testes

Para executar os testes automatizados da aplicação:

- **Linux / macOS:**

```bash
./mvnw test
```

- **Windows:**

```cmd
mvnw.cmd test
```

Os testes utilizam a infraestrutura do Spring Boot Test com suporte a banco em memória e validação do contexto de execução.

## Estrutura de diretórios

A estrutura de código-fonte e recursos do projeto é organizada da seguinte forma:

```plaintext
.
├── AGENTS.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── hachi
    │   │           └── api
    │   │               ├── ApiApplication.java
    │   │               ├── config
    │   │               │   ├── AutenticacaoEntryPoint.java
    │   │               │   ├── AutenticacaoFilter.java
    │   │               │   ├── CorsConfiguracao.java
    │   │               │   ├── GerenciadorTokenJwt.java
    │   │               │   ├── SecurityConfiguracao.java
    │   │               │   └── swagger
    │   │               │       └── OpenApiConfig.java
    │   │               ├── controller
    │   │               │   ├── CategoriaController.java
    │   │               │   ├── CodigoController.java
    │   │               │   ├── ExceptionHandlerController.java
    │   │               │   ├── FeedbackController.java
    │   │               │   ├── ProdutoController.java
    │   │               │   └── UsuarioController.java
    │   │               ├── dto
    │   │               │   ├── CategoriaCadastroDTO.java
    │   │               │   ├── CategoriaRespostaDTO.java
    │   │               │   ├── CodigoGeracaoDTO.java
    │   │               │   ├── CodigoResgateDTO.java
    │   │               │   ├── CodigoRespostaDTO.java
    │   │               │   ├── FeedbackCadastroDTO.java
    │   │               │   ├── FeedbackRespostaDTO.java
    │   │               │   ├── LoginDTO.java
    │   │               │   ├── ProdutoCadastroDTO.java
    │   │               │   ├── ProdutoRespostaDTO.java
    │   │               │   ├── RespostaErroDTO.java
    │   │               │   ├── ResultadoLoginDTO.java
    │   │               │   ├── UsuarioAtualizacaoDTO.java
    │   │               │   ├── UsuarioCadastroDTO.java
    │   │               │   └── UsuarioRespostaDTO.java
    │   │               ├── Entity
    │   │               │   ├── Categoria.java
    │   │               │   ├── Codigo.java
    │   │               │   ├── Feedback.java
    │   │               │   ├── Produto.java
    │   │               │   ├── ResgateItem.java
    │   │               │   ├── ResgateSelo.java
    │   │               │   └── Usuario.java
    │   │               ├── mapper
    │   │               │   ├── CodigoMapper.java
    │   │               │   ├── FeedbackMapper.java
    │   │               │   └── UsuarioMapper.java
    │   │               ├── repository
    │   │               │   ├── CategoriaRepository.java
    │   │               │   ├── CodigoRepository.java
    │   │               │   ├── FeedbackRepository.java
    │   │               │   ├── ProdutoRepository.java
    │   │               │   ├── ResgateItemRepository.java
    │   │               │   ├── ResgateSeloRepository.java
    │   │               │   └── UsuarioRepository.java
    │   │               ├── service
    │   │               │   ├── AutorizacaoAdminService.java
    │   │               │   ├── CategoriaService.java
    │   │               │   ├── CodigoService.java
    │   │               │   ├── FeedbackService.java
    │   │               │   ├── ProdutoService.java
    │   │               │   ├── ResgateItemService.java
    │   │               │   ├── ResgateSeloService.java
    │   │               │   └── UsuarioService.java
    │   │               └── util
    │   │                   └── GeradorCodigo.java
    │   └── resources
    │       ├── application.properties
    │       ├── data.sql
    │       └── schema.sql
    └── test
        └── java
            └── com
                └── hachi
                    └── api
                        └── ApiApplicationTests.java
```

## Variáveis de ambiente

As principais variáveis suportadas para parametrização do ambiente são:

| Variável | Descrição | Valor padrão | Obrigatória |
| --- | --- | --- | --- |
| `JWT_SECRET` | Segredo para assinatura criptográfica dos tokens JWT | Chave estática de desenvolvimento | Sim em produção |
| `JWT_EXPIRATION_MINUTES` | Tempo de validade do token JWT em minutos | 60 | Não |
| `COOKIE_SECURE` | Define se o cookie JWT exige canal HTTPS (Secure flag) | false | Sim (true em produção) |
| `CORS_ALLOWED_ORIGINS` | Origens autorizadas para requisições com credenciais | `http://localhost:5173` | Conforme frontend |

## Segurança

O modelo de segurança da API implementa as seguintes práticas:

- **Autenticação Stateless com JWT:** Sessões gerenciadas sem estado via tokens JWT assinados com algoritmo HMAC-SHA e transportados no cookie HTTP `hachi_access_token` com atributos de proteção (`HttpOnly`).
- **Armazenamento de Senhas:** Hashing unidirecional com salt utilizando BCrypt; senhas em texto puro e hashes nunca são trafegados nas respostas da API.
- **Controle de Acesso em Nível de Domínio:** Além da checagem de autoridade no Spring Security, regras críticas (como mutações no cardápio) validam diretamente no banco de dados se o usuário continua ativo e no papel exigido através do `AutorizacaoAdminService`.
- **Exclusão Lógica:** Contas excluídas são apenas desativadas para assegurar que integridades referenciais e históricos de fidelidade permaneçam consistentes.

## Contribuição

Para contribuir com o projeto, siga o fluxo de trabalho estabelecido:

1. Crie uma branch a partir de `dev` seguindo as convenções de nomenclatura (ex: `feature/nome-da-funcionalidade`).
2. Realize as implementações garantindo que os testes existentes continuem passando.
3. Abra um Pull Request direcionado à branch `dev` detalhando o escopo da alteração.
4. Aguarde a revisão por pares antes da integração.

## Licença

Projeto desenvolvido para fins educacionais e de extensão acadêmica pela equipe **H8** da Faculdade São Paulo Tech School (SPTech). Todos os direitos reservados aos respectivos autores.

---

## Estratégia de Branches

O projeto utiliza uma estratégia de branches para organizar o desenvolvimento, validar alterações e manter a estabilidade do código disponibilizado em produção.

### Branches principais

| Branch | Ambiente | Finalidade |
| --- | --- | --- |
| `main` | Produção | Contém a versão estável da API, considerada apta para produção. |
| `dev` | Desenvolvimento | Centraliza as novas funcionalidades, melhorias e correções em desenvolvimento. |
| `hom` | Homologação | Recebe versões candidatas para validação e testes antes da publicação em produção. |

### Fluxo de desenvolvimento

O fluxo padrão de promoção de código segue esta sequência:

```text
feature/* ---> dev ---> hom ---> main
                |       |       |
                v       v       v
          Desenvolvimento  Homologação  Produção
```

1. **Desenvolvimento (`dev`):** novas funcionalidades e correções são implementadas em branches específicas e integradas à `dev` por meio de Pull Requests.
2. **Homologação (`hom`):** após a validação inicial, as alterações aprovadas em `dev` são integradas à `hom`, onde são realizados testes de integração, validação funcional e verificação dos requisitos.
3. **Produção (`main`):** somente versões aprovadas em homologação podem ser promovidas para `main`, após revisão e autorização para publicação.

### Convenção de nomenclatura

Branches temporárias devem utilizar prefixos que identifiquem o propósito da alteração.

| Prefixo | Finalidade | Exemplo |
| --- | --- | --- |
| `feature/` | Nova funcionalidade | `feature/customer-registration` |
| `fix/` | Correção de defeito | `fix/points-calculation` |
| `hotfix/` | Correção urgente em produção | `hotfix/duplicate-redemption` |
| `refactor/` | Refatoração de código | `refactor/customer-service` |
| `docs/` | Alterações na documentação | `docs/api-readme` |
| `test/` | Criação ou atualização de testes | `test/points-service` |

Exemplo de criação de uma branch de funcionalidade:

```bash
git switch dev
git pull origin dev
git switch -c feature/customer-registration
```

Após implementar e testar a alteração, publique a branch e abra um Pull Request para `dev`:

```bash
git push -u origin feature/customer-registration
```

### Regras de integração

* Não realizar commits diretamente em `main`, `hom` ou `dev`; utilizar Pull Requests.
* Toda alteração deve passar por revisão antes de ser integrada às branches principais.
* A branch `dev` deve conter código integrado e testado em nível de desenvolvimento.
* A branch `hom` deve receber apenas versões candidatas que passaram pelas verificações iniciais.
* A branch `main` deve conter somente versões aprovadas para produção.
* Correções urgentes em produção devem ser realizadas em branches `hotfix/*`, revisadas e integradas à `main`.
* Após um hotfix, as correções também devem ser propagadas para `hom` e `dev`, evitando divergências entre as branches.
* Os conflitos devem ser resolvidos e os testes executados antes da conclusão de qualquer merge.

### Processo de publicação

A publicação de uma nova versão deve seguir as seguintes etapas:

1. Implementação e revisão das alterações em `dev`.
2. Integração da versão candidata em `hom`.
3. Execução dos testes e aprovação da homologação.
4. Merge da versão aprovada em `main`.
5. Criação de uma tag de versão, quando aplicável.
6. Deploy da versão aprovada no ambiente de produção.

**Importante:** a integração de código em uma branch não implica, por si só, a publicação automática no ambiente correspondente. Os processos de deploy devem ser configurados separadamente.
