LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES:= \
	src/addr.c \
	src/addr-util.c \
	src/arp-ioctl.c \
	src/arp-none.c \
	src/blob.c \
	src/err.c \
	src/eth-linux.c \
	src/eth-none.c \
	src/fw-none.c \
	src/intf.c \
	src/ip6.c \
	src/ip.c \
	src/ip-cooked.c \
	src/ip-util.c \
	src/memcmp.c \
	src/rand.c \
	src/route-linux.c \
	src/route-none.c \
	src/strlcat.c \
	src/strlcpy.c \
	src/strsep.c \
	src/tun-bsd.c \
	src/tun-linux.c \
	src/tun-none.c

#src/arp-bsd.c
#src/arp-win32.c
#src/eth-bsd.c
#src/eth-dlpi.c
#src/eth-ndd.c
#src/eth-pfilt.c
#src/eth-snoop.c
#src/eth-win32.c
#src/fw-ipchains.c
#src/fw-ipf.c
#src/fw-ipfw.c
#src/fw-pf.c
#src/fw-pktfilter.c
#src/intf-win32.c
#src/ip-win32.c
#src/route-bsd.c
#src/route-hpux.c
#src/route-win32.c
#src/tun-solaris.c

LOCAL_C_INCLUDES:= $(LOCAL_PATH)/src $(LOCAL_PATH)/include

LOCAL_CFLAGS:= -DHAVE_CONFIG_H
LOCAL_CFLAGS+= -static -ffunction-sections -fdata-sections

LOCAL_MODULE:= libdnet

include $(BUILD_STATIC_LIBRARY)

