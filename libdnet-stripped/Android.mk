LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES:= \
	src/addr-util.c\
	src/addr.c\
	src/blob.c\
	src/ip-util.c\
	src/ip6.c\
	src/rand.c\
	src/memcmp.c\
	src/arp-none.c\
	src/eth-linux.c\
	src/fw-none.c\
	src/intf.c\
	src/ip.c\
	src/route-linux.c\
	src/tun-linux.c

LOCAL_C_INCLUDES:= $(LOCAL_PATH)/src $(LOCAL_PATH)/include

LOCAL_CFLAGS:= -DHAVE_CONFIG_H
LOCAL_CFLAGS+= -static -ffunction-sections -fdata-sections

LOCAL_MODULE:= libdnet

include $(BUILD_STATIC_LIBRARY)

