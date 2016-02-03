#include "jni/com_jxpcap_Captor.h"
#include <pcap.h>
#include "util/util.h"

JNIEXPORT jstring JNICALL Java_com_jxpcap_Captor_nativeGetPacket
  (JNIEnv *env, jobject job, jlong jxpcap, jobject jpacket) {
	/* char errmsg[256]; */
    pcap_t *pcap;
    struct pcap_pkthdr *pkt_hdr;
    const u_char *pkt_dta;
    int result;
    /*int linktypes = pcap_datalink(pcap); */
    jclass Packet;
    jmethodID set_packet_MID;
    pcap = getPcap(jxpcap);
    result = pcap_next_ex(pcap, &pkt_hdr, (const u_char **) &pkt_dta);
    switch(result) {
        case 0:
            return (*env)->NewStringUTF(env, "Timeout.");
            break;
        case -1:
            return (*env)->NewStringUTF(env, "Error.");
            break;
        case -2:
            return (*env)->NewStringUTF(env, "EOF.");
    }
    if(pkt_dta == NULL) {
        return (*env)->NewStringUTF(env, "No packet captured.");
    }
    Packet = (*env)->FindClass(env, "com/jxpcap/packet/Packet");
    set_packet_MID = (*env)->GetMethodID(env, Packet, "setPacket", "(JJIILjava/nio/ByteBuffer;)V");
    (*env)->CallVoidMethod(env, jpacket, set_packet_MID,
		(jlong) pkt_hdr->ts.tv_sec, (jlong) pkt_hdr->ts.tv_usec, (jint) pkt_hdr->caplen, (jint) pkt_hdr->len,
		(*env)->NewDirectByteBuffer(env, (void *) pkt_dta, pkt_hdr->caplen));
	return (*env)->NewStringUTF(env, "OK.");
}
