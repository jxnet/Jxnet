#include "../jni/com_jxpcap_util_Devices.h"
#include "../util/util.h"
#include <pcap.h>


jobject setNetIface(JNIEnv *env, jobject jdevice_list, jmethodID List_addMID, pcap_if_t *device_list) {
	jclass NetworkInterface = (*env)->FindClass(env, "com/jxpcap/NetworkInterface");
	jmethodID NetworkInterfaceInit = (*env)->GetMethodID(env, NetworkInterface, "<init>", "()V");
	jfieldID nextFID = (*env)->GetFieldID(env, NetworkInterface, "next", "Lcom/jxpcap/NetworkInterface;");
	jfieldID nameFID = (*env)->GetFieldID(env, NetworkInterface, "name", "Ljava/lang/String;");
	jobject jobj = (*env)->NewObject(env, NetworkInterface, NetworkInterfaceInit);
	if(device_list->next != NULL) {
		jobject NI = setNetIface(env, jdevice_list, List_addMID, device_list->next);
		if(NI == NULL) {
			return NULL;
		}
		(*env)->SetObjectField(env, jobj, nextFID, NI);
		if((*env)->CallBooleanMethod(env, jdevice_list, List_addMID, NI) == JNI_FALSE) {
			(*env)->DeleteLocalRef(env, NI);
			return NULL;
		}
		(*env)->DeleteLocalRef(env, NI);
	} else {
		(*env)->SetObjectField(env, jobj, nextFID, NULL);
	}
	if(device_list->name != NULL) {
		jobject jstr = (*env)->NewStringUTF(env, device_list->name);
		if(jstr == NULL) {
			return NULL;
		}
		(*env)->SetObjectField(env, jobj, nameFID, jstr);
		(*env)->DeleteLocalRef(env, jstr);
	} else {
		(*env)->SetObjectField(env, jobj, nameFID, NULL);
	}
	return jobj;
}

JNIEXPORT jobject JNICALL Java_com_jxpcap_util_Devices_nativeGetAllDevices
  (JNIEnv *env, jclass jcls, jobject jdevice_list, jobject jerrmsg) {
	  char errmsg[256]; errmsg[0] = '\0';
	  pcap_if_t *device_list;
	  int result = pcap_findalldevs(&device_list, errmsg);
	  setMsg(env, jerrmsg, errmsg);
	  if(result != 0 || device_list == NULL) {
		  return NULL;
	  }
	  jclass List = (*env)->FindClass(env, "java/util/List");
	  jmethodID List_addMID = (*env)->GetMethodID(env, List, "add", "(Ljava/lang/Object;)Z");
	  jobject jobj = setNetIface(env, jdevice_list, List_addMID, device_list);
	  if(jobj == NULL) {
		  return NULL;
	  }
	  if((*env)->CallBooleanMethod(env, jdevice_list, List_addMID, jobj) == JNI_FALSE) {
		  (*env)->DeleteLocalRef(env, jobj);
		  return NULL;
	  }
	  (*env)->DeleteLocalRef(env, jobj);
	  pcap_freealldevs(device_list);
	  return jobj;
  }
