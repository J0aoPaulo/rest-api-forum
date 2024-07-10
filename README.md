# REST API Forum

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![Spring-security](https://img.shields.io/badge/Spring_Security-6DB50F?style=for-the-badge&logo=Spring-Security&logoColor=white)
![Auth0](https://img.shields.io/badge/Auth0-EB5424.svg?style=for-the-badge&logo=Auth0&logoColor=white)
![JWT](https://img.shields.io/badge/JSON%20Web%20Tokens-46317c.svg?style=for-the-badge&logo=JSON-Web-Tokens&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![MYSQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200.svg?style=for-the-badge&logo=Flyway&logoColor=white)

## Índice

- [Descrição](#descrição)
- [Instalação](#instalação)
- [Endpoints](#endpoints)

## Descrição

A REST API Forum é uma aplicação backend que fornece endpoints para gerenciar tópicos, cursos e autores em um fórum de discussão. O projeto utiliza Spring Boot para construção da API e JWT para segurança.

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
    spring.jpa.hibernate.ddl-auto=update
    ```

3. Execute o projeto utilizando a IDE de sua escolha.

4. API disponível em `http://localhost:8080`.

## Endpoints

### Autenticação

- `POST /login`: Autentica o usuário e retorna o token JWT.

### Tópicos

- `GET /topics`: Lista todos os tópicos.
- `GET /topics/{id}`: Detalha um tópico específico.
- `POST /topics`: Cria um novo tópico.
- `PUT /topics/{id}`: Atualiza um tópico existente.
- `DELETE /topics/{id}`: Deleta um tópico.

### Cursos

- `GET /courses`: Lista todos os cursos.
- `GET /courses/{id}`: Detalha um curso específico.
- `POST /courses`: Cria um novo curso.
- `PUT /courses/{id}`: Atualiza um curso existente.
- `DELETE /courses/{id}`: Deleta um curso.

### Autores

- `GET /authors`: Lista todos os autores.
- `GET /authors/{id}`: Detalha um autor específico.
- `POST /authors`: Cria um novo autor.
- `PUT /authors/{id}`: Atualiza um autor existente.
- `DELETE /authors/{id}`: Deleta um autor.

### Segurança

A API utiliza JWT para autenticação e autorização. A rota `/login` é pública, enquanto as demais exigem um token JWT válido no header da requisição.

## Contribuição

1. Fork este repositório.
2. Crie uma branch com sua feature: `git checkout -b minha-feature`.
3. Commit suas mudanças: `git commit -m 'Adiciona minha feature'`.
4. Push para a branch: `git push origin minha-feature`.
5. Abra um Pull Request.