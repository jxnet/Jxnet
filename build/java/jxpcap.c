
#include <pcap.h>
#include <jni.h>
#include "jni/com_ardikars_jxpcap_Jxpcap.h"
#include "util/jxpcap_exception.h"
#include "util/jxpcap_utils.h"
#include "util/jxpcap_ids.h"
#include "jxpcap_if.h"

JNIEXPORT jint JNICALL Java_com_ardikars_jxpcap_Jxpcap_nativeFindAllDevs
  (JNIEnv *env, jobject obj, jobject jalldevsp, jobject jerrbuf) {

	if(jalldevsp == NULL || jerrbuf == NULL) {
		if(ThrowNewException(env, NULL_PTR_EXCEPTION, "Jxpcap.findAllDevs(param can't be null.)") == 0) {
			return -1;
		}
	}
	
	char errbuf[PCAP_ERRBUF_SIZE]; errbuf[0] = '\0';
	int result; pcap_if_t *alldevsp;
	SetString(env, jerrbuf, NULL);
	result = pcap_findalldevs(&alldevsp, errbuf);
	if(result != 0) {
		SetString(env, jerrbuf, errbuf);
		return -1;
	}
	if(alldevsp != NULL) {
		jobject jxpcap_if;
		jxpcap_if = NewJxpcapIf(env, jalldevsp, jerrbuf, alldevsp);
		if(jxpcap_if == NULL) {
			return -1;
		}
		if((*env)->CallBooleanMethod(env, jalldevsp, ListAddMID, jxpcap_if) == JNI_FALSE) {
			(*env)->DeleteLocalRef(env, jxpcap_if);
			return -1;
		}
		(*env)->DeleteLocalRef(env, jxpcap_if);
	}
	pcap_freealldevs(alldevsp);
	return result;
}

JNIEXPORT jobject JNICALL Java_com_ardikars_jxpcap_Jxpcap_nativeOpenLive
  (JNIEnv *env, jclass cls, jstring jsource, jint jsnaplen, jint jpromisc, jint jto_ms, jobject jerrbuf) {
	char errbuf[PCAP_ERRBUF_SIZE]; errbuf[0] = '\0';
	pcap_t *pcap; const char *source;
	jobject obj;
	SetString(env, jerrbuf, NULL);
	source = (*env)->GetStringUTFChars(env, jsource, 0);
	pcap = pcap_open_live(source, jsnaplen, jpromisc, jto_ms, errbuf);
	if(pcap == NULL) {
		(*env)->ReleaseStringUTFChars(env, jsource, source);
		SetString(env, jerrbuf, errbuf);
		return NULL;
	}
	(*env)->ReleaseStringUTFChars(env, jsource, source);
	obj = (*env)->NewObject(env, cls, JxpcapInitMID);
	SetPcap(env, obj, pcap);
	return obj;	  
 }

JNIEXPORT jint JNICALL Java_com_ardikars_jxpcap_Jxpcap_nativeSendPacket
  (JNIEnv *env, jclass cls, jobject jxpcap, jobject jbuf, jint jsize) {
	if (jbuf == NULL) {
		if(ThrowNewException(env, NULL_PTR_EXCEPTION, "Buffer argument is null") == 0) {
			return -1;
		}
	}
	pcap_t *pcap; u_char *buf;
	pcap = GetPcap(env, jxpcap);
	if(pcap == NULL) {
		if(ThrowNewException(env, JXPCAP_EXCEPTION, "Jxpcap argument is null") == 0) {
			return -1;
		}
	}
	buf = (u_char *) (*env)->GetDirectBufferAddress(env, jbuf);
	if(buf == NULL) {
		if(ThrowNewException(env, ILLEGAL_ARGUMENT_EXCEPTION, "Unable to retrieve physical address from ByteBuffer") == 0) {
			return -1;
		}
	}
	return pcap_sendpacket(pcap, buf, (int) jsize);
}
 
 JNIEXPORT jstring JNICALL Java_com_ardikars_jxpcap_Jxpcap_nativeLookupDev
  (JNIEnv *env, jclass cls, jobject jerrbuf) {
	if(jerrbuf == NULL) {
		if(ThrowNewException(env, NULL_PTR_EXCEPTION, "Jxpcap.lookupDev(param can't be null.)") == 0) {
			return NULL;
		}
	}
	char errbuf[PCAP_ERRBUF_SIZE]; errbuf[0] = '\0';
	char *device; jstring jdevice;
	SetString(env, jerrbuf, NULL);
	device = pcap_lookupdev(errbuf);
	if (device == NULL) {
		SetString(env, jerrbuf, errbuf);
		return NULL;
	}
	#ifdef WIN32
	
	#else
	jdevice = (*env)->NewStringUTF(env, device);
	#endif
	return jdevice;
}

JNIEXPORT jstring JNICALL Java_com_ardikars_jxpcap_Jxpcap_nativeGetErr
  (JNIEnv *env, jclass csl, jobject jxpcap) {
	return (*env)->NewStringUTF(env, pcap_geterr(GetPcap(env, jxpcap)));
}