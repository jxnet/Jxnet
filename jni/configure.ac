
AC_PREREQ(2.69)

AC_INIT([jxnet], [1.1.0], [contact@ardikars.com])
AM_INIT_AUTOMAKE
LT_INIT()
AC_PROG_LIBTOOL(libtool)
AC_PROG_RANLIB
AC_PROG_CC
AC_CONFIG_MACRO_DIR([m4])
m4_include([m4/ax_jni_include_dir.m4])
AC_CANONICAL_HOST

CFLAGS="$CFLAGS -Wall"

AC_MSG_CHECKING([checking JAVA_HOME environment variable])
if test "x$JAVA_HOME" != x; then
	AC_MSG_RESULT([JAVA_HOME is found in $JAVA_HOME])
	case "$host_os" in
		*linux*)
			CFLAGS="$CFLAGS -I$JAVA_HOME/include -I$JAVA_HOME/include/linux"
			;;
		*freebsd*)
			CFLAGS="$CFLAGS -I$JAVA_HOME/include -I$JVA_HOME/include/freebsd"
			;;
		cygwin*|mingw*)
			CFLAGS="$CFLAGS -I$JAVA_HOME/include -I$JAVA_HOME/include/win32"
			;;
		*)
			AC_MSG_ERROR(["Platform is not supported."])
			;;
	esac
else
	AX_JNI_INCLUDE_DIR
	for JNI_INCLUDE_DIR in $JNI_INCLUDE_DIRS
	do
		CFLAGS="$CFLAGS -I$JNI_INCLUDE_DIR"
	done
fi

case "$host_os" in
	cygwin*|mingw*)
		AC_CHECK_LIB([wpcap], [wpcap_lib_version], [LDFLAGS+="-lwpcap "], [
			AC_MSG_ERROR(["Cannot find -lwpcap."])
		])
		AC_CHECK_LIB([iphlpapi], [main], [LDFLAGS+="-liphlpapi "], [
			AC_MSG_ERROR(["Cannot find -liphlpapi."])
		])
		AC_CHECK_LIB([dnet], [main], [LDFLAGS+="-ldnet" ], [
			AC_MSG_ERROR(["Cannot find -ldnet."])
		])
		AC_CHECK_HEADERS([winsock2.h iphlpapi.h Ws2tcpip.h])
	;;
	*)
		AC_CHECK_LIB([pcap], [pcap_lib_version], [LDFLAGS+="-lpcap "], [
			AC_MSG_ERROR(["Cannot find -lpcap."])
		])
		AC_CHECK_LIB([dnet], [main], [LDFLAGS+=-ldnet ], [
			AC_MSG_ERROR(["Cannot find -ldnet."])
		])
	;;
esac

AC_CONFIG_FILES([Makefile src/Makefile])

# Checks for headers
AC_CHECK_HEADERS([pcap.h], [AC_DEFINE([HAVE_PCAP_H], [1], [Define to 1 if you have <pcap.h>.])], [
	AC_MSG_ERROR(["Cannot find find pcap.h"])
])
AC_CHECK_HEADERS([dnet.h], [AC_DEFINE([HAVE_DNET_H], [1], [Define to 1 if you have <dnet.h>.])], [
	AC_MSG_ERROR(["Cannot find find dnet.h."])
])

# Checks for library functions.
# AC_CHECK_FUNCS([inet_ntoa memset socket strchr pcap_open_live pcap_open_dead pcap_open_offline pcap_dump_open pcap_setnonblock pcap_getnonblock pcap_freealldevs pcap_lookupdev pcap_lookupnet pcap_dispatch pcap_loop pcap_next pcap_next_ex pcap_sendpacket pcap_dump pcap_compile pcap_setfilter pcap_freecode pcap_datalink pcap_list_datalinks pcap_set_datalink pcap_datalink_name_to_val pcap_datalink_val_to_name pcap_datalink_val_to_description pcap_snapshot pcap_is_swapped pcap_major_version pcap_minor_version pcap_file pcap_stats pcap_perror pcap_geterr pcap_strerror pcap_lib_version pcap_close pcap_dump_file pcap_dump_flush pcap_dump_close socket close])

# Checks for typedefs, structures, and compiler characteristics.
#AC_CHECK_HEADER_STDBOOL
#AC_TYPE_INT16_T
#AC_TYPE_INT32_T
#AC_TYPE_INT64_T
#AC_TYPE_INT8_T
#AC_TYPE_SIZE_T

AC_OUTPUT
