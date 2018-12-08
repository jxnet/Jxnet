
FROM java:8

MAINTAINER Ardika Rommy Sanjaya <contact@ardikars.com>

RUN uname -a

# Install libpcap.
RUN apt-get update && \
    apt-get install -y libpcap0.8 wget

WORKDIR /usr/local/jxnet

RUN wget -c -O jxnet-spring-boot-starter-example.jar https://search.maven.org/remotecontent?filepath=com/ardikars/jxnet/jxnet-spring-boot-starter-example/1.4.8.Final/jxnet-spring-boot-starter-example-1.4.9.Final.jar

ENTRYPOINT ["java", "-jar", "jxnet-spring-boot-starter-example.jar"]
