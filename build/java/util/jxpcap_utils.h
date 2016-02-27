#include <jni.h>
#include <pcap.h>

#define PCAP_SENDPACKET	0x093
#define PCAP_INJECT     0x097

/*
jmethodID GetJavaMethodID(JNIEnv *env, jobject obj, const char *field_name, const char *sig);

jobject NewJavaObject(JNIEnv *env, jclass class, const char *field_name, const char *sig);
*/
void SetPcap(JNIEnv *env, jobject obj, pcap_t *pcap);

pcap_t *GetPcap(JNIEnv *env, jobject obj);

void SetString(JNIEnv *env, jobject buf, const char *str);