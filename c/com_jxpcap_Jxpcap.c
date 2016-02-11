#include "jni/com_jxpcap_Jxpcap.h"
#include <pcap.h>
#include "util/util.h"
#include <stdlib.h>
#include <stdio.h>

JNIEXPORT jobject JNICALL Java_com_jxpcap_Jxpcap_nativeOpenLive
	(JNIEnv *env, jclass jcls, jstring jdevice_name, jint jsnaplen, jint jpromisc, jint jtimeout, jobject jerrmsg) {
	char errmsg[256];
	const char *device_name;
	pcap_t *pcap;
	jmethodID JxpcapInit;
	jobject jobj;
	jfieldID jxpcap_FID;
	errmsg[0] = '\0';
	device_name = (*env)->GetStringUTFChars(env, jdevice_name, 0);
	pcap = pcap_open_live(device_name, jsnaplen, jpromisc, jtimeout, errmsg);
	setMsg(env, jerrmsg, errmsg);
	(*env)->ReleaseStringUTFChars(env, jdevice_name, device_name);
	if(pcap == NULL) {
		return NULL;
	}
	JxpcapInit = (*env)->GetMethodID(env, jcls, "<init>", "()V");
	jobj = (*env)->NewObject(env, jcls, JxpcapInit);
	jxpcap_FID = (*env)->GetFieldID(env, jcls, "jxpcap", "J");
	setPcap(env, jobj, jxpcap_FID, pcap);
	return jobj;
}

JNIEXPORT void JNICALL Java_com_jxpcap_Jxpcap_nativeClose
  (JNIEnv *env, jclass jcls, jlong jxpcap) {
	pcap_t *pcap = getPcap(jxpcap);
	pcap_close(pcap);
}


/*JNIEXPORT jobject JNICALL Java_com_jxpcap_Jxpcap_nativeCreate
  (JNIEnv *env, jclass jcls, jstring jdevice_name, jobject jerrmsg) {
	char errmsg[256];
	const char *device_name;
	pcap_t *pcap;
	jmethodID JxpcapInit;
	jobject jobj;
	jfieldID jxpcap_FID;
	errmsg[0] = '\0';
	device_name = (*env)->GetStringUTFChars(env, jdevice_name, 0);
	pcap = pcap_create(device_name, errmsg);
	setMsg(env, jerrmsg, errmsg);
	(*env)->ReleaseStringUTFChars(env, jdevice_name, device_name);
	if(pcap == NULL) {
		return NULL;
	}
	JxpcapInit = (*env)->GetMethodID(env, jcls, "<init>", "()V");
	jobj = (*env)->NewObject(env, jcls, JxpcapInit);
	jxpcap_FID = (*env)->GetFieldID(env, jcls, "jxpcap", "J");
	setPcap(env, jobj, jxpcap_FID, pcap);
	return jobj;
}


JNIEXPORT jint JNICALL Java_com_jxpcap_Jxpcap_nativeActive
  (JNIEnv *env, jclass jcls, jlong jxpcap) {
	pcap_t *pcap;
	pcap = getPcap(jxpcap);
	return (jint) pcap_active(pcap);
}*/
