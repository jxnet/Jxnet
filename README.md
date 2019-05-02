
Jxnet
=====

Jxnet is a Java library for capturing and sending custom network packet buffers with no copies.
Jxnet wraps a native packet capture library (libpcap/npcap) via JNI (Java Native Interface).

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/4d6ca7f3d9214098b1436990ac76a6cd)](https://www.codacy.com/project/jxnet/Jxnet/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jxnet/Jxnet&amp;utm_campaign=Badge_Grade_Dashboard)
[![Build status](https://ci.appveyor.com/api/projects/status/ev4t6t1ssacwj18j?svg=true)](https://ci.appveyor.com/project/jxnet/jxnet)
[![CircleCI](https://circleci.com/gh/jxnet/Jxnet.svg?style=svg)](https://circleci.com/gh/jxnet/Jxnet)
[![Coverage Status](https://coveralls.io/repos/github/jxnet/Jxnet/badge.svg?branch=)](https://coveralls.io/github/jxnet/Jxnet?branch=)

[ ![Download](https://api.bintray.com/packages/ardikars/maven/com.ardikars.jxnet/images/download.svg?version=1.5.4.RELEASE) ](https://bintray.com/ardikars/maven/com.ardikars.jxnet/1.5.4.RELEASE/link)


Features
=======

- Lightweight (`jxnet-core` is around 160 KB in size)
- Low lavel and asynchronous `event-driven` network application libarary
- Easy to configure and integrated with popular java framework (`spring-boot`)
- Using `direct buffer` (without copying buffer for minimized unnecessary memory copy)
- Better `throughput` and `lower latency`
- Maintainable and Immutable (`thread-safe`) packet classes

Getting Started
===============

### Prerequisites

  - ##### Platforms
    - Windows 7/8/10 (x86/x86_64)
    - Linux (x86/x86_64)
    - Mac OS
  - ##### Java Version
    - Java 6 (java 8 required if you are using spring-boot)
  - ##### Dependecies
    - Windows
      - Winpcap or Npcap
    - Linux
      - Libpcap
    - Mac OS
      - Libpcap


### Supported Protocol
List of supported protocol available at [Jxpacket](https://github.com/jxnet/Jxpacket).


### How to Use

  - ##### Maven project
>> Add a dependency to the pom.xml as like below:
>>>
>>> ```
>>> <dependencies>
>>>     <dependency>
>>>         <groupId>com.ardikars.jxnet</groupId>
>>>         <artifactId>jxnet-spring-boot-starter</artifactId>
>>>     </dependency>
>>> </dependencies>
>>>
>>> <dependencyManagement>
>>>     <dependencies>
>>>         <dependency>
>>>             <groupId>com.ardikars.jxnet</groupId>
>>>             <artifactId>jxnet</artifactId>
>>>             <version>1.5.4.RELEASE</version>
>>>             <type>pom</type>
>>>             <scope>import</scope>
>>>         </dependency>
>>>     </dependencies>
>>> </dependencyManagement>
>>>```

  - ##### Gradle project
>> Add a dependency to the build.gradle as like below:
>>>
>>> ```
>>> apply plugin: 'io.spring.dependency-management'
>>>
>>> dependencies {
>>>     compile ("com.ardikars.jxnet:jxnet-spring-boot-starter")
>>> }
>>>
>>> dependencyManagement {
>>>     imports { mavenBom("com.ardikars.jxnet:jxnet:1.5.4.RELEASE") }
>>> }
>>> ```  

  - ##### Example Application
  
```java
@SpringBootApplication
public class Application extends AbstractJxnetApplicationRunner<String>  {
    
    public static final int MAX_PACKET = -1; // infinite loop
    
    @Override
    public void run(String... args) throws Exception {
        showSystemInfo();
        showNetworkInfo();
        loop(MAX_PACKET, "Jxnet!");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```
  - ##### Pcap Packet Handler Configuration
  
```java
@EnablePacket
@Configuration
public class DefaultJxpacketHandler implements JxpacketHandler<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultJxpacketHandler.class.getName());

    private static final String PRETTY_FOOTER = "+---------------------------------------------------"
            + "--------------------------------------------------+";

    private void print(Pair<PcapPktHdr, Packet> packet) {
        Iterator<Packet> iterator = packet.getRight().iterator();
        LOGGER.info("Pcap packet header : {}", packet.getLeft());
        LOGGER.info("Packet header      : ");
        while (iterator.hasNext()) {
            LOGGER.info("{}", iterator.next());
        }
        LOGGER.info(PRETTY_FOOTER);
    }

    @Override
    public void next(String argument, Future<Pair<PcapPktHdr, Packet>> packet) throws ExecutionException, InterruptedException {
        LOGGER.info("User argument      : {}", argument);
        print(packet.get());
    }

}
```

  - #### Spring properties (Optional)
    
| Property                      | Pcap Type            | Default Value                      | Description                                      |
| :-----------------------------| :------------------- | :--------------------------------- | :----------------------------------------------- |
| jxnet.source                  | LIVE                 | Auto selected                      | A device (source) used to create a pcap handle   |
| jxnet.snaphot                 | LIVE                 | 65535                              | Dimension of the packet portion (in bytes)       |
| jxnet.promiscuous             | LIVE                 | `PROMISCUOUS`                      | Pomiscuous mode                                  |
| jxnet.timeout                 | LIVE                 | 2000                               | Packet buffer timeout                            |
| jxnet.immediate               | LIVE                 | `IMMEDIATE`                        | Immediate mode                                   |
| jxnet.timestampType           | LIVE                 | `HOST`                             | Typestamp type                                   |
| jxnet.timestampPrecision      | LIVE                 | `MICRO`                            | Typestamp precision                              |
| jxnet.rfmon                   | LIVE                 | `NON_RFMON`                        | Radio frequency monitor mode                     |
| jxnet.blocking                | LIVE                 | false                              | Blocking mode                                    |
| jxnet.direction               | LIVE                 | `PCAP_D_INOUT`                     | Specify a direction that packet will be captured |
| jxnet.datalink                | DEAD                 | 1 (Ethernet)                       | Datalink type                                    |
| jxnet.file                    | OFFLINE              | `null`                             | Absulute path of pcap file                       |
| jxnet.bpfCompileMode          | LIVE                 | `OPTIMIZE`                         | Berkeley packet filter compile mode              |
| jxnet.filter                  | LIVE                 | `null`                             | Filtering expression syntax                      |
| jxnet.pcapType                | LIVE/OFFLINE/DEAD    | `LIVE`                             | Pcap type (live/offline/dead)                    |
| jxnet.numberOfThread          | LIVE, OFFLINE        | Depends on available processors    | Number of pooled thread                          |
| jxnet.jxpacket.autoRegister   | LIVE, OFFLINE        | false                              | Auto register jxpacket protocol                  |


Build Jxnet from Source
=============================

### Setup Development Environment (JDK 1.8)
  - ##### Windows (x86_64)
    - Install Mingw64 (SLJL) for cross compilation and add the path of the Mingw64 ```bin``` directory to the environment variable ```PATH```. See ```.\.scripts\InstallMingw64.ps1```.
  - ##### Linux
    - Install Gcc
    - Install Libpcap-Dev, ex ```# apt-get install libpcap-dev``` on debian.
  - ##### Mac OS
    - Install Gcc/Clang/XCode

### Build
   - ```./gradlew clean build```
   
### Skip Test
   - ```./gradlew clean build -x test```



### Build Only Native Shared Library
  - Gradle
    - ./gradlew -p jxnet-native clean build
  - CMake
    - Install Gcc & CMake & Make & Libpcap-Dev
    - ```mkdir jxnet-native/build && cd jxnet-native/build && cmake ../ && make```
  - Autotools
    - Install Autoconf & Automake & Make & Libtool & Libpcap-Dev
    - ```cd jxnet-native/ && ./bootstrap.sh && ./configure && make```


License
=======

GNU Lesser General Public License, Version 3

```
/**
 * Copyright (C) 2015-2018 Jxnet
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
```

Issues
======

Have a bug or question? Please create an issue [here on Github!](https://github.com/jxnet/Jxnet/issues "Github issues")
