/**
 * Copyright (C) 2017  Ardika Rommy Sanjaya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

#ifndef _Included_com_ardikars_jxnet_util_AddrUtils
#define _Included_com_ardikars_jxnet_util_AddrUtils
#ifdef __cplusplus
extern "C" {
#endif

#include "../include/jxnet/com_ardikars_jxnet_util_AddrUtils.h"

#include <stdio.h>
#include <string.h>

#if defined(__linux__) || defined(__FreeBSD__)
#include <sys/types.h>
#include <sys/socket.h> 
#include <sys/ioctl.h>
#include <net/if.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <arpa/inet.h>
#endif

#if defined(__FreeBSD__)
#include <sys/sysctl.h>
#include <net/if_dl.h>
#elif defined(__linux__)
#include <string.h>
#include <unistd.h>
#include <linux/netlink.h>
#include <linux/rtnetlink.h>
#elif defined(WIN32)
#include <winsock2.h>
#include <iphlpapi.h>
#endif

#include "../src/utils.h"

JNIEXPORT jbyteArray JNICALL Java_com_ardikars_jxnet_util_AddrUtils_GetMACAddress
  (JNIEnv *env, jclass jcls, jstring jdev_name) {
	  
	jbyteArray hw_addr;
	const char *buf = (*env)->GetStringUTFChars(env, jdev_name, 0);
	
#if defined(WIN32) || defined(__CYGWIN__)
	PIP_ADAPTER_INFO pAdapterInfo;
	PIP_ADAPTER_INFO pAdapter = NULL;
	DWORD dwRetVal = 0;
	u_long ulOutBufLen = sizeof (IP_ADAPTER_INFO);
	pAdapterInfo = (IP_ADAPTER_INFO *) malloc(sizeof (IP_ADAPTER_INFO));
	if (pAdapterInfo == NULL) {
		(*env)->ReleaseStringUTFChars(env, jdev_name, buf);
		ThrowNew(env, JXNET_EXCEPTION, "Error allocating memory needed to call GetAdaptersinfo");
		return NULL;
	}
	if (GetAdaptersInfo(pAdapterInfo, &ulOutBufLen) == ERROR_BUFFER_OVERFLOW) {
		free(pAdapterInfo);
		pAdapterInfo = (IP_ADAPTER_INFO *) malloc(ulOutBufLen);
		if (pAdapterInfo == NULL) {
			(*env)->ReleaseStringUTFChars(env, jdev_name, buf);
			ThrowNew(env, JXNET_EXCEPTION, "Error allocating memory needed to call GetAdaptersinfo\n");
			return NULL;
		}
	}
	if ((dwRetVal = GetAdaptersInfo(pAdapterInfo, &ulOutBufLen)) == NO_ERROR) {
		pAdapter = pAdapterInfo;
		while (pAdapter) {
			const char *p1 = strchr(buf, '{');
			const char *p2 = strchr(pAdapter->AdapterName,  '{');
			if(p1 == NULL || p2 == NULL) {
				p1 = buf;
				p2 = pAdapter->AdapterName;
			}
			if((strcmp(p1, p2) == 0)) {
				hw_addr = (*env)->NewByteArray(env, (jsize) pAdapter->AddressLength);
				(*env)->SetByteArrayRegion(env, hw_addr, 0 , pAdapter->AddressLength, (jbyte *) pAdapter->Address);
				break;
			}
			pAdapter = pAdapter->Next;
        }
    } else {
    	(*env)->ReleaseStringUTFChars(env, jdev_name, buf);
    	ThrowNew(env, JXNET_EXCEPTION, "GetAdaptersInfo failed\n");
		return NULL;
	}
	if (pAdapterInfo)
		free(pAdapterInfo);
		
#elif defined(__linux__)

	char mac_addr[6];
	struct ifreq ifr;
	int sd = socket(PF_INET, SOCK_DGRAM, 0);
	if (sd < 0) {
		(*env)->ReleaseStringUTFChars(env, jdev_name, buf);
		ThrowNew(env, JXNET_EXCEPTION, "Can't create socket");
		return NULL;
	}
	memset(&ifr, 0, sizeof(ifr));
	strncpy(ifr.ifr_ifrn.ifrn_name, buf, IFNAMSIZ);
	if (ioctl(sd, SIOCGIFHWADDR, &ifr) != 0) {
		(*env)->ReleaseStringUTFChars(env, jdev_name, buf);
		return NULL;
	}
	bcopy((u_char *)ifr.ifr_ifru.ifru_hwaddr.sa_data, mac_addr, 6);
	hw_addr = (*env)->NewByteArray(env, (jsize) 6);
	(*env)->SetByteArrayRegion(env, hw_addr,0 , 6, (jbyte *) mac_addr);
	close(sd);
	
#elif defined(__FreeBSD__)

	int			mib[6];
	size_t			len;
	char			*mac_buf;
	unsigned char		*ptr;
	struct if_msghdr	*ifm;
	struct sockaddr_dl	*sdl;
	mib[0] = CTL_NET;
	mib[1] = AF_ROUTE;
	mib[2] = 0;
	mib[3] = AF_LINK;
	mib[4] = NET_RT_IFLIST;
	if((mib[5] = if_nametoindex(buf)) == 0) {
		// error
	}
	if(sysctl(mib, 6, NULL, &len, NULL, 0) < 0) {
		//error
	}
	if((mac_buf = malloc(len)) == NULL) {
		//error
	}
	if(sysctl(mib, 6, mac_buf, &len, NULL, 0) < 0) {
		//error
	}
	ifm = (struct if_msghdr *) mac_buf;
	sdl = (struct sockaddr_dl *) (ifm + 1);
	free(mac_buf);
	ptr = (unsigned char *) LLADDR(sdl);
	hw_addr = (*env)->NewByteArray(env, (jsize) 6);
	(*env)->SetByteArrayRegion(env, hw_addr, 0, 6, (jbyte *) ptr);
	
#endif

	(*env)->ReleaseStringUTFChars(env, jdev_name, buf);
	
  	return hw_addr;
  }

// Get Gateway Address

#if defined(__linux__)
int read_nl_socket(int fd, char *buf, int msg_seq, int pid) {
    struct nlmsghdr *nl_msg;
    int read_len = 0, msg_len = 0;
	do {
        if ((read_len = recv(fd, buf, 8192 - msg_len, 0)) < 0) return -1;
        nl_msg = (struct nlmsghdr *) buf;
        if ((NLMSG_OK(nl_msg, read_len) == 0) || (nl_msg->nlmsg_type == NLMSG_ERROR)) return -1;
        if (nl_msg->nlmsg_type == NLMSG_DONE) {
            break;
        } else {
            buf += read_len;
            msg_len += read_len;
        }
        if ((nl_msg->nlmsg_flags & NLM_F_MULTI) == 0) {
            break;
        }
    } while ((nl_msg->nlmsg_seq != msg_seq) || (nl_msg->nlmsg_pid != pid));
    return msg_len;
}
#endif

JNIEXPORT jstring JNICALL Java_com_ardikars_jxnet_util_AddrUtils_GetGatewayAddress
  (JNIEnv *env, jclass jcls, jstring jdev_name) {
	const char *buf = (*env)->GetStringUTFChars(env, jdev_name, 0);
#if defined(WIN32) || defined(__CYGWIN__)
	PIP_ADAPTER_INFO pAdapterInfo;
	PIP_ADAPTER_INFO pAdapter = NULL;
	DWORD dwRetVal = 0;
	u_long ulOutBufLen = sizeof (IP_ADAPTER_INFO);
	pAdapterInfo = (IP_ADAPTER_INFO *) malloc(sizeof (IP_ADAPTER_INFO));
	if (pAdapterInfo == NULL) {
		(*env)->ReleaseStringUTFChars(env, jdev_name, buf);
		ThrowNew(env, JXNET_EXCEPTION, "Error allocating memory needed to call GetAdaptersinfo");
		return NULL;
	}
	if (GetAdaptersInfo(pAdapterInfo, &ulOutBufLen) == ERROR_BUFFER_OVERFLOW) {
		free(pAdapterInfo);
		pAdapterInfo = (IP_ADAPTER_INFO *) malloc(ulOutBufLen);
		if (pAdapterInfo == NULL) {
			(*env)->ReleaseStringUTFChars(env, jdev_name, buf);
			ThrowNew(env, JXNET_EXCEPTION, "Error allocating memory needed to call GetAdaptersinfo\n");
			return NULL;
		}
	}
	if ((dwRetVal = GetAdaptersInfo(pAdapterInfo, &ulOutBufLen)) == NO_ERROR) {
		pAdapter = pAdapterInfo;
		while (pAdapter) {
			const char *p1 = strchr(buf, '{');
			const char *p2 = strchr(pAdapter->AdapterName,  '{');
			if(p1 == NULL || p2 == NULL) {
				p1 = buf;
				p2 = pAdapter->AdapterName;
			}
			if((strcmp(p1, p2) == 0)) {
				return (*env)->NewStringUTF(env, pAdapter->GatewayList.IpAddress.String);
				break;
        	}
        	pAdapter = pAdapter->Next;
        }
    } else {
    	(*env)->ReleaseStringUTFChars(env, jdev_name, buf);
    	ThrowNew(env, JXNET_EXCEPTION, "GetAdaptersInfo failed\n");
		return NULL;
	}
	if (pAdapterInfo)
		free(pAdapterInfo);
#elif defined(__linux__)

	struct in_addr route_dst;
	struct in_addr route_gw;
	int nlfd;
	
	if ((nlfd = socket(AF_NETLINK, SOCK_RAW, NETLINK_ROUTE)) < 0) {
		(*env)->ReleaseStringUTFChars(env, jdev_name, buf);
		return NULL;
	}
	
	char msg_buf[8192];
	memset(msg_buf, 0, 8192);
	
	struct nlmsghdr *nl_msg = NULL;
	struct rtmsg *rt_msg = NULL;
	struct rtattr *rt_attr = NULL;
	
	nl_msg = (struct nlmsghdr *) msg_buf;
	rt_msg = (struct rtmsg *) NLMSG_DATA(nl_msg);
	    
    int msg_seq = 0, len = 0, rt_len = 0;
    int pid = getpid();
    
    nl_msg->nlmsg_len = NLMSG_LENGTH(sizeof(struct rtmsg));  // Length of message.
    nl_msg->nlmsg_type = RTM_GETROUTE; // Get the routes from kernel routing table.
	nl_msg->nlmsg_flags = NLM_F_DUMP | NLM_F_REQUEST;    // The message is a request for dump.
    nl_msg->nlmsg_seq = msg_seq++;    // Sequence of the message packet.
    nl_msg->nlmsg_pid = pid;    // PID of process sending the request.
	
	if (send(nlfd, nl_msg, nl_msg->nlmsg_len, 0) < 0) {
		(*env)->ReleaseStringUTFChars(env, jdev_name, buf);
        return NULL;
    }

	if ((len = read_nl_socket(nlfd, msg_buf, msg_seq, getpid())) < 0) {
		(*env)->ReleaseStringUTFChars(env, jdev_name, buf);
		return NULL;
	}
	
	char if_name[32];
	
	for (; NLMSG_OK(nl_msg, len); nl_msg = NLMSG_NEXT(nl_msg, len)) {
		rt_attr = NULL;
		rt_msg = NULL;
		rt_msg = (struct rtmsg *) NLMSG_DATA(nl_msg);
		rt_attr = (struct rtattr *) RTM_RTA(rt_msg);
		rt_len = RTM_PAYLOAD(nl_msg);
		if ((rt_msg->rtm_family != AF_INET) || (rt_msg->rtm_table != RT_TABLE_MAIN)) {
			continue;
		}
		memset(if_name, 0, 32);
		route_gw.s_addr = -1;
		route_dst.s_addr = -1;
		for (; RTA_OK(rt_attr, rt_len); rt_attr = RTA_NEXT(rt_attr, rt_len)) {
			switch (rt_attr->rta_type) {
				case RTA_OIF:
					if_indextoname(*(int *) RTA_DATA(rt_attr), if_name);
					break;
				case RTA_GATEWAY:
					route_gw.s_addr = *(u_int *) RTA_DATA(rt_attr);
					break;
				case RTA_DST:
					route_dst.s_addr= *(u_int *) RTA_DATA(rt_attr);
				break;
			}
		}
		if (strcmp(buf, if_name) == 0 && route_dst.s_addr == 0) {
			break;
		} else {
			route_gw.s_addr = -1;
			route_dst.s_addr = -1;
		}
	}
	close(nlfd);
	char gateway[15];
	if (route_dst.s_addr == 0)
        sprintf(gateway, (char *) inet_ntoa(route_gw));
	(*env)->ReleaseStringUTFChars(env, jdev_name, buf);
	return (*env)->NewStringUTF(env, gateway);
#endif
	(*env)->ReleaseStringUTFChars(env, jdev_name, buf);
  	return NULL;
  }

#ifdef __cplusplus
}
#endif
#endif
