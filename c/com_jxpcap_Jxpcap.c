#include "jni/com_jxpcap_Jxpcap.h"
#include <pcap.h>
#include "util/util.h"

JNIEXPORT jobject JNICALL Java_com_jxpcap_Jxpcap_nativeOpenLive
	(JNIEnv *env, jclass jcls, jstring jdevice_name, jint jsnaplen, jint jpromisc, jint jtimeout, jobject jerrmsg) {
	char errmsg[256]; errmsg[0] = '\0';
	const char *device_name = (*env)->GetStringUTFChars(env, jdevice_name, 0);
	pcap_t *pcap = pcap_open_live(device_name, jsnaplen, jpromisc, jtimeout, errmsg);
	setMsg(env, jerrmsg, errmsg);
	(*env)->ReleaseStringUTFChars(env, jdevice_name, device_name);
	if(pcap == NULL) {
		return NULL;
	}
	jmethodID JxpcapInit = (*env)->GetMethodID(env, jcls, "<init>", "()V");
	jobject jobj = (*env)->NewObject(env, jcls, JxpcapInit);
	jfieldID jxpcap_FID = (*env)->GetFieldID(env, jcls, "jxpcap", "J");
	setPcap(env, jobj, jxpcap_FID, pcap);
	return jobj;
}
