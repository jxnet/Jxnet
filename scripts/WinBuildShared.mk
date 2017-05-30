
 ##
 # Copyright (C) 2017  Ardika Rommy Sanjaya
 ##

ifneq ($(OS), Windows_NT)
	$(error This build script just for Windows NT.)
endif

ifeq ($(JDK_HOME), )
	$(error JDK_HOME environment is not set.)
endif

CC = gcc
CFLAGS = -Wall 
LDFLAGS =  

CFLAGS += -I$(JDK_HOME)/include -I$(JDK_HOME)/include/win32 -I../jni/include 

ifeq ($(PROCESSOR_ARCHITECTURE), AMD64)
	CFLAGS += -Wl,--export-all-symbols -Wl,--add-stdcall-alias -m64 -shared 
	LDFLAGS += -L../jni/lib/x64 
endif

ifeq ($(PROCESSOR_ARCHITECTURE), x86)
	CFLAGS += -Wl,--export-all-symbols -Wl,--add-stdcall-alias -m32 -shared 
	LDFLAGS += -L../jni/lib 
endif

SRC = \
	../jni/src/ids.c \
	../jni/src/preconditions.c \
	../jni/src/bpf.c \
	../jni/src/jxnet.c \
	../jni/src/utils.c \
	../jni/src/mac_address.c

LIB_NAME = jxnet

all: $(LIB_NAME)

$(LIB_NAME):
	$(CC) $(CFLAGS) $(LDFLAGS) $(SRC) -o $@.dll -lwpcap -liphlpapi
	
clean:
	rm $(LIB_NAME).*
