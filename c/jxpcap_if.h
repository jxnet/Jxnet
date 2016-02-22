#include <jni.h>
#include <pcap.h>

jobject NewJxpcapIf(JNIEnv *env, jobject jalldevsp, jmethodID add_MID, jobject jerrbuf, pcap_if_t *alldevsp);