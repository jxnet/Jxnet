#include <jni.h>
#include <pcap.h>
#include "jxpcap_exception.h"

jmethodID GetJavaMethodID(JNIEnv *env, jobject obj, const char *field_name, const char *sig) {
	if(obj == NULL) {
		if(ThrowNewException(env, NULL_PTR_EXCEPTION, "Jxpcap Error: GetJavaMethodID") == 0) {
			return NULL;
		}
	}
	jclass class; jmethodID MID;
	class = (*env)->GetObjectClass(env, obj);
	if(class == NULL) {
		if(ThrowNewException(env, CLASS_NOT_FOUND_EXCEPTION, "Jxpcap Error: GetJavaMethodID") == 0) {
			return NULL;
		}
	}
	MID = (*env)->GetMethodID(env, class, field_name, sig);
	(*env)->DeleteLocalRef(env, class);
	if(MID == NULL) {
		if(ThrowNewException(env, NO_SUCH_FIELD_EXCEPTION, "Jxpcap Error: GetJavaMethodID") == 0){
			return NULL;
		}
	}
	return MID;
}


jobject NewJavaObject(JNIEnv *env, jclass class, const char *field_name, const char *sig) {
	jmethodID MID;
	MID = (*env)->GetMethodID(env, class, field_name, sig);
	if(MID == NULL) {
		if(ThrowNewException(env, NO_SUCH_FIELD_EXCEPTION, NULL) == 0) {
			return NULL;
		}
	}
	jobject new_obj = (*env)->NewObject(env, class, MID);
	(*env)->DeleteLocalRef(env, class);
	return new_obj;
}