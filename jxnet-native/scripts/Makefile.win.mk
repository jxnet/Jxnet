
ifneq ($(OS), Windows_NT)
    $(error Your platform is not supported by this script.)
endif

ifeq ($(JAVA_HOME), )
    $(error JAVA_HOME system environment is not set.)
else
    $(info JAVAHOME : $(JAVA_HOME))
endif

ifeq ($(CC), )
    $(error CC system environment is not set.)
else
    $(info CC : $(CC))
endif

ifeq ($(PROCESSOR_ARCHITECTURE), AMD64)
    ARCH=x64
    CFLAGS=-Wl,--export-all-symbols -Wl,--add-stdcall-alias -m64 -shared
    $(info Architecture : x64)
endif

ifeq ($(PROCESSOR_ARCHITECTURE), x86)
    ARCH=
    CFLAGS=-Wl,--export-all-symbols -Wl,--add-stdcall-alias -m32 -shared
    $(info Architecture : x86)
endif

SRC = ../src/bpf.c  ../src/ids.c  ../src/jxnet.c  ../src/mac_address.c  ../src/preconditions.c  ../src/utils.c

CFLAGS += -I"$(JAVA_HOME)/include" -I"$(JAVA_HOME)/include/win32" -I"../include" -L"../lib/$(ARCH)" -Wall -Werror

LDFLAGS = -lwpcap -liphlpapi

LIB_NAME = jxnet

all: $(LIB_NAME)

$(LIB_NAME):
	$(CC) $(CFLAGS) $(SRC) $(LDFLAGS) -o $@.dll


clean:
	rm $(LIB_NAME).*

