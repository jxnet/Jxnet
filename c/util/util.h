#include <jni.h>
#include <pcap.h>

void setMsg(JNIEnv *env, jobject jobj, const char *message);

jint toJint(void *ptr);

jlong toJlong(void *ptr);

void *toPcap(jlong lp);

void *toPacket(jint ip);

pcap_t *getPcap(jlong lp);

u_char *getPacket(jint ip);

pcap_dumper_t *getPcapDumper(jlong lp);

void setPcap(JNIEnv *env, jobject jobj, jfieldID FID, pcap_t *pcap);

void setPcapDumper(JNIEnv *env, jobject jobj, jfieldID FID, pcap_dumper_t *pcap_dumper);