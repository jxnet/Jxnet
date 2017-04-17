
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
	LDFLAGS += -L../lib/x64 
endif

ifeq ($(PROCESSOR_ARCHITECTURE), x86)
	LDFLAGS += -L../lib 
endif

SRC = \
	../src/ids.c \
	../src/preconditions.c \
	../src/bpf.c \
	../src/jxnet.c \
	../src/utils.c \
	../src/addr_utils.c \
	../src/mac_address.c

LIB_NAME = jxnet

all: $(LIB_NAME)

$(LIB_NAME):
	$(CC) -shared $(CFLAGS) $(LDFLAGS) $(SRC) -o $@.dll -lwpcap -liphlpapi
	
clean:
	rm $(LIB_NAME).*

