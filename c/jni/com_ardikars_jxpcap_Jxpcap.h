/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_ardikars_jxpcap_Jxpcap */

#ifndef _Included_com_ardikars_jxpcap_Jxpcap
#define _Included_com_ardikars_jxpcap_Jxpcap
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_ardikars_jxpcap_Jxpcap
 * Method:    nativeSendPacket
 * Signature: (Lcom/ardikars/jxpcap/Jxpcap;Ljava/nio/ByteBuffer;I)I
 */
JNIEXPORT jint JNICALL Java_com_ardikars_jxpcap_Jxpcap_nativeSendPacket
  (JNIEnv *, jclass, jobject, jobject, jint);

/*
 * Class:     com_ardikars_jxpcap_Jxpcap
 * Method:    nativeFindAllDevs
 * Signature: (Ljava/util/List;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_ardikars_jxpcap_Jxpcap_nativeFindAllDevs
  (JNIEnv *, jclass, jobject, jstring);

#ifdef __cplusplus
}
#endif
#endif
