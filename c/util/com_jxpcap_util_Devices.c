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
#include <string.h>
#include <errno.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <unistd.h>
#include <linux/netlink.h>
#include <linux/rtnetlink.h>
#include <arpa/inet.h>
#endif

#define BUFSIZE			8192

struct route_info {
	struct in_addr dst_addr;
	struct in_addr src_addr;
	struct in_addr gw_addr;
	char if_name[16];
};


int compare_strings(char *first, char *second) {
   while (*first == *second) {
      if (*first == '\0' || *second == '\0')
         break;
 
      first++;
      second++;
   }
 
   if (*first == '\0' && *second == '\0')
      return 1;
   else
      return 0;
}

#ifndef WIN32
char *get_gw(struct nlmsghdr *nl_hdr, struct route_info *rt_info, char *if_name) {
	struct rtmsg *rt_msg;
	struct rtattr *rt_attr;
	int rt_len;
	rt_msg = (struct rtmsg *) NLMSG_DATA(nl_hdr);
	if ((rt_msg->rtm_family != AF_INET) || (rt_msg->rtm_table != RT_TABLE_MAIN)) {
		return NULL;
	}
	rt_attr = (struct rtattr *) RTM_RTA(rt_msg);
	rt_len = RTM_PAYLOAD(nl_hdr);

	for (;RTA_OK(rt_attr, rt_len); rt_attr = RTA_NEXT(rt_attr, rt_len)) {
		switch (rt_attr->rta_type) {
			case RTA_OIF:
	            if_indextoname(*(int *) RTA_DATA(rt_attr), rt_info->if_name);
	            break;
	        case RTA_GATEWAY:
	            rt_info->gw_addr.s_addr= *(u_int *) RTA_DATA(rt_attr);
	            break;
	        case RTA_PREFSRC:
	            rt_info->src_addr.s_addr= *(u_int *) RTA_DATA(rt_attr);
	            break;
	        case RTA_DST:
	            rt_info->dst_addr.s_addr= *(u_int *) RTA_DATA(rt_attr);
	            break;
		}
	}

	if(compare_strings(if_name, (char *) rt_info->if_name) == 1) {
		return (char *) inet_ntoa(rt_info->gw_addr);
	}
	return NULL;
}


int read_nl_sock(int sock, char *msg_buf, int msg_seq, int pid) {
	struct nlmsghdr *nlHdr;
    int read_len = 0, msg_len = 0;
    do {
		if ((read_len = recv(sock, msg_buf, BUFSIZE - msg_len, 0)) < 0) {
			return -1;
		}
		nlHdr = (struct nlmsghdr *) msg_buf;
		if ((NLMSG_OK(nlHdr, read_len) == 0) || (nlHdr->nlmsg_type == NLMSG_ERROR)) {
			return -1;
		}
		if (nlHdr->nlmsg_type == NLMSG_DONE) {
			break;
		} else {
			msg_buf += read_len;
			msg_len += read_len;
		}
		if ((nlHdr->nlmsg_flags & NLM_F_MULTI) == 0) {
			break;
		}
    } while ((nlHdr->nlmsg_seq != msg_seq) || (nlHdr->nlmsg_pid != pid));
    return msg_len;
}

char *get_gateway(char *if_name) {
	int msg_seq = 0;
	int sock; int len;
	char msg_buf[BUFSIZE];

	struct nlmsghdr *nl_msg;
	/*struct rtmsg *rt_msg;*/
	struct route_info *rt_info;

	if((sock = socket(PF_NETLINK, SOCK_DGRAM, NETLINK_ROUTE)) < 0) {
	    return NULL;
	}
	memset(msg_buf, 0, BUFSIZ);

	nl_msg = (struct nlmsghdr *) msg_buf;
	/*rt_msg = (struct rtmsg *) NLMSG_DATA(nl_msg);*/

	nl_msg->nlmsg_len = NLMSG_LENGTH(sizeof(struct rtmsg));
	nl_msg->nlmsg_type = RTM_GETROUTE;
	nl_msg->nlmsg_flags = NLM_F_DUMP | NLM_F_REQUEST;
	nl_msg->nlmsg_seq = msg_seq++;
	nl_msg->nlmsg_pid = getpid();

	if(send(sock, nl_msg, nl_msg->nlmsg_len, 0) < 0) {
	  	return NULL;
	}

	if ((len = read_nl_sock(sock, msg_buf, msg_seq, getpid())) < 0) {
	    return NULL;
	}

	rt_info = (struct route_info *) malloc(sizeof(struct route_info));

	memset(rt_info, 0, sizeof(struct route_info));

	return get_gw(nl_msg, rt_info, if_name);
}
#endif

char *get_mac_addr(JNIEnv *env, char *if_name, jobject jerrmsg) {
	const unsigned char *mac;
	#ifdef WIN32
	IP_ADAPTER_INFO AdapterInfo[16];
	DWORD dwBufLen = sizeof(AdapterInfo);
	DWORD dwStatus = GetAdaptersInfo(AdapterInfo, &dwBufLen);
	assert(dwStatus == ERROR_SUCCESS);
	PIP_ADAPTER_INFO pAdapterInfo = AdapterInfo;
	char *ifname;
	do {
		if(pAdapterInfo->Type == MIB_IF_TYPE_ETHERNET) {
			ifname = pAdapterInfo->AdapterName;
			if(strcmp(ifname, if_name) == 0) {
				mac = pAdapterInfo->Address;
				break;
			}
		}
		pAdapterInfo = pAdapterInfo->Next; 
	} while(pAdapterInfo);
	#else
	struct ifreq ifr;
	size_t if_name_len=strlen(if_name);
	if (if_name_len<sizeof(ifr.ifr_name)) {
    		memcpy(ifr.ifr_name,if_name,if_name_len);
    		ifr.ifr_name[if_name_len]=0;
	} else {
		setMsg(env, jerrmsg, "Interface name is too long.");
		return NULL;
	}
	int fd=socket(AF_UNIX,SOCK_DGRAM,0);
	if (fd==-1) {
		setMsg(env, jerrmsg, strerror(errno));
		return NULL;
	}
	if (ioctl(fd,SIOCGIFHWADDR,&ifr)==-1) {
 		setMsg(env, jerrmsg, strerror(errno));
		return NULL;
	}
	if (ifr.ifr_hwaddr.sa_family!=ARPHRD_ETHER) {
		setMsg(env, jerrmsg, "Not an Ethernet interface.");
		return NULL;
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

jobject setNetIface(JNIEnv *env, jobject jdevice_list, jmethodID List_addMID, pcap_if_t *device_list, jobject jerrmsg) {
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
	jfieldID gatewayFID = (*env)->GetFieldID(env, NetworkInterface, "gateway", "Ljava/lang/String;");
	/*jfieldID AF_NAMEFID = (*env)->GetFieldID(env, NetworkInterface, "AF_NAME", "Ljava/lang/String;");*/

	jobject jobj = (*env)->NewObject(env, NetworkInterface, NetworkInterfaceInit);

	if(device_list->next != NULL) {
		jobject NI = setNetIface(env, jdevice_list, List_addMID, device_list->next, jerrmsg);
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
		char *if_name;
		jobject jstr_if_name = (*env)->NewStringUTF(env, device_list->name);
		const u_char *mac;
		char *mac_addr = (char *) malloc(16 * sizeof(u_char));
		jobject jstr_mac_addr;
		char *gateway;
		jobject jstr_gateway;
#ifdef WIN32
		strtok(device_list->name, "_");
		if_name = strtok(NULL, "_");
#else
		if_name = device_list->name;
#endif
		(*env)->SetObjectField(env, jobj, nameFID, jstr_if_name);
		(*env)->DeleteLocalRef(env, jstr_if_name);

#ifdef WIN32

#else
		struct ifreq ifr;
		size_t if_name_len=strlen(if_name);
		if (if_name_len<sizeof(ifr.ifr_name)) {
		   		memcpy(ifr.ifr_name,if_name,if_name_len);
		   		ifr.ifr_name[if_name_len]=0;
		} else {
			setMsg(env, jerrmsg, "Interface name is too long.");
		}
		int fd=socket(AF_UNIX,SOCK_DGRAM,0);
		if (fd==-1) {
			setMsg(env, jerrmsg, strerror(errno));
		}
		if (ioctl(fd,SIOCGIFHWADDR,&ifr)==-1) {
			setMsg(env, jerrmsg, strerror(errno));
		}
		if (ifr.ifr_hwaddr.sa_family!=ARPHRD_ETHER) {
			setMsg(env, jerrmsg, "Not an Ethernet interface.");
		}
		mac=(u_char*)ifr.ifr_hwaddr.sa_data;
		gateway = get_gateway(if_name);
#endif
		sprintf(mac_addr, "%02X:%02X:%02X:%02X:%02X:%02X", mac[0],mac[1],mac[2],mac[3],mac[4],mac[5]);
		jstr_mac_addr = (*env)->NewStringUTF(env, mac_addr);
		jstr_gateway = (*env)->NewStringUTF(env, gateway);
		(*env)->SetObjectField(env, jobj, mac_addressFID, jstr_mac_addr);
		(*env)->SetObjectField(env, jobj, gatewayFID, jstr_gateway);
		(*env)->DeleteLocalRef(env, jstr_mac_addr);
		(*env)->DeleteLocalRef(env, jstr_gateway);
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
	/*char ip6str[128];*/
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
	  jobject jobj = setNetIface(env, jdevice_list, List_addMID, device_list, jerrmsg);
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
