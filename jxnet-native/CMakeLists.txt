
 ##
 # Copyright (C) 2015-2019 Jxnet
 # Author: Ardika Rommy Sanjaya <contact@ardikars.com>
 ##

cmake_minimum_required(VERSION 2.8 FATAL_ERROR)

project(jxnet)

set(CMAKE_C_STANDARD 99)

# Remove warnings for cygwin
set(CMAKE_LEGACY_CYGWIN_WIN32 0) 

message (STATUS "Start configuring project...")

if ("${CMAKE_C_COMPILER_ID}" STREQUAL "GNU" OR
    "${CMAKE_C_COMPILER_ID}" STREQUAL "Clang")
    message (STATUS "C COMPILER ID = GNU OR Clang")
    set(warnings "-Wall -Wextra -Werror")
elseif ("${CMAKE_C_COMPILER_ID}" STREQUAL "MSVC")
    set(warnings "/W4 /WX /EHsc")
	message (STATUS "C COMPILER ID = MSVC")
endif()

if (NOT CONFIGURED_ONCE)
    set(CMAKE_CXX_FLAGS "${warnings}"
        CACHE STRING "Flags used by the compiler during all build types." FORCE)
    set(CMAKE_C_FLAGS   "${warnings}"
        CACHE STRING "Flags used by the compiler during all build types." FORCE)
endif()

# Set source files for jxnet
set(SOURCE_FILES src/bpf.c src/jxnet.c src/preconditions.c src/utils.h src/ids.c src/preconditions.h src/ids.h src/utils.c)

# Set source file for test
set(SOURCE_APPLICATION_FILES src/main.c)

# Set library path
set(CMAKE_FIND_LIBRARY_PREFIXES "lib" "")
if (WIN32 OR MSYS OR MSVC OR MINGW)
	set(CMAKE_FIND_LIBRARY_SUFFIXES ".lib" ".a" ".dll")
	set(CMAKE_LIBRARY_PATH "${CMAKE_CURRENT_SOURCE_DIR}/WpdPack/Lib/x64" "${CMAKE_LIBRARY_PATH}")
elseif (APPLE)
	set(CMAKE_MACOSX_RPATH 1)
	set(CMAKE_FIND_LIBRARY_SUFFIXES ".la" ".a" ".so" ".dylib")
	set(CMAKE_LIBRARY_PATH "${CMAKE_CURRENT_SOURCE_DIR}/libpcap" "${CMAKE_LIBRARY_PATH}")
else ()
	set(CMAKE_FIND_LIBRARY_SUFFIXES ".la" ".a" ".so")
	set(CMAKE_LIBRARY_PATH "${CMAKE_CURRENT_SOURCE_DIR}/libpcap" "${CMAKE_LIBRARY_PATH}")
endif()

# Set JNI include dirs
# We are only interested in finding jni.h: we do not care about extended JVM
# functionality or the AWT library.
set(JAVA_AWT_LIBRARY "include")
set(JAVA_AWT_INCLUDE_PATH "include")
set(JAVA_JVM_LIBRARY "include")
find_package(JNI REQUIRED)
if (NOT JNI_FOUND)
	if (WIN32 OR MSYS)
		set(JNI_INCLUDE_DIRS "${CMAKE_CURRENT_SOURCE_DIR}/include" "${CMAKE_CURRENT_SOURCE_DIR}/include/win32")
	elseif (LINUX)
		set(JNI_INCLUDE_DIRS "${CMAKE_CURRENT_SOURCE_DIR}/include" "${CMAKE_CURRENT_SOURCE_DIR}/include/linux")
	else ()
		set(JNI_INCLUDE_DIRS "${CMAKE_CURRENT_SOURCE_DIR}/include" "${CMAKE_CURRENT_SOURCE_DIR}/include/darwin")
	endif()
	message (STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")
	message (STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
endif()

# Set include path and link directory
if (WIN32 OR MSYS OR MSVC OR MINGW)
	add_definitions(-DWIN32 -DWIN64 -D__MINGW32__ -D__MINGW64__ -D_WIN32 -D_WIN64)
	include_directories(${CMAKE_CURRENT_SOURCE_DIR}/WpdPack/Include ${JNI_INCLUDE_DIRS})
	LINK_DIRECTORIES(${CMAKE_CURRENT_SOURCE_DIR}/WpdPack/Lib/x64)
else ()
	include_directories(${CMAKE_CURRENT_SOURCE_DIR}/libpcap ${JNI_INCLUDE_DIRS})
	LINK_DIRECTORIES(${CMAKE_CURRENT_SOURCE_DIR}/libpcap)
endif()

# Set c flags
if (WIN32 OR MSYS OR MSVC OR MINGW)
	set(CMAKE_C_FLAGS "-Wno-unused-variable ${CMAKE_C_FLAGS}")
	set(CMAKE_C_FLAGS "-Wl,--export-all-symbols -Wl,--add-stdcall-alias -liphlpapi ${CMAKE_C_FLAGS}")
else ()
	set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS}")
endif()

message (STATUS "Start building project...")
message (STATUS "SOURCE_FILES                = ${SOURCE_FILES}")
message (STATUS "CURRENT_SOURCE_DIR          = ${CMAKE_CURRENT_SOURCE_DIR}")
message (STATUS "JNI_INCLUDE_DIRS            = ${JNI_INCLUDE_DIRS}")
message (STATUS "CMAKE_FIND_LIBRARY_PREFIXES = ${CMAKE_FIND_LIBRARY_PREFIXES}")
message (STATUS "CMAKE_FIND_LIBRARY_SUFFIXES = ${CMAKE_FIND_LIBRARY_SUFFIXES}")
message (STATUS "CMAKE_C_FLAGS               = ${CMAKE_C_FLAGS}")
message (STATUS "CMAKE_LIBRARY_PATH          = ${CMAKE_LIBRARY_PATH}")
message (STATUS "CMAKE_INCLUDE_PATH          = ${CMAKE_INCLUDE_PATH}")

add_library(jxnet SHARED ${SOURCE_FILES})
add_executable(application ${SOURCE_APPLICATION_FILES})

if (WIN32 OR MSYS OR MSVC OR MINGW)
	find_library(WPCAP_LIB wpcap)
	find_library(IPHLPAPI_LIB iphlpapi)
	target_link_libraries(jxnet ${WPCAP_LIB} ${IPHLPAPI_LIB})
	set_target_properties(jxnet PROPERTIES PREFIX "")
	target_link_libraries(application ${WPCAP_LIB})
else()
	find_library(PCAP_LIB pcap)
	target_link_libraries(jxnet ${PCAP_LIB})
	set_target_properties(jxnet PROPERTIES PREFIX "lib")
	target_link_libraries(application ${PCAP_LIB})
endif()

# add the install targets
install (TARGETS jxnet DESTINATION lib)
