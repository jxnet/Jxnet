

JAVA_HOME=/usr/lib/jvm/jdk-8-oracle-arm-vfp-hflt
JAVAC=$(JAVA_HOME)/bin/javac
JAVA=$(JAVA_HOME)/bin/java
JAR=$(JAVA_HOME)/bin/jar
JNI_INCLUDE=$(JAVA_HOME)/include
JAVA_LIB_TARGET=jxpcap.jar

GCC=/usr/bin/gcc
INCLUDE=/usr/lib
PCAP_LIB=/usr/lib

PLATFORM=$(shell "uname")

ifeq ($(PLATFORM), Linux)
	JNI_PLATFORM=$(JNI_INCLUDE)/linux
	C_LIB_TARGET=libjxpcap.so
	INSTALL_DIR=/usr/lib
else
	JNI_PLATFORM=$(JNI_INCLUDE)/win32
	C_LIB_TARGET=jxpcap.dll
	INSTALL_DIR=C:\Windows\System32
endif

JAVA_SRC=\
	java/com/jxpcap/*.java \
	java/com/jxpcap/util/*.java \
	java/com/jxpcap/util/packet/*.java \
	java/com/jxpcap/packet/*.java
	
C_SRC=\
	c/*.c \
	c/util/*.c

JAVA_CLASS=\
	bin/java/com/jxpcap/*.class \
	bin/java/com/jxpcap/util/*.class \
	bin/java/com/jxpcap/util/packet/*.class \
	bin/java/com/jxpcap/packet/*.class

JAVA_FLAGS= -g -verbose -Werror

C_FLAGS= -shared -L $(PCAP_LIB) -I $(INCLUDE) -I $(JNI_INCLUDE) -I $(JNI_PLATFORM)

all:
	$(JAVAC) $(JAVA_FLAGS) $(JAVA_SRC) -h c/jni -d bin/java
	$(GCC) $(C_FLAGS) $(C_SRC) $(C_LIB_FLAGS) -o $(C_LIB_TARGET) -lpcap
	$(JAR) -cvf $(JAVA_LIB_TARGET) $(JAVA_CLASS)
	
install:
	mv $(C_LIB_TARGET) $(INSTALL_DIR)
	
uninstall:
	rm $(INSTALL_DIR)/$(C_LIB_TARGET)
	
clean:
	rm -Rf $(C_LIB_TARGET) $(JAVA_LIB_TARGET)
	rm -Rf bin/java/*
	rm -Rf bin/c/*