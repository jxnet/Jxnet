#include <jni.h>
#include <pcap.h>
#include "util/jxpcap_ids.h"

jobject NewJxpcapAddr(JNIEnv *env, jobject jxpcap_addr, jmethodID add_MID_Addr, jobject jerrbuf, pcap_addr_t *addresses);