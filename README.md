
Jxnet
=====

Jxnet is a Java library for capturing and sending custom network packet buffers with no copies.
Jxnet wraps a native packet capture library (libpcap/npcap) via JNI (Java Native Interface).

[![Build status](https://ci.appveyor.com/api/projects/status/cdhg247wftehh8xe?svg=true)](https://ci.appveyor.com/project/ardikars/jxnet)
[![CircleCI](https://circleci.com/gh/ardikars/Jxnet/tree/master.svg?style=svg)](https://circleci.com/gh/ardikars/Jxnet/tree/master)

[ ![Download](https://api.bintray.com/packages/ardikars/maven/com.ardikars.jxnet/images/download.svg?version=1.1.4) ](https://bintray.com/ardikars/maven/com.ardikars.jxnet/1.1.4/link)


HOW TO BUILD
============

./gradlew build -x test


HOW TO USE
==========

#### Gradle project ####
Add a dependency to the build.gradle as like below:

```
...
dependencies {
  compile 'com.ardikars.jxnet:jxnet-core:1.1.4'
  compile 'com.ardikars.jxnet:jxnet-packet:1.1.4'
  compile 'com.ardikars.jxnet:jxnet-util:1.1.4'
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

Email: Ardika Rommy Sanjaya (contact@ardikars.com)


Issues
======

Have a bug? Please create an issue here on GitHub!

https://github.com/ardikars/Jxnet/issues

