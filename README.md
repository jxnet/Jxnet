
Jxnet
=====

Jxnet is a Java library for capturing and sending custom network packet buffers with no copies.
Jxnet wraps a native packet capture library (libpcap/npcap) via JNI (Java Native Interface).

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/4d6ca7f3d9214098b1436990ac76a6cd)](https://www.codacy.com/project/jxnet/Jxnet/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jxnet/Jxnet&amp;utm_campaign=Badge_Grade_Dashboard)
[![Coverage Status](https://coveralls.io/repos/github/jxnet/Jxnet/badge.svg?branch=)](https://coveralls.io/github/jxnet/Jxnet?branch=)
[![Build status](https://ci.appveyor.com/api/projects/status/ev4t6t1ssacwj18j?svg=true)](https://ci.appveyor.com/project/jxnet/jxnet)

[ ![Download](https://api.bintray.com/packages/ardikars/maven/com.ardikars.jxnet/images/download.svg?version=1.4.4.Final) ](https://bintray.com/ardikars/maven/com.ardikars.jxnet/1.4.4.Final/link)


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
>>> <dependencyManagement>
>>>     <dependencies>
>>>         <dependency>
>>>             <groupId>com.ardikars.common</groupId>
>>>             <artifactId>common</artifactId>
>>>             <version>1.2.1.Final</version>
>>>             <type>pom</type>
>>>             <scope>import</scope>
>>>         </dependency>
>>>     <dependencies>
>>>         <dependency>
>>>             <groupId>com.ardikars.jxnet</groupId>
>>>             <artifactId>jxnet</artifactId>
>>>             <version>1.4.4.Final</version>
>>>             <type>pom</type>
>>>             <scope>import</scope>
>>>         </dependency>
>>>     </dependencies>
>>> </dependencyManagement>
>>>```
  - ##### Example Application
  
```java
@SpringBootApplication
public class Application implements CommandLineRunner  {

    public static final int MAX_PACKET = 10;

    public static final int WAIT_TIME_FOR_THREAD_TERMINATION = 10000;

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class.getName());

    @Autowired
    private Context context;
    
    @Autowired
    private PcapIf pcapIf;
    
    @Autowired
    private MacAddress macAddress;

    @Override
    public void run(String... args) throws Exception {
        final ExecutorService pool = Executors.newCachedThreadPool();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                pool.shutdownNow();
            }
        });
        LOGGER.info("Device        : " + pcapIf);
        LOGGER.info("MacAddress    : " + macAddress);
        context.pcapLoop(MAX_PACKET, new PcapHandler<String>() {
            @Override
            public void nextPacket(String user, PcapPktHdr pktHdr, ByteBuffer buffer) {
                byte[] bytes = new byte[buffer.capacity()];
                buffer.get(bytes, 0, bytes.length);
                String hexDump = Hexs.toPrettyHexDump(bytes);
                LOGGER.info("User argument : " + user);
                LOGGER.info("Packet header : " + pktHdr);
                LOGGER.info("Packet buffer : \n" + hexDump);
            }
        }, "Jxnet!", pool);
		pool.shutdown();
		pool.awaitTermination(WAIT_TIME_FOR_THREAD_TERMINATION, TimeUnit.MICROSECONDS);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```
  - ##### Pcap Configuration (Optional)
  
```java
@Configuration
public class Config {
    
    @Bean
    @Primary
    public static PcapIf pcapIf(StringBuilder errbuf) throws DeviceNotFoundException {
        String source = null;
        List<PcapIf> alldevsp = new ArrayList<>();
        if (PcapFindAllDevs(alldevsp, errbuf) != OK && LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.warning("Error: {}" + errbuf.toString());
        }
        if (source == null || source.isEmpty()) {
            for (PcapIf dev : alldevsp) {
                for (PcapAddr addr : dev.getAddresses()) {
                    if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET && addr.getAddr().getData() != null) {
                        Inet4Address d = Inet4Address.valueOf(addr.getAddr().getData());
                        if (!d.equals(Inet4Address.LOCALHOST) && !d.equals(Inet4Address.ZERO)) {
                            return dev;
                        }
                    }
                }
            }
        } else {
            for (PcapIf dev : alldevsp) {
                if (dev.getName().equals(source)) {
                    return dev;
                }
            }
        }
        throw new DeviceNotFoundException("No device connected to the network.");
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
