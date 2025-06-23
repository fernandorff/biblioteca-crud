# Sistema de Gestão de Aluguéis de Livros (SISGAL)

O SISGAL é uma API desenvolvida em **Spring Boot** para gerenciar um sistema de biblioteca. Ele permite o cadastro e consulta de livros, usuários e empréstimos, com autenticação baseada em **JWT** e controle de acesso por papéis (`ADMIN` e `USER`). A API é documentada com **Swagger** e utiliza **PostgreSQL** como banco de dados.

## Funcionalidades
- **Livros**: Criação, consulta por ID/ISBN e listagem de livros disponíveis.
- **Usuários**: Cadastro (público), consulta por ID ou matrícula.
- **Empréstimos**: Criação, devolução e listagem de empréstimos ativos ou atrasados.
- **Autenticação**: Login com matrícula e senha, retornando um token JWT.

## Como rodar com Docker
Siga os passos abaixo para executar o SISGAL com Docker:

1 - **Clone o repositório**:
```bash
git clone https://github.com/fernandorff/biblioteca-crud.git
cd sisgal
```

---

2 - **Construa e inicie os containers**:
```bash
docker compose up
```
- Isso iniciará a aplicação na porta `8080` e o banco de dados PostgreSQL na porta `5432`.

---

3 - **Acesse a API**:
- **Swagger UI**: Abra `http://localhost:8080/` para explorar a documentação da API.

---

4 - **Parar os containers**:
```bash
docker compose down
```

5 - **Destruir os containers, volumes e imagens**:
```bash
docker compose down --rmi all --volumes
```
