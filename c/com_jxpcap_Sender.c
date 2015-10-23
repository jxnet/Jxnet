#include "jni/com_jxpcap_Sender.h"
#include <pcap.h>
#include "util/util.h"

JNIEXPORT jstring JNICALL Java_com_jxpcap_Sender_nativeSendPacket
	(JNIEnv *env, jobject jobj, jlong jxpcap, jobject jpacket, jint jlength) {
	pcap_t *pcap = getPcap(jxpcap);
	u_char *packet = (*env)->GetDirectBufferAddress(env, jpacket);
	if(pcap_sendpacket(pcap, packet + 0, (int) jlength) == 0) {
		return (*env)->NewStringUTF(env, "OK.");
	}
	return (*env)->NewStringUTF(env, "Can't sending packet.");
}
