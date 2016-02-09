#include <pcap.h>
#include <jni.h>
#include <unistd.h>
#include "util.h"

jint toJint(void *ptr) {
#ifndef WIN32
    jint ip = (intptr_t) ptr;
#else
    jint ip = (UINT_PTR) ptr;
#endif
    return ip;
}

jlong toJlong(void *ptr) {
#ifndef WIN32
    jlong lp = (intptr_t) ptr;
#else
    jlong lp = (UINT_PTR) ptr;
#endif
    return lp;
}

void *toPcap(jlong lp) {
#ifndef WIN32
    void *ptr = (void *) ((intptr_t) lp);
#else
    void *ptr = (void *) ((UINT_PTR) lp);
#endif
    return ptr;
}

void *toPacket(jint ip) {
#ifndef WIN32
    void *ptr = (void *) ((intptr_t) ip);
#else
    void *ptr = (void *) ((UINT_PTR) ip);
#endif
    return ptr;
}

pcap_t *getPcap(jlong lp) {
    return ((pcap_t *) toPcap(lp));
}

u_char *getPacket(jint ip) {
	return ((u_char *) toPacket(ip));
}

pcap_dumper_t *getPcapDumper(jlong lp) {
	return ((pcap_dumper_t *) toPcap(lp));
}

void setPcap(JNIEnv *env, jobject jobj, jfieldID FID, pcap_t *pcap) {
	(*env)->SetLongField(env, jobj, FID, toJlong(pcap));
}

void setPcapDumper(JNIEnv *env, jobject jobj, jfieldID FID, pcap_dumper_t *pcap_dumper) {
	(*env)->SetLongField(env, jobj, FID, toJlong(pcap_dumper));
}

void setMsg(JNIEnv *env, jobject jobj, const char *message) {
    jclass Message = (*env)->FindClass(env, "com/jxpcap/util/Message");
    jmethodID set_message_MID = (*env)->GetMethodID(env, Message, "setMessage", "(Ljava/lang/String;)V");
    if(message == NULL) {
		message = "";
	}
    jstring jstr = (*env)->NewStringUTF(env, message);
    (*env)->CallVoidMethod(env, jobj, set_message_MID, jstr);
    return;
}
