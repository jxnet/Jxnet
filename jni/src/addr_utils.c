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
#define BUFSIZE 8192
char gateway[255];

struct route_info {
	struct in_addr dstAddr;
	struct in_addr srcAddr;
	struct in_addr gateWay;
	char ifName[IF_NAMESIZE];
};

int readNlSock(int sockFd, char *bufPtr, int seqNum, int pId) {
	struct nlmsghdr *nlHdr;
	int readLen = 0, msgLen = 0;
	do {
		/* Recieve response from the kernel */
		if ((readLen = recv(sockFd, bufPtr, BUFSIZE - msgLen, 0)) < 0) {
			perror("SOCK READ: ");
			return -1;
		}
		nlHdr = (struct nlmsghdr *) bufPtr;
		/* Check if the header is valid */
		if ((NLMSG_OK(nlHdr, readLen) == 0)
				|| (nlHdr->nlmsg_type == NLMSG_ERROR)) {
			perror("Error in recieved packet");
			return -1;
		}
		/* Check if the its the last message */
		if (nlHdr->nlmsg_type == NLMSG_DONE) {
			break;
		} else {
			/* Else move the pointer to buffer appropriately */
			bufPtr += readLen;
			msgLen += readLen;
		}
		/* Check if its a multi part message */
		if ((nlHdr->nlmsg_flags & NLM_F_MULTI) == 0) {
			/* return if its not */
			break;
		}
	} while ((nlHdr->nlmsg_seq != (unsigned) seqNum) || (nlHdr->nlmsg_pid != (unsigned) pId));
	return msgLen;
}

/* For printing the routes. */
void printRoute(struct route_info *rtInfo) {
	char tempBuf[512];
	/* Print Destination address */
	if (rtInfo->dstAddr.s_addr != 0)
		strcpy(tempBuf,  inet_ntoa(rtInfo->dstAddr));
	else
		sprintf(tempBuf, "*.*.*.*\t");
	fprintf(stdout, "%s\t", tempBuf);
	/* Print Gateway address */
	if (rtInfo->gateWay.s_addr != 0)
		strcpy(tempBuf, (char *) inet_ntoa(rtInfo->gateWay));
	else
		sprintf(tempBuf, "*.*.*.*\t");
	fprintf(stdout, "%s\t", tempBuf);
	/* Print Interface Name*/
	fprintf(stdout, "%s\t", rtInfo->ifName);
	/* Print Source address */
	if (rtInfo->srcAddr.s_addr != 0)
		strcpy(tempBuf, inet_ntoa(rtInfo->srcAddr));
	else
		sprintf(tempBuf, "*.*.*.*\t");
	fprintf(stdout, "%s\n", tempBuf);
}

void printGateway() {
	printf("%s\n", gateway);
}

/* For parsing the route info returned */
void parseRoutes(struct nlmsghdr *nlHdr, struct route_info *rtInfo, const char *buf) {
	struct rtmsg *rtMsg;
	struct rtattr *rtAttr;
	int rtLen;
	rtMsg = (struct rtmsg *) NLMSG_DATA(nlHdr);
	/* If the route is not for AF_INET or does not belong to main routing table then return. */
	if ((rtMsg->rtm_family != AF_INET) || (rtMsg->rtm_table != RT_TABLE_MAIN))
		return;
	/* get the rtattr field */
	rtAttr = (struct rtattr *) RTM_RTA(rtMsg);
	rtLen = RTM_PAYLOAD(nlHdr);
	for (; RTA_OK(rtAttr, rtLen); rtAttr = RTA_NEXT(rtAttr, rtLen)) {
		switch (rtAttr->rta_type) {
			case RTA_OIF:
				if_indextoname(*(int *) RTA_DATA(rtAttr), rtInfo->ifName);
				break;
			case RTA_GATEWAY:
				rtInfo->gateWay.s_addr= *(u_int *) RTA_DATA(rtAttr);
				break;
			case RTA_PREFSRC:
				rtInfo->srcAddr.s_addr= *(u_int *) RTA_DATA(rtAttr);
				break;
			case RTA_DST:
				rtInfo->dstAddr .s_addr= *(u_int *) RTA_DATA(rtAttr);
				break;
		}
	}
	//printf("%s\n", inet_ntoa(rtInfo->dstAddr));
	if (rtInfo->dstAddr.s_addr == 0 && strcmp(rtInfo->ifName, buf) == 0)
		sprintf(gateway, "%s", (char *) inet_ntoa(rtInfo->gateWay));
	//printRoute(rtInfo);
	return;
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
	struct nlmsghdr *nlMsg = NULL;
	struct route_info *rtInfo = NULL;
	char msgBuf[BUFSIZE];
	int sock, len, msgSeq = 0;
	
	/* Create Socket */
	if ((sock = socket(PF_NETLINK, SOCK_DGRAM, NETLINK_ROUTE)) < 0)
		perror("Socket Creation: ");
	memset(msgBuf, 0, BUFSIZE);
	/* point the header and the msg structure pointers into the buffer */
	nlMsg = (struct nlmsghdr *) msgBuf;
	/* Fill in the nlmsg header*/
	nlMsg->nlmsg_len = NLMSG_LENGTH(sizeof(struct rtmsg));  // Length of message.
	nlMsg->nlmsg_type = RTM_GETROUTE;   // Get the routes from kernel routing table.
	nlMsg->nlmsg_flags = NLM_F_DUMP | NLM_F_REQUEST;    // The message is a request for dump.
	nlMsg->nlmsg_seq = msgSeq++;    // Sequence of the message packet.
	nlMsg->nlmsg_pid = getpid();    // PID of process sending the request.
	/* Send the request */
	if (send(sock, nlMsg, nlMsg->nlmsg_len, 0) < 0) {
		(*env)->ReleaseStringUTFChars(env, jdev_name, buf);
		printf("Write To Socket Failed...\n");
		return NULL;
	}
	/* Read the response */
	if ((len = readNlSock(sock, msgBuf, msgSeq, getpid())) < 0) {
		(*env)->ReleaseStringUTFChars(env, jdev_name, buf);
		printf("Read From Socket Failed...\n");
	    return NULL;
	}
	/* Parse and print the response */
	rtInfo = (struct route_info *) malloc(sizeof(struct route_info));
	//fprintf(stdout, "Destination\tGateway\tInterface\tSource\n");
	for (; NLMSG_OK(nlMsg, len); nlMsg = NLMSG_NEXT(nlMsg, len)) {
		memset(rtInfo, 0, sizeof(struct route_info));
		parseRoutes(nlMsg, rtInfo, buf);
	}
	free(rtInfo);
	close(sock);
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
