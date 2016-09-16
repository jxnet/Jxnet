
AC_PREREQ(2.69)

AC_INIT([jxnet], [0.0.1], [contact@ardikars.com])
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
*)
	CFLAGS="$CFLAGS -I$JAVA_HOME/include -I$JAVA_HOME/include/win32"
	;;
esac
else
case "$host_os" in
*linux*)
	#AC_MSG_CHECKING([[linux]])
	AX_JNI_INCLUDE_DIR
	for JNI_INCLUDE_DIR in $JNI_INCLUDE_DIRS
	do
		CFLAGS="$CFLAGS -I$JNI_INCLUDE_DIR"
	done
	;;
*)
	AC_MSG_WARN([JAVA_HOME is not found, please set JAVA_HOME environment variable manualy])
	;;
esac
fi

# Checks for libraries
AC_CHECK_LIB([pcap], [pcap_lib_version], [LDFLAGS+=-lpcap], 
	[CFLAGS="$CFLAGS -L../libpcap" LDFLAGS+=-lpcap]
)

AC_CHECK_LIB([wpcap], [wpcap_lib_version], [LDFLAGS+=-lwpcap], [])
AC_CHECK_LIB([iphlpapi], [main], [LDFLAGS+=-liphlpapi], [])
AC_CONFIG_FILES([Makefile src/Makefile])

# Checks for headers
AC_CHECK_HEADERS([arpa/inet.h fcntl.h netinet/in.h stdint.h stdlib.h string.h sys/ioctl.h sys/socket.h sys/time.h unistd.h winsock2.h iphlpapi.h net/if.h stdio.h Ws2tcpip.h])
AC_CHECK_HEADERS([pcap.h], [AC_DEFINE([HAVE_PCAP_H], [1], [Define to 1 if you have <pcap.h>.])], 
	[CFLAGS="$CFLAGS -I../libpcap"]
)

# Checks for library functions.
# AC_CHECK_FUNCS([inet_ntoa memset socket strchr pcap_open_live pcap_open_dead pcap_open_offline pcap_dump_open pcap_setnonblock pcap_getnonblock pcap_freealldevs pcap_lookupdev pcap_lookupnet pcap_dispatch pcap_loop pcap_next pcap_next_ex pcap_sendpacket pcap_dump pcap_compile pcap_setfilter pcap_freecode pcap_datalink pcap_list_datalinks pcap_set_datalink pcap_datalink_name_to_val pcap_datalink_val_to_name pcap_datalink_val_to_description pcap_snapshot pcap_is_swapped pcap_major_version pcap_minor_version pcap_file pcap_stats pcap_perror pcap_geterr pcap_strerror pcap_lib_version pcap_close pcap_dump_file pcap_dump_flush pcap_dump_close socket close])

# Checks for typedefs, structures, and compiler characteristics.
AC_CHECK_HEADER_STDBOOL
AC_TYPE_INT16_T
AC_TYPE_INT32_T
AC_TYPE_INT64_T
AC_TYPE_INT8_T
AC_TYPE_SIZE_T

AC_OUTPUT
