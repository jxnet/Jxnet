
Jxnet
=====

Jxnet is a Java library for capturing and sending custom network packet buffers with no copies.
Jxnet wraps a native packet capture library (libpcap/npcap) via JNI (Java Native Interface).

[ ![Download](https://api.bintray.com/packages/ardikars/maven/com.ardikars.jxnet/images/download.svg?version=1.1.3) ](https://bintray.com/ardikars/maven/com.ardikars.jxnet/1.1.3/link)


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
      <version>1.1.4</version>
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

*) In Windows you need to install Npcap with Winpcap support.


License
=======

GNU Lesser General Public License, Version 3


Contact
=======

Email: contact@ardikars.com


Issues
======

Have a bug? Please create an issue here on GitHub!

https://github.com/ardikars/Jxnet/issues

