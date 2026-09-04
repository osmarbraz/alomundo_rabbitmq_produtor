# Produtor RabbitMQ com Java

Aplicação Java que funciona como **produtor de mensagens** utilizando o **RabbitMQ** e o serviço **CloudAMQP**.

## Tecnologias

* Java
* RabbitMQ
* CloudAMQP
* Maven
* RabbitMQ Java Client
* AMQPS

## Funcionamento

A aplicação:

1. Estabelece uma conexão segura com o RabbitMQ.
2. Cria um canal de comunicação.
3. Declara a fila `alo`.
4. Converte a mensagem para UTF-8.
5. Publica a mensagem na fila.
6. Utiliza uma mensagem persistente.
7. Exibe uma confirmação no console.

## Execução

Configure a URL de conexão do RabbitMQ no código:

```java
private static final String URL_RABBITMQ = "...";
```

A mensagem enviada é definida em:

```java
private static final String MENSAGEM = "Ola Mundo! CloudAMQP";
```

Depois, compile o projeto:

```bash
mvn clean package
```

Execute a aplicação:

```bash
mvn exec:java
```

## Observação

A URL do RabbitMQ contém credenciais de acesso. **Não publique credenciais reais no código-fonte ou em repositórios públicos.** Prefira utilizar variáveis de ambiente ou arquivos de configuração seguros.
