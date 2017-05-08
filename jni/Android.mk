
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := jxnet

LOCAL_SRC_FILES := \
	src/jxnet.c \
	src/ids.c \
	src/utils.c \
	src/addr_utils.c \
	src/preconditions.c \
	src/mac_address.c

LOCAL_STATIC_LIBRARIES := libpcap

ifeq ($(TARGET_ARCH_ABI), armeabi)
	LOCAL_C_INCLUDES := $(LOCAL_PATH)/libpcap
	include $(BUILD_SHARED_LIBRARY)
	include $(LOCAL_PATH)/libpcap/Android.mk
endif

ifeq ($(TARGET_ARCH_ABI), armeabi-v7a)
	LOCAL_C_INCLUDES := $(LOCAL_PATH)/libpcap
	include $(BUILD_SHARED_LIBRARY)
	include $(LOCAL_PATH)/libpcap/Android.mk
endif

ifeq ($(TARGET_ARCH_ABI), arm64-v8a)
	LOCAL_C_INCLUDES := $(LOCAL_PATH)/libpcap-latest
	include $(BUILD_SHARED_LIBRARY)
	include $(LOCAL_PATH)/libpcap-latest/Android.mk
endif

ifeq ($(TARGET_ARCH_ABI), x86)
	LOCAL_C_INCLUDES := $(LOCAL_PATH)/libpcap
	include $(BUILD_SHARED_LIBRARY)
	include $(LOCAL_PATH)/libpcap/Android.mk
endif

ifeq ($(TARGET_ARCH_ABI), x86_64)
	LOCAL_C_INCLUDES := $(LOCAL_PATH)/libpcap-latest
	include $(BUILD_SHARED_LIBRARY)
	include $(LOCAL_PATH)/libpcap-latest/Android.mk
endif

ifeq ($(TARGET_ARCH_ABI), mips)
	LOCAL_C_INCLUDES := $(LOCAL_PATH)/libpcap
	include $(BUILD_SHARED_LIBRARY)
	include $(LOCAL_PATH)/libpcap/Android.mk
endif

ifeq ($(TARGET_ARCH_ABI), mips64)
	LOCAL_C_INCLUDES := $(LOCAL_PATH)/libpcap-latest
	include $(BUILD_SHARED_LIBRARY)
	include $(LOCAL_PATH)/libpcap-latest/Android.mk
endif

