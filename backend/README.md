# Gruly API REST

Antes de tudo, você deve ter o maven instalado e configurado na sua máquina, caso não esteja usando o **Gitpod**.

## Executando o projeto
Entre no diretório *__backend__* e digite no terminal:
> __mvn spring-boot:run__

Isto irá subir servidor e criará as tabelas do banco de dados automaticamente.

### Debug da aplicação
Pra executar a aplicação em modo _debug_, digite o seguinte no terminal:
>__mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"__

Isso fará com que a aplicação seja iniciada em modo _debug_ remoto e irá escutar por um debugger na porta **5005**.
Em seguida, na aba de Debug no VSCode, execute a task **Debug (Attach)**.

---

## Problemas durante a execução do projeto

Caso o terminal apresente erros no momento da execução da aplicação por inconsistência de dependências, insira no terminal:
> __mvn clean install spring-boot:run__

Isso irá limpar o cache das dependências do projeto, instalará novamente e executará a aplicação.

## Swagger
Para acessar documentação da API com Swagger, acesse **https://(url)/swagger-ui.html**