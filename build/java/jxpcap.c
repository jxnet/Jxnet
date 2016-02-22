
#include <pcap.h>
#include <jni.h>
#include "jni/com_ardikars_jxpcap_Jxpcap.h"
#include "util/jxpcap_exception.h"
#include "util/jxpcap_utils.h"
#include "jxpcap_if.h"

JNIEXPORT jint JNICALL Java_com_ardikars_jxpcap_Jxpcap_findAllDevs
  (JNIEnv *env, jobject obj, jobject jalldevsp, jstring jerrbuf) {

	if(jalldevsp == NULL || jerrbuf == NULL) {
		if(ThrowNewException(env, NULL_PTR_EXCEPTION, "Jxpcap.findAllDevs(param can't be null.)") == 0) {
			return -1;
		}
	}
	
	char errbuf[PCAP_ERRBUF_SIZE]; errbuf[0] = '\0';
	int result; pcap_if_t *alldevsp;
	
	result = pcap_findalldevs(&alldevsp, errbuf);
	if(result != 0) {
		return -1;
	}
	if(alldevsp != NULL) {
		jmethodID add_MID; jobject jxpcap_if;
		add_MID = GetJavaMethodID(env, jalldevsp, "add", "(Ljava/lang/Object;)Z");
		jxpcap_if = NewJxpcapIf(env, jalldevsp, add_MID, jerrbuf, alldevsp);
		if(jxpcap_if == NULL) {
			return -1;
		}
		if((*env)->CallBooleanMethod(env, jalldevsp, add_MID, jxpcap_if) == JNI_FALSE) {
			(*env)->DeleteLocalRef(env, jxpcap_if);
			return -1;
		}
		(*env)->DeleteLocalRef(env, jxpcap_if);
	}
	pcap_freealldevs(alldevsp);
	return result;
}