#include <jni.h>
#include <pcap.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include "sockaddr.h"
#include "util/jxpcap_ids.h"

jobject NewSockAddr(JNIEnv *env, struct sockaddr *address) {
	jobject obj = (*env)->NewObject(env, SockAddrClass, SockAddrInitMID);
	(*env)->SetShortField(env, obj, SockAddrSaFamilyFID, (jshort) address->sa_family);
	if(address->sa_family == AF_INET) {
		jbyteArray jarray = (*env)->NewByteArray(env, 4);
		(*env)->SetByteArrayRegion(env, jarray, 0, 4, (jbyte *)(address->sa_data + 2));
		(*env)->SetObjectField(env, obj, SockAddrDataFID, jarray);
		(*env)->DeleteLocalRef(env, jarray);
	} else if(address->sa_family == AF_INET6) {
		jbyteArray jarray = (*env)->NewByteArray(env, 16);
		(*env)->SetByteArrayRegion(env, jarray, 0, 16, (jbyte *)&((struct sockaddr_in6 *)address)->sin6_addr);
		(*env)->SetObjectField(env, obj, SockAddrDataFID, jarray);
		(*env)->DeleteLocalRef(env, jarray);
	} else {
		jbyteArray jarray = (*env)->NewByteArray(env, 14);
		(*env)->SetByteArrayRegion(env, jarray, 0, 14, (jbyte *)(address->sa_data + 2));
		(*env)->SetObjectField(env, obj, SockAddrDataFID, jarray);
		(*env)->DeleteLocalRef(env, jarray);
	}
	return obj;
}