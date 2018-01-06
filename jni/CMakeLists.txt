
#
# Copyright (C) 2017  Ardika Rommy Sanjaya
#

cmake_minimum_required(VERSION 2.8 FATAL_ERROR)
set(CMAKE_LAGACY_CYGWIN_WIN32 0)

project(jni)

if ("${CMAKE_CXX_COMPILER_ID}" STREQUAL "GNU" OR
    "${CMAKE_CXX_COMPILER_ID}" STREQUAL "Clang")
    set(warnings "-Wall -Wextra -Werror")
elseif ("${CMAKE_CXX_COMPILER_ID}" STREQUAL "MSVC")
    set(warnings "/W4 /WX /EHsc")
endif()

if (NOT CONFIGURED_ONCE)
    set(CMAKE_CXX_FLAGS "${warnings}"
        CACHE STRING "Flags used by the compiler during all build 
types." FORCE)
    set(CMAKE_C_FLAGS   "${warnings}"
        CACHE STRING "Flags used by the compiler during all build 
types." FORCE)
endif()

set(SOURCE_FILES src/bpf.c src/jxnet.c src/preconditions.c src/utils.h src/ids.c src/mac_address.c src/preconditions.h src/ids.h src/utils.c)

find_package(JNI REQUIRED)

if (JNI_FOUND)
    message (STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")
    message (STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
    include_directories(${CMAKE_CURRENT_SOURCE_DIR}/include ${CMAKE_CURRENT_SOURCE_DIR}/include/jxnet ${JNI_INCLUDE_DIRS})
endif()

set(CMAKE_C_FLAGS "-Wno-unused-variable")

add_library(jxnet SHARED ${SOURCE_FILES})

IF (WIN32)
	message (STATUS "PLATFORM: WIN32")
	LINK_DIRECTORIES(${CMAKE_CURRENT_SOURCE_DIR}/lib/x64)
	find_library(WPCAP_LIB wpcap)
	find_library(IPHLPAPI_LIB iphlpapi)
	set(CMAKE_C_FLAGS "-Wl,--export-all-symbols -Wl,--add-stdcall-alias -liphlpapi")
	target_link_libraries(jxnet ${WPCAP_LIB} ${IPHLPAPI_LIB})
	set_target_properties(jxnet PROPERTIES PREFIX "")
ELSE()
	find_library(PCAP_LIB pcap)
	target_link_libraries(jxnet ${PCAP_LIB})
ENDIF()
