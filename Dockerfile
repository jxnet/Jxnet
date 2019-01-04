
FROM java:8

# Install libpcap.
RUN apt-get update && \
apt-get install -y libpcap0.8 libpcap-dev wget git gcc

WORKDIR /usr/local/jxnet

RUN git clone https://github.com/jxnet/Jxnet.git Jxnet
RUN cd Jxnet && ./gradlew build -x test
ENTRYPOINT ["java", "-jar", "jxnet-spring-boot-starter-example/build/libs/jxnet-spring-boot-starter-example-1.5.4.RELEASE.jar"]
