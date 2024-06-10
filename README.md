# Backend do projeto Weather App

O Backend di projeto Weather App é uma aplicação que envolve SpringBoot, JUnit para os testes unitários e Mockito para os testes de integração. Além disso, temos um banco de dados em MySQL para armazenar nossos dados.
O projeto foi proposto pela DB como parte do desafio final.<br>

Link do repositório do frontend do projeto:<br>
https://github.com/wendellvsilva/front-end-openweather
 

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
