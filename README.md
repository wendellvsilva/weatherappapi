# Backend do projeto Weather App

O Backend do projeto Weather App é uma API robusta e eficiente, desenvolvida para registrar e gerenciar informações climáticas de diversas cidades. Este sistema permite aos usuários inserir, atualizar, recuperar e deletar dados meteorológicos, oferecendo uma interface simples e intuitiva para a gestão de informações sobre o clima.

Desenvolvido utilizando o framework Spring Boot, o projeto conta com testes unitários e de integração realizados com JUnit e Mockito, garantindo a confiabilidade e a integridade do código. Além disso, utilizamos um banco de dados MySQL para o armazenamento seguro e estruturado dos dados meteorológicos.

Este projeto foi proposto pela DB como parte do desafio final, desafiando-nos a aplicar nossos conhecimentos em uma solução prática e funcional.<br>

Link do repositório do frontend do projeto:<br>
https://github.com/wendellvsilva/front-end-openweather

. **Tecnologias utilizadas**
    - Spring Boot: Framework principal para desenvolvimento da aplicação.
    - JUnit: Framework para testes unitários.
    - Mockito: Biblioteca para testes de integração.
    - MySQL: Banco de dados relacional utilizado para armazenamento dos dados.
    - Maven: Ferramenta de automação de compilação e gerenciamento de dependências.
    - JaCoCo: Ferramenta para medição de cobertura de código em testes.
 

1. **Configurações do Spring Boot:**
  - O arquivo application.properties deve conter a de conexão com o banco de dados MySQL.
  ```bash
    spring.jpa.hibernate.ddl-auto=update
    spring.datasource.url=jdbc:mysql://localhost:3306/weatherapp
    spring.datasource.username=seuUsername
    spring.datasource.password=suaSenha
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    spring.flyway.enabled=false
```
2. **Como executar o projeto**
    - Abra o terminal e digite o comando ` mvn spring-boot:run`
    - Faça o comando ` SHIFT + F10`

## Fazendo requisições
   - Use o software Postman ou o Insomnia para fazer as requisições.
   
**Método POST**
  - `http://localhost:8080/cidades`
  ```bash
  {
    "cidade": "Recife",
    "clima": {
     "situacaoClima": "ENSOLARADO",
    "turno": "MANHÃ",
    "data": "07/06/2024",
    "umidade": "80",
    "precipitacao": "10",
    "temperatura": "25",
    "velVento": "15",
    "tempMaxima": "28",
    "tempMinima": "20"
    }
}
```

**Metódo GET
  - `http://localhost:8080/cidades`<br>

  
**Método PUT(o ID deve ser adicionado ao JSON)**
  - `http://localhost:8080/cidades`
  ```bash
  {
	"id": 1,
	"cidade" : "Olinda",
  "dadosCadastroClima": {
    "situacaoClima": "CHOVENDO",
    "turno": "MANHÃ",
    "data": "07/06/2024",
    "umidade": "80",
    "precipitacao": "10",
    "temperatura": "25",
    "velVento": "15",
    "tempMaxima": "28",
    "tempMinima": "20"
  }
}

  ```
**Métódo DELETE**
  - `http://localhost:8080/cidades/1`

**Como rodar os testes**
 - Abra o terminal e digite o comando ` mvn test`

