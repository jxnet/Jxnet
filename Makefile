
JAVA_HOME=/usr/lib/jvm/jdk-8-oracle-arm-vfp-hflt
JAVAC=$(JAVA_HOME)/bin/javac
JAVA=$(JAVA_HOME)/bin/java
JAR=$(JAVA_HOME)/bin/jar
JNI_INCLUDE=$(JAVA_HOME)/include

GCC=/usr/bin/gcc
PCAP_INCLUDE=/usr/lib

PLATFORM=$(shell "uname")

ifeq ($(PLATFORM), Linux)
	JNI_INCLUDE_PLATFORM=$(JNI_INCLUDE)/linux
	C_COMPILE_OPTION= -shared -fPIC -L.
	SUFFIX=.so
	INSTALL_DIR=/usr/lib
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
	com/jxpcap/*.class \
	com/jxpcap/util/*.class \
	com/jxpcap/util/packet/*.class \
	com/jxpcap/packet/*.class

JAVA_FLAGS= -g -verbose -Werror

C_FLAGS= $(C_COMPILE_OPTION) -I $(PCAP_INCLUDE) -I $(JNI_INCLUDE) -I $(JNI_INCLUDE_PLATFORM)

all:
	$(JAVAC) $(JAVA_FLAGS) $(JAVA_SRC) -h c/jni -d bin/java
	$(GCC) $(C_FLAGS) $(C_SRC) -o libjxpcap$(SUFFIX) -lpcap
	cd bin/java/ && $(JAR) -cvf ../../jxpcap.jar $(JAVA_CLASS)
	
install:
	mv $(C_LIB_TARGET) $(INSTALL_DIR)
	
uninstall:
	rm $(INSTALL_DIR)/$(C_LIB_TARGET)
	
clean:
	rm -Rf jxpcap.jar libjxpcap.so
	rm -Rf bin/java/*
	rm -Rf bin/c/*