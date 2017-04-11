
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

LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/libpcap

LOCAL_STATIC_LIBRARIES := libpcap

include $(BUILD_SHARED_LIBRARY)

ifeq ($(TARGET_ARCH_ABI), arm64-v8a)	
	include $(LOCAL_PATH)/libpcap/Android.mk 
else
	include $(LOCAL_PATH)/libpcap/Android.mk 
endif

