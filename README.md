
Jxnet
=====

Jxnet is a Java library for capturing and sending custom network packet buffers with no copies.
Jxnet wraps a native packet capture library (libpcap/npcap) via JNI (Java Native Interface).

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/4d6ca7f3d9214098b1436990ac76a6cd)](https://www.codacy.com/project/jxnet/Jxnet/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jxnet/Jxnet&amp;utm_campaign=Badge_Grade_Dashboard)
[![Coverage Status](https://coveralls.io/repos/github/jxnet/Jxnet/badge.svg?branch=)](https://coveralls.io/github/jxnet/Jxnet?branch=)
[![Build status](https://ci.appveyor.com/api/projects/status/ev4t6t1ssacwj18j?svg=true)](https://ci.appveyor.com/project/jxnet/jxnet)

[ ![Download](https://api.bintray.com/packages/ardikars/maven/com.ardikars.jxnet/images/download.svg?version=1.3.0.Final) ](https://bintray.com/ardikars/maven/com.ardikars.jxnet/1.3.0.Final/link)


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

  - ##### Gradle project
>> Add a dependency to the build.gradle as like below:
>>>
>>> ```
>>> dependencies { 
>>>     compile 'com.ardikars.jxnet:jxnet-core:1.3.0.Final'
>>>     compile 'com.ardikars.jxnet:jxnet-context:1.3.0.Final'
>>> }
>>>```
  - ##### Maven project
>> Add a dependency to the pom.xml as like below:
>>>
>>> ```
>>> <dependencies>
>>>     <dependency>
>>>         <groupId>com.ardikars.jxnet</groupId>
>>>         <artifactId>jxnet-core</artifactId>
>>>         <version>1.3.0.Final</version>
>>>     </dependency>
>>>     <dependency>
>>>         <groupId>com.ardikars.jxnet</groupId>
>>>         <artifactId>jxnet-context</artifactId>
>>>         <version>1.3.0.Final</version>
>>>     </dependency>
>>> </dependencies>
>>>```
  - ##### Example Application
  
```java
public class ExampleApplication {

    public static class Initializer implements ApplicationInitializer {

        public void initialize(Context context) {
            context.addLibrary(new DefaultLibraryLoader());
        }

    }

    public static void main(String[] args) throws IOException {
        int maxPacket = 10;
        StringBuilder errbuf = new StringBuilder();
        Application.run("Example", "1.0.0", Initializer.class, new ApplicationContext());
        String device = Jxnet.PcapLookupDev(errbuf);
        Pcap pcap = Pcap.live(
                new Pcap.Builder()
                        .source(device)
                        .immediateMode(ImmediateMode.IMMEDIATE)
                        .errbuf(errbuf)
        );
        BpfProgram bpfProgram = BpfProgram.bpf(
                new BpfProgram.Builder()
                        .pcap(pcap)
                        .bpfCompileMode(BpfProgram.BpfCompileMode.OPTIMIZE)
                        .filter("tcp")
                        .netmask(Inet4Address.valueOf("255.255.255.0").toInt())
        );
        Context context = ApplicationContext.newApplicationContext(pcap, bpfProgram);
        context.pcapLoop(maxPacket, (user, h, bytes) -> {
            byte[] buffer = new byte[bytes.capacity()];
            bytes.get(buffer, 0, buffer.length);
            System.out.println(Hexs.toPrettyHexDump(buffer));
        }, null);
        context.pcapClose();
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


Jxnet dependencies
==================
  - com.ardikars.common:common-util
  - com.ardikars.common:common-net

License
=======

GNU Lesser General Public License, Version 3

```
/**
 * Copyright (C) 2017-2018  Ardika Rommy Sanjaya <contact@ardikars.com>
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

https://github.com/ardikars/Jxnet/issues

