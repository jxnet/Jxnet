

JAVA_HOME = /usr/lib/jvm/jdk

JAVAC = $(JAVA_HOME)/bin/javac

JAVA_FLAGS = -g -Xlint -verbose


JAVA_SOURCE = \
	../java/com/ardikars/jxpcap/*.java \
	../java/com/ardikars/jxpcap/util/*.java \
	../test/com/ardikars/test/jxpcap/*.java

C_SOURCE = \
	../c/*.c ../c/util/*.c

C_FLAGS = -shared -fPIC -L. -I $(JAVA_HOME)/include/ -I $(JAVA_HOME)/include/linux -I /usr/lib/ -Wall  -std=gnu11

all:
	$(JAVAC) $(JAVA_FLAGS) $(JAVA_SOURCE) -h ../c/jni/ -d java/
	gcc $(C_FLAGS) $(C_SOURCE) -o ../libjxpcap.so -lpcap
	sudo mv ../libjxpcap.so /usr/lib
	rm -Rf ../*.log
