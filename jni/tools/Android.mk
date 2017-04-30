
LOCAL_PATH := $(call my-dir)

define libcap_progs
	include $(CLEAR_VARS)
	LOCAL_CFLAGS := -Wall
	LOCAL_C_INCLUDES := $(LOCAL_PATH)/../libcap/include $(LOCAL_PATH)/../libcap/include/uapi
	LOCAL_MODULE_TAGS := optional eng
	LOCAL_SHARED_LIBRARIES := libcap
	LOCAL_MODULE := $1
	LOCAL_SRC_FILES := src/$1.c
	include $(BUILD_EXECUTABLE)
	include $(LOCAL_PATH)/../libcap/Android.mk
endef

progs := capsh getcap getpcaps setcap

$(foreach item, $(progs), $(eval $(call libcap_progs, $(progs))))
