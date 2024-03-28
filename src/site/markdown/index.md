# Reactor Netty HTTP Client Example

A small Netty HTTP client to serve as an example.

To use the project first package it:

```
mvn clean package
```

The JAR will be a runnable Java file. It can be executed like this:

```
java -jar target/client.jar post localhost 8080 Hello
```

To show other commands:

```
java -jar target/client.jar -h
```

## Other Netty examples

### TCP

- [Netty TCP Client Example](https://github.com/Bernardo-MG/netty-tcp-client-example)
- [Netty TCP Server Example](https://github.com/Bernardo-MG/netty-tcp-server-example)
- [Netty TCP Proxy Example](https://github.com/Bernardo-MG/netty-tcp-proxy-example)

### TCP Reactive

- [Reactor Netty TCP Client Example](https://github.com/Bernardo-MG/reactor-netty-tcp-client-example)
- [Reactor Netty TCP Server Example](https://github.com/Bernardo-MG/reactor-netty-tcp-server-example)
- [Reactor Netty TCP Proxy Example](https://github.com/Bernardo-MG/reactor-netty-tcp-proxy-example)

### HTTP

- [Netty HTTP Client Example](https://github.com/Bernardo-MG/reactor-netty-http-client-example)
- [Netty HTTP Server Example](https://github.com/Bernardo-MG/reactor-netty-http-server-example)
