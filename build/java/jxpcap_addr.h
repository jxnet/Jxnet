#include <jni.h>
#include <pcap.h>
#include "util/jxpcap_ids.h"

jobject NewJxpcapAddr(JNIEnv *env, jobject jxpcap_addr, jobject jerrbuf, pcap_addr_t *addresses);