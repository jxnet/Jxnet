
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES:= \
	cap_alloc.c \
	cap_proc.c \
	cap_extint.c \
	cap_flag.c \
	cap_text.c \
	cap_file.c

LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/include \
	$(LOCAL_PATH)/include/uapi

LOCAL_CFLAGS := -fPIC -Wall
LOCAL_CFLAGS += -D_LARGEFILE64_SOURCE -D_FILE_OFFSET_BITS=64 -Dlinux -Wall 
LOCAL_CFLAGS += -Wwrite-strings -Wpointer-arith -Wcast-qual -Wcast-align
LOCAL_CFLAGS += -Wstrict-prototypes -Wmissing-prototypes -Wnested-externs -Winline -Wshadow

LOCAL_MODULE := libcap

include $(BUILD_STATIC_LIBRARY)
