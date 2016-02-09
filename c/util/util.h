#include <jni.h>
#include <pcap.h>

void setMsg(JNIEnv *env, jobject jobj, const char *message);

jlong toJlong(void *ptr);

void *toPcap(jlong lp);

pcap_t *getPcap(jlong lp);

pcap_dumper_t *getPcapDumper(jlong lp);

void setPcap(JNIEnv *env, jobject jobj, jfieldID FID, pcap_t *pcap);

void setPcapDumper(JNIEnv *env, jobject jobj, jfieldID FID, pcap_dumper_t *pcap_dumper);