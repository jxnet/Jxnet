#include "jxpcap_exception.h"
#include <jni.h>

jint ThrowNewException(JNIEnv *env, const char *class_name, const char *message) {
	jclass exception = (*env)->FindClass(env, class_name);
	return (*env)->ThrowNew(env, exception, message);
}