#include <jni.h>
#include <pcap.h>
#include "util/jxpcap_utils.h"
#include "util/jxpcap_ids.h"
#include "jxpcap_addr.h"

jobject NewJxpcapIf(JNIEnv *env, jobject jalldevsp, jmethodID add_MID, jobject jerrbuf, pcap_if_t *alldevsp) {
	
	jobject field;
	jobject obj = (*env)->NewObject(env, JxpcapIfClass, JxpcapIfInitMID);
	
	if(JxpcapIfInitMID == NULL)
		puts("JxpcapIfInitMID is NULL");
	
	if(alldevsp->next != NULL) {
		jobject jxpcap_if = NewJxpcapIf(env, jalldevsp, add_MID, jerrbuf, alldevsp->next);
		if(jxpcap_if == NULL)
			return NULL;
		(*env)->SetObjectField(env, obj, JxpcapIfNextFID, jxpcap_if);
		if((*env)->CallBooleanMethod(env, jalldevsp, add_MID, jxpcap_if) == JNI_FALSE) {
			(*env)->DeleteLocalRef(env, jxpcap_if);
			return NULL;
		}
		(*env)->DeleteLocalRef(env, jxpcap_if);
	} else {
		(*env)->SetObjectField(env, obj, JxpcapIfNextFID, NULL);
	}

	if(alldevsp->name != NULL) {
		field = (*env)->NewStringUTF(env, alldevsp->name);
		if(field == NULL) {
			return NULL;
		}
		(*env)->SetObjectField(env, obj, JxpcapIfNameFID, field);
		(*env)->DeleteLocalRef(env, field);
	} else {
		(*env)->SetObjectField(env, obj, JxpcapIfNameFID, NULL);
	}
	
	if(alldevsp->description != NULL) {
		field = (*env)->NewStringUTF(env, alldevsp->description);
		if(field == NULL) {
			return NULL;
		}
		(*env)->SetObjectField(env, obj, JxpcapIfDescriptionFID, field);
		(*env)->DeleteLocalRef(env, field);
	} else {
		(*env)->SetObjectField(env, obj, JxpcapIfDescriptionFID, NULL);
	}
	
	if(alldevsp->addresses != NULL) {
		jobject jxpcap_addresses = (*env)->GetObjectField(env, obj, JxpcapIfAddressesFID);
		if(jxpcap_addresses == NULL) {
			return NULL; // Exception already thrown
		}
		jmethodID add_MID_Addr = GetJavaMethodID(env, jxpcap_addresses, "add", "(Ljava/lang/Object;)Z"); 
		if(add_MID_Addr == NULL) {
			(*env)->DeleteLocalRef(env, jxpcap_addresses);
			return NULL; // Exception already thrown
		}
		jobject jxpcap_addr = NewJxpcapAddr(env, jxpcap_addresses, add_MID_Addr, jerrbuf, alldevsp->addresses);
		if(jxpcap_addr == NULL) {
			(*env)->DeleteLocalRef(env, jxpcap_addresses);
			return NULL;
		}
		if((*env)->CallBooleanMethod(env, jxpcap_addresses, add_MID_Addr, jxpcap_addr) == JNI_FALSE) {
			(*env)->DeleteLocalRef(env, jxpcap_addresses);
			(*env)->DeleteLocalRef(env, jxpcap_addr);
			return NULL;
		}
		(*env)->DeleteLocalRef(env, jxpcap_addresses);
		(*env)->DeleteLocalRef(env, jxpcap_addr);
	}
	
	(*env)->SetIntField(env, obj, JxpcapIfFlagsFID, (jint) alldevsp->flags);
	
	return obj;
}