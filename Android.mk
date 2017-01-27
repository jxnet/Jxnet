
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := jxnet

LOCAL_SRC_FILES := \
	src/jxnet.c \
	src/ids.c \
	src/utils.c \
	src/addr_utils.c

LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/libdnet-stripped/src \
	$(LOCAL_PATH)/libdnet-stripped/include \
	$(LOCAL_PATH)/libpcap

LOCAL_STATIC_LIBRARIES := libdnet libpcap

include $(BUILD_SHARED_LIBRARY)

include $(LOCAL_PATH)/libdnet-stripped/Android.mk 

include $(LOCAL_PATH)/../libpcap/Android.mk 

