# Golden Raspberry Awards

Projeto que realiza a leitura dos indicados e vencedores de Pior Filme do Golden Raspberry Awards e os retorna para uma API.  

---

Para executar o projeto, após realizar o download ou clone do mesmo, basta executar os comandos abaixo:

* `cd golden-raspberry-awards/ `
* `mvn clean install`
* `cd target/`
* `java -jar golden-raspberry-awards-1.0.0-SNAPSHOT.jar`  

Obs.: Um alternativa é abrir o projeto em sua IDE de preferência e executar a classe main

Para executar os testes do projeto, após realizar o download ou clone do mesmo, basta executar os comandos abaixo:

* `cd golden-raspberry-awards/ `
* `mvn clean test`
* `cd target/`  

Obs.: Um alternativa é abrir o projeto em sua IDE de preferência e executar os testes através da mesma.

---

### Chamada da API

Após executar o projeto, deve-se realizar uma requisição `GET` para o endereço `http://localhost:8080/api/worst-movies/prizes-range`

### Melhorias

* Realizar um cache das transformações de dados do serviço utilizado para retornar para a API, dessa forma prevenindo processamento desnecessário.