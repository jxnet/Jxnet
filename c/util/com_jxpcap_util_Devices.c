#include "../jni/com_jxpcap_util_Devices.h"
#include "../util/util.h"
#include <pcap.h>

#ifdef WIN32
#include <Winsock2.h>
#include <Windows.h>
#include <iphlpapi.h>
#include <Assert.h>
#include <stdio.h>
#pragma comment(lib, "iphlpapi.lib")
#endif

#ifndef WIN32
#include <sys/socket.h>
#include <netdb.h>
#include <sys/ioctl.h>
#include <net/if.h>
#include <net/if_arp.h>
#include <sys/types.h>
#endif

char *get_mac_addr(char *if_name) {
	const unsigned char *mac;
	#ifdef WIN32
	IP_ADAPTER_INFO AdapterInfo[16];
	DWORD dwBufLen = sizeof(AdapterInfo);
	DWORD dwStatus = GetAdaptersInfo(AdapterInfo, &dwBufLen);
	assert(dwStatus == ERROR_SUCCESS);
	PIP_ADAPTER_INFO pAdapterInfo = AdapterInfo;
	if(strcmp(pAdapterInfo->AdapterName,if_name) == 1) {
		mac = pAdapterInfo->AdapterName;
	} else {
		return NULL;
	}
	#else
	struct ifreq ifr;
	size_t if_name_len=strlen(if_name);
	if (if_name_len<sizeof(ifr.ifr_name)) {
    		memcpy(ifr.ifr_name,if_name,if_name_len);
    		ifr.ifr_name[if_name_len]=0;
	} else {
		printf("interface name is too long");
	}
	int fd=socket(AF_UNIX,SOCK_DGRAM,0);
	if (fd==-1) {
    		printf("%s",strerror(errno));
	}
	if (ioctl(fd,SIOCGIFHWADDR,&ifr)==-1) {
 		int temp_errno=errno;
    		printf("%s",strerror(temp_errno));
	}
	if (ifr.ifr_hwaddr.sa_family!=ARPHRD_ETHER) {
    		printf("not an Ethernet interface");
	}
	mac=(unsigned char*)ifr.ifr_hwaddr.sa_data;
	#endif
	char *mac_addr = (char *) malloc (16 * sizeof (char));;
	sprintf(mac_addr, "%02X:%02X:%02X:%02X:%02X:%02X", mac[0],mac[1],mac[2],mac[3],mac[4],mac[5]);
	return (char *) mac_addr;
}

#define IPTOSBUFFERS	12
char *iptos(u_long in) {
	static char output[IPTOSBUFFERS][3*4+3+1];
    static short which;
    u_char *p;
    p = (u_char *)&in;
    which = (which + 1 == IPTOSBUFFERS ? 0 : which + 1);
    sprintf(output[which], "%d.%d.%d.%d", p[0], p[1], p[2], p[3]);
    return output[which];
}
/*
char* ip6tos(struct sockaddr *sockaddr, char *address, int addrlen) {
    socklen_t sockaddrlen;
    #ifdef WIN32
    sockaddrlen = sizeof(struct sockaddr_in6);
    #else
    sockaddrlen = sizeof(struct sockaddr_storage);
    #endif
    if(getnameinfo(sockaddr,
        sockaddrlen,
        address,
        addrlen,
        NULL,
        0,
        NI_NUMERICHOST) != 0) address = NULL;
    return address;
}*/

jobject setNetIface(JNIEnv *env, jobject jdevice_list, jmethodID List_addMID, pcap_if_t *device_list) {
	jclass NetworkInterface = (*env)->FindClass(env, "com/jxpcap/NetworkInterface");
	jmethodID NetworkInterfaceInit = (*env)->GetMethodID(env, NetworkInterface, "<init>", "()V");
	jfieldID nextFID = (*env)->GetFieldID(env, NetworkInterface, "next", "Lcom/jxpcap/NetworkInterface;");
	jfieldID nameFID = (*env)->GetFieldID(env, NetworkInterface, "name", "Ljava/lang/String;");
	jfieldID descriptionFID = (*env)->GetFieldID(env, NetworkInterface, "description", "Ljava/lang/String;");
	jfieldID ip_addressFID = (*env)->GetFieldID(env, NetworkInterface, "ip_address", "Ljava/lang/String;");
	jfieldID netmaskFID = (*env)->GetFieldID(env, NetworkInterface, "netmask", "Ljava/lang/String;");
	jfieldID broadcast_addressFID = (*env)->GetFieldID(env, NetworkInterface, "broadcast_address", "Ljava/lang/String;");
	jfieldID mac_addressFID = (*env)->GetFieldID(env, NetworkInterface, "mac_address", "Ljava/lang/String;");
	jfieldID destination_addressFID = (*env)->GetFieldID(env, NetworkInterface, "destination_address", "Ljava/lang/String;");
	jfieldID AF_NAMEFID = (*env)->GetFieldID(env, NetworkInterface, "AF_NAME", "Ljava/lang/String;");

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
		/*char *p = strtok (device_list->name,"_");
		char *tmp[2];
		int i;
		while (p != NULL)
		{
			tmp[i++] = p;
			p = strtok (NULL, "/");
		}*/	
		char *m = get_mac_addr(device_list->name);
		if(m != NULL) {
			jobject jstr_mac = (*env)->NewStringUTF(env, m);
			(*env)->SetObjectField(env, jobj, mac_addressFID, jstr_mac);
			(*env)->DeleteLocalRef(env, jstr_mac);
		} else {
			(*env)->SetObjectField(env, jobj, mac_addressFID, NULL);
		}
		(*env)->SetObjectField(env, jobj, nameFID, jstr);
		(*env)->DeleteLocalRef(env, jstr);
	} else {
		(*env)->SetObjectField(env, jobj, nameFID, NULL);
	}
	if(device_list->description != NULL) {
		jobject jstr = (*env)->NewStringUTF(env, device_list->description);
		(*env)->SetObjectField(env, jobj, descriptionFID, jstr);
		(*env)->DeleteLocalRef(env, jstr);
	} else {
		(*env)->SetObjectField(env, jobj, descriptionFID, NULL);
	}

	pcap_addr_t *address;
	char ip6str[128];
	for(address=device_list->addresses; address; address=address->next) {
		switch(address->addr->sa_family) {
			case AF_INET:
				if (address->addr) {
					jobject jstr = (*env)->NewStringUTF(env, iptos(((struct sockaddr_in *)address->addr)->sin_addr.s_addr));
					(*env)->SetObjectField(env, jobj, ip_addressFID, jstr);
					(*env)->DeleteLocalRef(env, jstr);
				} else {
					(*env)->SetObjectField(env, jobj, ip_addressFID, NULL);
				}
				if (address->netmask) {
					jobject jstr = (*env)->NewStringUTF(env, iptos(((struct sockaddr_in *)address->netmask)->sin_addr.s_addr));
					(*env)->SetObjectField(env, jobj, netmaskFID, jstr);
					(*env)->DeleteLocalRef(env, jstr);
				} else {
					(*env)->SetObjectField(env, jobj, netmaskFID, NULL);
				}
				if (address->broadaddr) {
					jobject jstr = (*env)->NewStringUTF(env, iptos(((struct sockaddr_in *)address->broadaddr)->sin_addr.s_addr));
					(*env)->SetObjectField(env, jobj, broadcast_addressFID, jstr);
					(*env)->DeleteLocalRef(env, jstr);
				} else {
					(*env)->SetObjectField(env, jobj, broadcast_addressFID, NULL);
				}
				if (address->dstaddr) {
					jobject jstr = (*env)->NewStringUTF(env, iptos(((struct sockaddr_in *)address->dstaddr)->sin_addr.s_addr));
					(*env)->SetObjectField(env, jobj, destination_addressFID, jstr);
					(*env)->DeleteLocalRef(env, jstr);
				} else {
					(*env)->SetObjectField(env, jobj, destination_addressFID, NULL);
				}
				break;

			case AF_INET6:
				//printf("\tAddress Family Name: AF_INET6\n");
		        //if (address->addr)
		        	//printf("\tAddress ->: %s\n", ip6tos(address->addr, ip6str, sizeof(ip6str)));
		        break;
			default:
		        //printf("\tAddress Family Name: Unknown\n");
		        break;
		}
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
