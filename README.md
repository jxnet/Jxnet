
Jxnet
=====

Jxnet is a Java library for capturing and sending custom network packet buffers with no copies.
Jxnet wraps a native packet capture library (libpcap/npcap) via JNI (Java Native Interface).

[![Build status](https://ci.appveyor.com/api/projects/status/cdhg247wftehh8xe?svg=true)](https://ci.appveyor.com/project/ardikars/jxnet)
[![CircleCI](https://circleci.com/gh/ardikars/Jxnet/tree/master.svg?style=svg)](https://circleci.com/gh/ardikars/Jxnet/tree/master)

[ ![Download](https://api.bintray.com/packages/ardikars/maven/com.ardikars.jxnet/images/download.svg?version=1.1.3) ](https://bintray.com/ardikars/maven/com.ardikars.jxnet/1.1.3/link)
[![Maven Central](https://img.shields.io/maven-central/v/org.apache.maven/apache-maven.svg)](http://search.maven.org/#artifactdetails|com.ardikars|jxnet|1.1.3|)


HOW TO BUILD
============

./gradlew build -x test


HOW TO USE
==========

#### Maven project ####
Add a dependency to the pom.xml as like below:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                      http://maven.apache.org/xsd/maven-4.0.0.xsd">
  ...
  <dependencies>
    <dependency>
      <groupId>com.ardikars.jxnet</groupId>
      <artifactId>jxnet-core</artifactId>
      <version>1.1.3</version>
    </dependency>
    <dependency>
      <groupId>com.ardikars.jxnet</groupId>
      <artifactId>jxnet-packet</artifactId>
      <version>1.1.3</version>
    </dependency>
    <dependency>
      <groupId>com.ardikars.jxnet</groupId>
      <artifactId>jxnet-util</artifactId>
      <version>1.1.3</version>
    </dependency>
    ...
  </dependencies>
  ...
</project>
```

#### Gradle project ####
Add a dependency to the build.gradle as like below:

```
...
dependencies {
  compile 'com.ardikars.jxnet:jxnet-core:1.1.3'
  compile 'com.ardikars.jxnet:jxnet-packet:1.1.3'
  compile 'com.ardikars.jxnet:jxnet-util:1.1.3'
  ...
}
...
```

*) In Windows you need to install Npcap with WinPcap API-compatible Mode.


License
=======

GNU Lesser General Public License, Version 3


Contact
=======

Email: contact@ardikars.com


Issues
======

Have a question/bug? Please create an issue here on GitHub!

https://github.com/ardikars/Jxnet/issues

