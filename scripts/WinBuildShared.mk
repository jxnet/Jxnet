
ifneq ($(OS), Windows_NT)
	$(error This build script just for Windows NT.)
endif

ifeq ($(JAVA_HOME), )
	$(error JAVA_HOME environment is not set.)
endif

CC = gcc
CFLAGS = -Wall 
LDFLAGS =  

CFLAGS += -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/win32 -I../include 

ifeq ($(PROCESSOR_ARCHITECTURE), AMD64)
	CFLAGS += -m64
	LDFLAGS += -L../jni/lib/x64 
endif

ifeq ($(PROCESSOR_ARCHITECTURE), x86)
	CFLAGS += -m32
	LDFLAGS += -L../jni/lib 
endif

SRC = \
	../jni/src/ids.c \
	../jni/src/preconditions.c \
	../jni/src/bpf.c \
	../jni/src/jxnet.c \
	../jni/src/utils.c \
	../jni/src/mac_address.o

LIB_NAME = jxnet

all: $(LIB_NAME)

$(LIB_NAME):
	$(CC) -shared $(CFLAGS) $(LDFLAGS) $(SRC) -o $@.dll -lwpcap -liphlpapi
	
clean:
	rm $(LIB_NAME).*

