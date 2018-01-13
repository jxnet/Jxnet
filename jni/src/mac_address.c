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

#include <jni.h>

#include "../include/jxnet/com_ardikars_jxnet_MacAddress.h"
#include "ids.h"
#include "utils.h"
#include "preconditions.h"
#include <stdio.h>
#include <string.h>

#if defined(__linux__) || defined(__FreeBSD__) || defined(__APPLE__)
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/ioctl.h>
#include <net/if.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <arpa/inet.h>
#endif

#if defined(__FreeBSD__) || defined(__APPLE__)
#include <sys/sysctl.h>
#include <net/if_dl.h>
#elif defined(__linux__)
#include <string.h>
#include <unistd.h>
#include <linux/netlink.h>
#include <linux/rtnetlink.h>
#elif defined(WIN32) || defined(__CYGWIN__)
#include <winsock2.h>
#include <iphlpapi.h>
#endif

/*
 * Class:     com_ardikars_jxnet_MacAddress
 * Method:    fromNicName
 * Signature: (Ljava/lang/String;)Lcom/ardikars/jxnet/MacAddress;
 */
JNIEXPORT jobject JNICALL Java_com_ardikars_jxnet_MacAddress_fromNicName
		(JNIEnv *env, jclass jclazz, jstring jnic_name) {

	if (CheckNotNull(env, jnic_name, NULL) == NULL) return NULL;

	jbyteArray hw_addr = NULL;
	const char *buf = (*env)->GetStringUTFChars(env, jnic_name, 0);

#if defined(WIN32) || defined(__CYGWIN__)
	PIP_ADAPTER_INFO pAdapterInfo;
	PIP_ADAPTER_INFO pAdapter = NULL;
	DWORD dwRetVal = 0;
	ULONG ulOutBufLen = (ULONG) sizeof(IP_ADAPTER_INFO);
	pAdapterInfo = (IP_ADAPTER_INFO *) malloc(sizeof (IP_ADAPTER_INFO));
	if (pAdapterInfo == NULL) {
		(*env)->ReleaseStringUTFChars(env, jnic_name, buf);
		ThrowNew(env, NATIVE_EXCEPTION, "Error allocating memory needed to call GetAdaptersinfo");
		return NULL;
	}
	if (GetAdaptersInfo(pAdapterInfo, &ulOutBufLen) == ERROR_BUFFER_OVERFLOW) {
		free(pAdapterInfo);
		pAdapterInfo = (IP_ADAPTER_INFO *) malloc(ulOutBufLen);
		if (pAdapterInfo == NULL) {
			(*env)->ReleaseStringUTFChars(env, jnic_name, buf);
			ThrowNew(env, NATIVE_EXCEPTION, "Error allocating memory needed to call GetAdaptersinfo");
			return NULL;
		}
	}
	if ((dwRetVal = GetAdaptersInfo(pAdapterInfo, &ulOutBufLen)) == NO_ERROR) {
		pAdapter = pAdapterInfo;
		while (pAdapter) {
			const char *p1 = strchr(buf, '{');
			const char *p2 = strchr(pAdapter->AdapterName,  '{');
			if (p1 == NULL || p2 == NULL) {
				p1 = buf;
				p2 = pAdapter->AdapterName;
			}
			if ((strcmp(p1, p2) == 0)) {
				hw_addr = (*env)->NewByteArray(env, (jsize) pAdapter->AddressLength);
				(*env)->SetByteArrayRegion(env, hw_addr, 0 , pAdapter->AddressLength, (jbyte *) pAdapter->Address);
				break;
			}
			pAdapter = pAdapter->Next;
        }
	} else {
		(*env)->ReleaseStringUTFChars(env, jnic_name, buf);
		ThrowNew(env, DEVICE_NOT_FOUND_EXCEPTION, "GetAdaptersInfo failed");
		return NULL;
	}
	if (pAdapterInfo)
		free(pAdapterInfo);

	(*env)->ReleaseStringUTFChars(env, jnic_name, buf);
    SetMacAddressIDs(env);
    jobject obj = (*env)->CallStaticObjectMethod(env, MacAddressClass,
                    MacAddressValueOfMID, hw_addr);
    return obj;


#elif defined(__linux__)

	char mac_addr[6];
	struct ifreq ifr;
	int sd = socket(PF_INET, SOCK_DGRAM, 0);
	if (sd < 0) {
		(*env)->ReleaseStringUTFChars(env, jnic_name, buf);
		ThrowNew(env, NATIVE_EXCEPTION, "Can't create socket");
		return NULL;
	}
	memset(&ifr, 0, sizeof(ifr));
	strncpy(ifr.ifr_ifrn.ifrn_name, buf, IFNAMSIZ);
	if (ioctl(sd, SIOCGIFHWADDR, &ifr) != 0) {
		(*env)->ReleaseStringUTFChars(env, jnic_name, buf);
		ThrowNew(env, DEVICE_NOT_FOUND_EXCEPTION, "Ioctl error.");
		return NULL;
	}
	bcopy((u_char *) ifr.ifr_ifru.ifru_hwaddr.sa_data, mac_addr, 6);
	hw_addr = (*env)->NewByteArray(env, (jsize) 6);
	(*env)->SetByteArrayRegion(env, hw_addr, 0 , 6, (jbyte *) mac_addr);
	close(sd);

	(*env)->ReleaseStringUTFChars(env, jnic_name, buf);
	SetMacAddressIDs(env);
	jobject obj = (*env)->CallStaticObjectMethod(env, MacAddressClass,
												 MacAddressValueOfMID, hw_addr);
	return obj;

#elif defined(__FreeBSD__) || defined(__APPLE__)

	int mib[6];
	size_t len;
	char *mac_buf;
	unsigned char *ptr;
	struct if_msghdr *ifm;
	struct sockaddr_dl *sdl;
	mib[0] = CTL_NET;
	mib[1] = AF_ROUTE;
	mib[2] = 0;
	mib[3] = AF_LINK;
	mib[4] = NET_RT_IFLIST;
	if ((mib[5] = if_nametoindex(buf)) == 0) {
	    ThrowNew(env, DEVICE_NOT_FOUND_EXCEPTION, "Device index not found.");
		return NULL;
	}
	if (sysctl(mib, 6, NULL, &len, NULL, 0) < 0) {
	    ThrowNew(env, NATIVE_EXCEPTION, "Sysctl error.");
		return NULL;
	}
	if ((mac_buf = malloc(len)) == NULL) {
	    ThrowNew(env, NATIVE_EXCEPTION, "Error allocating memory.");
		return NULL;
	}
	if (sysctl(mib, 6, mac_buf, &len, NULL, 0) < 0) {
	    ThrowNew(env, NATIVE_EXCEPTION, "Sysctl error.");
		return NULL;
	}
	ifm = (struct if_msghdr *) mac_buf;
	sdl = (struct sockaddr_dl *) (ifm + 1);
	free(mac_buf);
	ptr = (unsigned char *) LLADDR(sdl);
	hw_addr = (*env)->NewByteArray(env, (jsize) 6);
	(*env)->SetByteArrayRegion(env, hw_addr, 0, 6, (jbyte *) ptr);

	(*env)->ReleaseStringUTFChars(env, jnic_name, buf);
    SetMacAddressIDs(env);
    jobject obj = (*env)->CallStaticObjectMethod(env, MacAddressClass,
                    MacAddressValueOfMID, hw_addr);
    return obj;
#else
	ThrowNew(env, NOT_SUPPORTED_PLATFORM_EXCEPTION, NULL);
	return NULL;
#endif
	return NULL;
}
