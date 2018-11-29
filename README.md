
Jxnet
=====

Jxnet is a Java library for capturing and sending custom network packet buffers with no copies.
Jxnet wraps a native packet capture library (libpcap/npcap) via JNI (Java Native Interface).

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/4d6ca7f3d9214098b1436990ac76a6cd)](https://www.codacy.com/project/jxnet/Jxnet/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jxnet/Jxnet&amp;utm_campaign=Badge_Grade_Dashboard)
[![Build status](https://ci.appveyor.com/api/projects/status/ev4t6t1ssacwj18j?svg=true)](https://ci.appveyor.com/project/jxnet/jxnet)

[ ![Download](https://api.bintray.com/packages/ardikars/maven/com.ardikars.jxnet/images/download.svg?version=1.4.8.Final) ](https://bintray.com/ardikars/maven/com.ardikars.jxnet/1.4.8.Final/link)


Getting Started
===============

### Prerequisites

  - ##### Platforms
    - Windows 7/8/10 (x86/x86_64)
    - Linux (x86/x86_64)
    - Mac OS
  - ##### Java Version
    - Java 8 (or newer)
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
>>>             <version>1.4.8.Final</version>
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
>>>     imports { mavenBom("com.ardikars.jxnet:jxnet:1.4.8.Final") }
>>> }
>>> ```  

  - ##### Example Application
  
```java
@SpringBootApplication
public class Application implements CommandLineRunner  {

    public static final int MAX_PACKET = -1; // infinite loop

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class.getName());

    private Context context;
    private PcapIf pcapIf;
    private MacAddress macAddress;

    @Autowired
    private PcapHandler<String> pcapHandler;

    public Application(Context context, PcapIf pcapIf, MacAddress macAddress) {
        this.context = context;
        this.pcapIf = pcapIf;
        this.macAddress = macAddress;
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Network Interface : {}", pcapIf.getName());
        LOGGER.info("MAC Address       : {}", macAddress);
        LOGGER.info("Addresses         : ");
        for (PcapAddr addr : pcapIf.getAddresses()) {
            if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
                LOGGER.info("\tAddress       : {}", Inet4Address.valueOf(addr.getAddr().getData()));
                LOGGER.info("\tNetwork       : {}", Inet4Address.valueOf(addr.getNetmask().getData()));
            }
        }
        context.pcapLoop(MAX_PACKET, pcapHandler, "Jxnet!");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```
  - ##### Pcap Packet Handler Configuration
  
```java
@Configuration
public class DefaultPacketHandler implements PacketHandler<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPacketHandler.class.getName());

    private static final String PRETTY_FOOTER = ""
            + "+-----------------------------------------------------------------------------------------------------+";

    @Override
    public void next(String argument, PcapPktHdr header, Future<Packet> packet) throws ExecutionException, InterruptedException {
        Iterator<Packet> iterator = packet.get().iterator();
        while (iterator.hasNext()) {
            LOGGER.info(iterator.next().toString());
        }
        LOGGER.info(PRETTY_FOOTER);
    }

}
```

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

Contact
=======

Email: Ardika Rommy Sanjaya (contact@ardikars.com)


Issues
======

Have a bug? Please create an issue here on GitHub!

https://github.com/jxnet/Jxnet/issues
