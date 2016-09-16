

ifneq ($(OS), Windows_NT)
	$(error This build script just for Windows NT.)
endif

ifeq ($(JAVA_HOME), )
	$(error JAVA_HOME environment is not set.)
endif

ifeq ($(PROCESSOR_ARCHITECTURE), AMD64)
	ARCH=x64
	CFLAGS=-Wl,--export-all-symbols -Wl,--add-stdcall-alias -m64 -shared
endif

ifeq ($(PROCESSOR_ARCHITECTURE), x86)
	ARCH=
	CFLAGS=-Wl,--export-all-symbols -Wl,--add-stdcall-alias -m32 -shared
endif

ifeq ($(WpdPack), )
	$(error can not find Winpcap SDK)
endif

SRC = \
	../src/jxnet.c \
	../src/utils.c \
	../src//addr_utils.c \
	../src/ids.c \
	../src/bpf.c	


CC = gcc

CFLAGS += -I"$(JAVA_HOME)/include" -I"$(JAVA_HOME)/include/win32" -I"$(WpdPack)/Include" -L"$(WpdPack)/Lib/$(ARCH)" -Wall -Werror

LDFLAGS = -lwpcap -liphlpapi

LIB_NAME = jxnet

all: $(LIB_NAME)

$(LIB_NAME):
	$(CC) $(CFLAGS) $(SRC) $(LDFLAGS) -o $@.dll
	

clean:
	rm $(LIB_NAME).*
