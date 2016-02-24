#include <jni.h>
#include <pcap.h>

jmethodID GetJavaMethodID(JNIEnv *env, jobject obj, const char *field_name, const char *sig);

jobject NewJavaObject(JNIEnv *env, jclass class, const char *field_name, const char *sig);

void SetPcap(JNIEnv *env, jobject obj, pcap_t *pcap);

pcap_t *GetPcap(JNIEnv *env, jobject obj);

void SetString(JNIEnv *env, jobject buf, const char *str);