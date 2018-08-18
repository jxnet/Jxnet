
Jxnet
=====

Jxnet is a Java library for capturing and sending custom network packet buffers with no copies.
Jxnet wraps a native packet capture library (libpcap/npcap) via JNI (Java Native Interface).

[![CircleCI](https://circleci.com/gh/jxnet/Jxnet/tree/master.svg?style=svg)](https://circleci.com/gh/jxnet/Jxnet/tree/master)
[![Build status](https://ci.appveyor.com/api/projects/status/ev4t6t1ssacwj18j?svg=true)](https://ci.appveyor.com/project/jxnet/jxnet)

[ ![Download](https://api.bintray.com/packages/ardikars/maven/com.ardikars.jxnet/images/download.svg?version=1.1.4) ](https://bintray.com/ardikars/maven/com.ardikars.jxnet/1.1.4/link)


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
>>>     compile 'com.ardikars.jxnet:jxnet-core:1.1.5.RC8'
>>>     compile 'com.ardikars.jxnet:jxnet-context:1.1.5.RC8'
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
>>>         <version>1.1.5.RC8</version>
>>>     </dependency>
>>>     <dependency>
>>>         <groupId>com.ardikars.jxnet</groupId>
>>>         <artifactId>jxnet-context</artifactId>
>>>         <version>1.1.5.RC8</version>
>>>     </dependency>
>>> </dependencies>
>>>```


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

### Build Process
   - ```./gradlew clean build```
   
### Skip Unit Test
   - ```./gradlew clean build -x test```


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

