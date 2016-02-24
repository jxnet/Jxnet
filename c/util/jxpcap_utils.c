#include <jni.h>
#include <pcap.h>
#include <unistd.h>
#include "jxpcap_ids.h"
#include "jxpcap_exception.h"

jlong TOJLONG(void *ptr) {
#ifndef WIN32
    jlong lp = (intptr_t) ptr;
#else
    jlong lp = (UINT_PTR) ptr;
#endif
    return lp;
}

void *TOPTR(jlong lp) {
#ifndef WIN32
    void *ptr = (void *) ((intptr_t) lp);
#else
    void *ptr = (void *) ((UINT_PTR) lp);
#endif
    return ptr;
}

void SetPcap(JNIEnv *env, jobject obj, pcap_t *pcap) {
	(*env)->SetLongField(env, obj, JxpcapPcapFID, TOJLONG(pcap));
}

pcap_t *GetPcap(JNIEnv *env, jobject obj) {
	jlong lp = (*env)->GetLongField(env, obj, JxpcapPcapFID);
	if(lp == 0) {
		if(ThrowNewException(env, JXPCAP_EXCEPTION, "Pcap is null (closed).") == 0) {
			return NULL;
		}
	}
	pcap_t *pcap = (pcap_t *) TOPTR(lp);
	return pcap;
}

void SetString(JNIEnv *env, jobject buf, const char *str) {
	if (str == NULL) {
		str = "";
	}
	jstring jstr = (*env)->NewStringUTF(env, str);
	(*env)->CallVoidMethod(env, buf, StringBuilderSetLengthMID, 0);
	(*env)->CallObjectMethod(env, buf, StringBuilderAppendMID, jstr);
}


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