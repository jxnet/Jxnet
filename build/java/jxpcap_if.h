#include <jni.h>
#include <pcap.h>

jobject NewJxpcapIf(JNIEnv *env, jobject jalldevsp, jobject jerrbuf, pcap_if_t *alldevsp);