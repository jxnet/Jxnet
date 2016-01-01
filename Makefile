
# Specify where JAVA_HOHE directory is
JAVA_HOME=/usr/lib/jvm/jdk-8-oracle-arm-vfp-hflt

JAVAC=$(JAVA_HOME)/bin/javac
JAVA=$(JAVA_HOME)/bin/java
JAR=$(JAVA_HOME)/bin/jar
JNI_INCLUDE=$(JAVA_HOME)/include

GCC=/usr/bin/gcc

# Specify where pcap.h is
PCAP_INCLUDE=/usr/lib
#PCAP_INCLUDE=/usr/lib/pcap

# Target
JAVA_LIB_TARGET=jxpcap.jar

PLATFORM=$(shell "uname")

ifeq ($(PLATFORM), Linux)
	JNI_INCLUDE_PLATFORM=$(JNI_INCLUDE)/linux
	C_COMPILE_OPTION= -shared -fPIC -L.
	SUFFIX=.so
	INSTALL_DIR=/usr/lib
else
ifeq ($(PLATFORM), FreeBSD)
	JNI_INCLUDE_PLATFORM=$(JNI_INCLUDE)/freebsd
	C_COMPILE_OPTION= -shared -L.
	SUFFIX=.so
	INSTALL_DIR=/usr/lib
endif
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

JAVA_FLAGS= -g -verbose -Werror -Xlint

C_FLAGS= $(C_COMPILE_OPTION) -I $(PCAP_INCLUDE) -I $(JNI_INCLUDE) -I $(JNI_INCLUDE_PLATFORM)

all:
	$(JAVAC) $(JAVA_FLAGS) $(JAVA_SRC) -h c/jni -d bin/java
	$(GCC) $(C_FLAGS) $(C_SRC) -o libjxpcap$(SUFFIX) -lpcap
	cd bin/java/ && $(JAR) -cvf ../../$(JAVA_LIB_TARGET) $(JAVA_CLASS)
	
install:
	mv libjxpcap$(SUFFIX) $(INSTALL_DIR)
	
uninstall:
	rm $(INSTALL_DIR)/libjxpcap$(SUFFIX)
	
clean:
	rm -Rf $(JAVA_LIB_TARGET) libjxpcap$(SUFFIX)
	rm -Rf bin/java/*
	rm -Rf bin/c/*
