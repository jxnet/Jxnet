
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

include $(LOCAL_PATH)/libpcap/Android.mk 



include $(CLEAR_VARS)

LOCAL_MODULE := setcap

LOCAL_SRC_FILES := \
	src/setcap.c
	
LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/libcap/include \
	$(LOCAL_PATH)/libcap/include/uapi
        
LOCAL_STATIC_LIBRARIES := libcap
	
include $(BUILD_EXECUTABLE)

include $(LOCAL_PATH)/libcap/Android.mk 
