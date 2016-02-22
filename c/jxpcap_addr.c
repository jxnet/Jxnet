#include <stdio.h>
#include <jni.h>
#include <pcap.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include "jxpcap_addr.h"
#include "sockaddr.h"
#include "util/jxpcap_ids.h"

jobject NewJxpcapAddr(JNIEnv *env, jobject jxpcap_addresses, jmethodID add_MID_Addr, jobject jerrbuf, pcap_addr_t *addresses) {
	
	jobject obj = (*env)->NewObject(env, JxpcapAddrClass, JxpcapAddrInitMID);
	
	if(addresses->next != NULL) {
		jobject jxpcap_addr = NewJxpcapAddr(env, jxpcap_addresses, add_MID_Addr, jerrbuf, addresses->next);
		if(jxpcap_addresses == NULL) {
			(*env)->DeleteLocalRef(env, jxpcap_addr);  
			return NULL;
		}
		(*env)->SetObjectField(env, obj, JxpcapAddrNextFID, jxpcap_addresses);
		if((*env)->CallBooleanMethod(env, jxpcap_addresses, add_MID_Addr, jxpcap_addr) == JNI_FALSE) {
			(*env)->DeleteLocalRef(env, jxpcap_addresses);
			return NULL;
		}
	} else {
		(*env)->SetObjectField(env, obj, JxpcapAddrNextFID, NULL);
	}

	jobject jxpcap_sockaddr;
	if(addresses->addr != NULL) {
		if((jxpcap_sockaddr = NewSockAddr(env, addresses->addr)) == NULL) {
			return NULL; /* (*env)->DeleteLocalRef(env, jxpcap_sockaddr);*/
		}
		(*env)->SetObjectField(env, obj, JxpcapAddrAddrFID, jxpcap_sockaddr);
	} else {
		(*env)->SetObjectField(env, obj, JxpcapAddrAddrFID, NULL);
	}
	if(addresses->netmask != NULL) {
		if((jxpcap_sockaddr = NewSockAddr(env, addresses->netmask)) == NULL) {
			return NULL;
		}
		(*env)->SetObjectField(env, obj, JxpcapAddrNetmaskFID, jxpcap_sockaddr);
	} else {
		(*env)->SetObjectField(env, obj, JxpcapAddrNetmaskFID, NULL);
	}
	if(addresses->broadaddr != NULL) {
		if((jxpcap_sockaddr = NewSockAddr(env, addresses->broadaddr)) == NULL) {
			return NULL;
		}
		(*env)->SetObjectField(env, obj, JxpcapAddrBroadAddrFID, jxpcap_sockaddr);
	} else {
		(*env)->SetObjectField(env, obj, JxpcapAddrBroadAddrFID, NULL);
	}
	if(addresses->dstaddr != NULL) {
		if((jxpcap_sockaddr = NewSockAddr(env, addresses->dstaddr)) == NULL) {
			return NULL;
		}
		(*env)->SetObjectField(env, obj, JxpcapAddrDstAddrFID, jxpcap_sockaddr);
	} else {
		(*env)->SetObjectField(env, obj, JxpcapAddrDstAddrFID, jxpcap_sockaddr);
	}
	return obj;
}