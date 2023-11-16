# Definição do projeto
Projeto desenvolvido durante o curso 'Spring Boot Expert: JPA, RESTFul API, Security, JWT e Mais' e atualizado conforme convenções e boas práticas.

> <b> É uma implementação de API Rest com Spring Boot e alguns dos melhores recursos do framework, como JWT e Spring Security, internacionalização de messages e validators, e algumas convenções que comumente econtrei em ambientes que já atuei </b>

O projeto inicialmente era composto apenas por uma API de vendas (vendas-api) e dependia de uma instância de MySQL local. <br>
Atualizei o projeto, containerizando a API do backend e a instância do MySQL para contâiners independentes, sendo orquestrado através do <b> docker-compose </b>. <br>
podendo utilizar:

```yaml
docker-compose up --build
#para construir e executar o ambiente completo
```
```yaml
docker-compose up vendas-api
#para executar apenas a API
```

