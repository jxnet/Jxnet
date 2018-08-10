
 ##
 # Copyright (C) 2017  Ardika Rommy Sanjaya
 ##

LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := jxnet

LOCAL_SRC_FILES := \
	src/jxnet.c \
	src/ids.c \
	src/utils.c \
	src/preconditions.c \
	src/mac_address.c

LOCAL_STATIC_LIBRARIES := libpcap

LOCAL_C_INCLUDES := $(LOCAL_PATH)/libpcap
include $(BUILD_SHARED_LIBRARY)
include $(LOCAL_PATH)/libpcap/Android.mk
