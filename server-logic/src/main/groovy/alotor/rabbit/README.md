# Down The RabbitMQ Hole. Greach 2016

This repository contains the example source code for my [Greach 2016](http://greachconf.com/) Down The RabbitMQ Hole.

The code consist of a complete example of a WebSockets + STOMP RabbitMQ + Spring Integration that uses the Twitter Streaming API to deliver messages asynchronously to a simple Javascript frot-end

## Installation

Before running the code you should install the JDK 1.8 and asure it's on the PATH environment variable. To check if your code is running the command `java -version` should display something along the lines of:

```
openjdk version "1.8.0_74"
OpenJDK Runtime Environment (build 1.8.0_74-b02)
OpenJDK 64-Bit Server VM (build 25.74-b02, mixed mode)
```

You also need to install RabbitMQ with the STOMP plugin.

Alternatively, this repository contains a docker image that already has RabbitMQ exposed you you can just simple do:

```
cd docker/rabbitmq
./build.sh
./run.sh
```

This will start a RabbitMQ server in the host `172.17.0.2` (if there are no more docker processes running)


## Execution

To execute the samples you should edit the files:

`server-logic/src/main/resources/application.yml`
`server-websocket/src/main/resources/application.yml`

And change the "default" parameters of RabbitMQ on your machine. Once this is set up you just need to open two terminals and start the two services:

`./gradlew server-websocket:bootRun`
`./gradlew server-logic:bootRun`

## Profit

Access [http://localhost:3333] and put any tag you want to search (whithout the hash symbol)

## License

Licensed under [BSD License](https://opensource.org/licenses/BSD-2-Clause).
