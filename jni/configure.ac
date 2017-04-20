
AC_PREREQ(2.69)
LT_PREREQ(2.4.6)

AC_INIT([jxnet], [1.1.0], [contact@ardikars.com], [jxnet], [http://ardikars.com])
AM_INIT_AUTOMAKE([])
LT_INIT([shared static])
AC_PROG_LIBTOOL(libtool)
AC_PROG_RANLIB
AC_PROG_CC
AC_CONFIG_MACRO_DIR([m4])
m4_include([m4/ax_jni_include_dir.m4])
AC_CANONICAL_HOST

CFLAGS="$CFLAGS -Wall "

AC_MSG_CHECKING([checking JAVA_HOME environment variable])
if test "x$JAVA_HOME" != x; then
	AC_MSG_RESULT([JAVA_HOME is found in $JAVA_HOME])
	AS_CASE([$host_os],
		[linux*], [
			CFLAGS="$CFLAGS -I$JAVA_HOME/include -I$JAVA_HOME/include/linux "
		],
		[cygwin*|mingw*|msys*], [
			CFLAGS="$CFLAGS -I$JAVA_HOME/include -I$JAVA_HOME/include/win32 "
		],
		[*], [
			AC_MSG_ERROR(["Platform is not supported."])
		]
	)
else
	AX_JNI_INCLUDE_DIR
	for JNI_INCLUDE_DIR in $JNI_INCLUDE_DIRS
	do
		CFLAGS="$CFLAGS -I$JNI_INCLUDE_DIR"
	done
fi

AS_CASE([$host_os],
	[cygwin*|mingw*|msys*], [
		CFLAGS="$CFLAGS -I../include "
		LDFLAGS+="--disable-rpath "
		AS_CASE([$host_cpu],
			[amd64|x86_64], [
				LDFLAGS+="-L../lib/x64 "
			],
			[i?86|x86], [
				LDFLAGS+="-L../lib "
			],
			[*], [
				AC_MSG_ERROR(["Unknown architecture."])
			]
		)
		LDFLAGS+="-lwpcap -liphlpapi "
	],
	[*], [
		AC_CHECK_LIB([pcap], [pcap_lib_version], [LDFLAGS+="-lpcap "], [
			AC_MSG_ERROR(["Cannot find -lpcap."])
		])
		AC_CHECK_HEADERS([pcap.h], [AC_DEFINE([HAVE_PCAP_H], [1], [Define to 1 if you have <pcap.h>.])], [
			AC_MSG_ERROR(["Cannot find find pcap.h"])
		])
	]
)

AC_CONFIG_FILES([Makefile src/Makefile])

# Checks for typedefs, structures, and compiler characteristics.
#AC_CHECK_HEADER_STDBOOL
#AC_TYPE_INT16_T
#AC_TYPE_INT32_T
#AC_TYPE_INT64_T
#AC_TYPE_INT8_T
#AC_TYPE_SIZE_T

AC_OUTPUT

