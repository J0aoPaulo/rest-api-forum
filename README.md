# REST API Forum

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![Spring-security](https://img.shields.io/badge/Spring_Security-6DB50F?style=for-the-badge&logo=Spring-Security&logoColor=white)
![JWT](https://img.shields.io/badge/JSON%20Web%20Tokens-46317c.svg?style=for-the-badge&logo=JSON-Web-Tokens&logoColor=white)
![OAUTH2](https://img.shields.io/badge/OAUTH2-%23E84242?style=for-the-badge&logo=OAUTH2&logoColor=953030)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![MYSQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200.svg?style=for-the-badge&logo=Flyway&logoColor=white)

## Índice

- [Sobre o projeto](#Sobre-o-projeto)
- [Instalação](#instalação)
- [Funcionalidades](#Funcionalidades)
- [Endpoints](#endpoints)


## Sobre o projeto

A REST API Forum é uma aplicação backend projetada para gerenciar usuários, tópicos e respostas em um fórum de discussão. 
Desenvolvida com Spring Boot, a API utiliza Spring Security para garantir a proteção e integridade dos dados, e JWT para autenticação, permitindo que apenas usuários autorizados acessem e interajam com os recursos. 
Além disso, a API usa Flyway para versionamento do banco de dados, assegurando a consistência das alterações ao longo do tempo. 
A aplicação oferece suporte robusto para operações de CRUD. 

## Instalação

### Pré-requisitos

- Java 21 ou superior
- Maven
- MySQL

### Passos

1. Clone o repositório:

    ```bash
    git clone https://github.com/J0aoPaulo/rest-api-forum.git
    cd rest-api-forum
    ```

2. Configure o banco de dados PostgreSQL e atualize o `application.properties` com suas credenciais:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost/seu_banco_de_dados
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha
    ```

3. Execute o projeto utilizando a IDE de sua escolha.

4. API disponível em `http://localhost:8080`.

## Funcionalidades
- CRUD para tópicos e usuários.

- Paginação e ordenação de tópicos.

- Registro de usuários com informações básicas.

- Autenticação e autorização com OAuth2 e JWT.

- Diferentes papéis de usuário (ADMIN e BASIC) com permissões específicas.

- Tratamento de exceções com mensagens customizadas.

## Endpoints

### Autenticação

- `POST /login`: Autentica o usuário e retorna o token JWT.

### Tópicos

- `POST /topics`: Cria um novo tópico.
- `PUT /topics/{id}`: Atualiza um tópico existente.
- `GET /topics/active`: Lista todos os tópicos ativos.
- `GET /topics/inactive`: Lista todos os tópicos desativados.
- `GET /topics/{id}`: Detalha um tópico específico.
- `DELETE /topics/{id}/delete`: Deleta um tópico.
- `DELETE /topics/{id}/disable`: Desabilitar um tópico.

### Autores

- `POST /authors`: Cria um novo autor.
- `PUT /authors/{id}`: Atualiza um autor existente.
- `GET /authors`: Lista todos os autores.
- `GET /authors/{id}`: Detalha um autor específico.
- `GET /authors/admins`: Detalha um autor específico.
- `DELETE /authors/{id}`: Deleta um autor.
- `DELETE /authors/disable`: Desabilita um autor.

### Admin

- `POST /admin`: Cria um novo administrador.