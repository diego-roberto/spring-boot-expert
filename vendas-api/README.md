# BACKEND

API em spring boot, MVC, utiliza Java 1.8.

### Executando em ambiente local

Para executar essa aplicação em modo local, primeiro certifique-se que os containers `mysql` estão em execução.

Na raiz da pasta do projeto, execute:
> docker-compose up mysql
>
verifique os logs e certifique que o container da base, `mysql` foi iniciado.

execute o script `schema.sql` na base de dados para a criação do schema e tabelas.

Para executar a API do backend, pode-se utilizar a IDE intelliJ, que possui plugin Maven integrado.
Ou executar o comando:

> mvn spring-boot:run
>

### Configurarndo a URL do Backend

> Procure pelo arquivo environment.ts
>
> Altere o BACKEND_URL para `http://localhost:8082`

### Possíveis Erros:

Caso tenha problema ao executar o maven, pode ser permissão de pastas onde
a IDE intelliJ não está acessando.
Execute os seguintes comandos dentro da pasta raiz do `vendas-api`:

- `sudo chmod -R 777 ./target`
- `sudo chmod -R 777 ./logs`
- `sudo chmod -R 777 ./.m2`

### Cadastrando um usuário:

utilizando a requisição:
`http://localhost:8080/api/users`
```json
{
  "login": "usuario",
  "password": "secreta123"
}
```