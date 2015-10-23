#include "jni/com_jxpcap_Writer.h"
#include <pcap.h>
#include "util/util.h"

JNIEXPORT jstring JNICALL Java_com_jxpcap_Writer_nativeOpenDumpFile
  (JNIEnv *env, jobject jobj, jlong jxpcap, jstring jfilename) {
	  jclass Writer = (*env)->FindClass(env, "com/jxpcap/Writer");
	  jfieldID jxpcap_dumper_FID = (*env)->GetFieldID(env, Writer, "jxpcap_dumper", "J");
	  const char *filename = (*env)->GetStringUTFChars(env, jfilename, 0);
	  pcap_dumper_t *pcap_dumper = pcap_dump_open(getPcap(jxpcap), filename);
	  (*env)->ReleaseStringUTFChars(env, jfilename, filename);
	  if(pcap_dumper == NULL) {
		  return (*env)->NewStringUTF(env, pcap_geterr(getPcap(jxpcap)));
	  }
	  setPcapDumper(env, jobj, jxpcap_dumper_FID, pcap_dumper);
	  return (*env)->NewStringUTF(env, "OK.");
  }
JNIEXPORT jstring JNICALL Java_com_jxpcap_Writer_nativeWritePacket
  (JNIEnv *env, jobject jobj, jobject jpacket) {
	  jclass Packet = (*env)->FindClass(env, "com/jxpcap/packet/Packet");
	  jclass Writer = (*env)->FindClass(env, "com/jxpcap/Writer");
	  struct pcap_pkthdr *pkt_hdr;
	  u_char *pkt_dta;
	  //pkt_hdr->ts.tv_sec = (jlong) (*env)->GetLongField(env, jobj, (*env)->GetFieldID(env, Packet, "time_in_seconds", "J"));
	  //pkt_hdr->ts.tv_usec = (jlong) (*env)->GetLongField(env, jobj, (*env)->GetFieldID(env, Packet, "time_in_microseconds", "J"));
	  //pkt_hdr->caplen = (*env)->GetIntField(env, jobj, (*env)->GetFieldID(env, Packet, "captured_length", "I"));
	  //pkt_hdr->len = (*env)->GetIntField(env, jobj, (*env)->GetFieldID(env, Packet, "length", "I"));
	  //pkt_dta = (*env)->GetDirectBufferAddress(env, (*env)->GetObjectField(env, jobj, (*env)->GetFieldID(env, Packet, "packet_data" ,"Ljava/nio/ByteBuffer;")));
	  jlong jxpcap_dumper = (*env)->GetLongField(env, jobj, (*env)->GetFieldID(env, Writer, "jxpcap_dumper", "J"));
	  if(pkt_dta == NULL) {
		  return (*env)->NewStringUTF(env, "Error.");
	  }
	  pcap_dump((u_char *) getPcapDumper(jxpcap_dumper), pkt_hdr, pkt_dta);
	  return (*env)->NewStringUTF(env, "OK.");
  }
