#include <jni.h>

#define CLASS_NOT_FOUND_EXCEPTION "java/lang/ClassNotFoundException"
#define NO_SUCH_METHOD_EXCEPTION "java.lang.NoSuchMethodException"
#define NO_SUCH_FIELD_EXCEPTION "java/lang/NoSuchFieldException"
#define INDEX_OUT_OF_BOUNDS_EXCEPTION "java/lang/IndexOutOfBoundsException"
#define NULL_PTR_EXCEPTION "java/lang/NullPointerException"
#define UNSUPPORTED_OPERATION_EXCEPTION "java/lang/UnsupportedOperationException"
#define OUT_OF_MEMORY_ERROR "java/lang/OutOfMemoryError"
#define BUFFER_OVERFLOW_EXCEPTION "java/nio/BufferOverflowException"
#define BUFFER_UNDERFLOW_EXCEPTION "java/nio/BufferUnderflowException"
#define READ_ONLY_BUFFER_EXCETPION "java/nio/ReadOnlyBufferException"
#define IO_EXCEPTION "java/io/IOException"

#define JXPCAP_EXCEPTION "com/ardikars/jxpcap/util/JxpcapException"

jint ThrowNewException(JNIEnv *env, const char *class_name, const char *message);