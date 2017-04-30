
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES:= \
	progs/setcap.c

LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/libcap/include \
	$(LOCAL_PATH)/libcap/include/uapi

LOCAL_CFLAGS := -fPIC -Wall
LOCAL_CFLAGS += -Wall -Wwrite-strings -Wpointer-arith -Wcast-qual -Wcast-align -Wstrict-prototypes -Wmissing-prototypes -Wnested-externs -Winline -Wshadow

LOCAL_MODULE := libcap

include $(BUILD_STATIC_LIBRARY)
