#include <jni.h>
#include <pcap.h>
#include "../jni/com_ardikars_jxpcap_util_JxpcapAddrUtils.h"
#include <stdio.h>

#ifdef WIN32

#else
#include <sys/ioctl.h>
#include <net/if.h>
#include <netinet/in.h>
#include <errno.h>
#include <string.h>
#include <arpa/inet.h>
#endif

JNIEXPORT jbyteArray JNICALL Java_com_ardikars_jxpcap_util_JxpcapAddrUtils_nativeGetHwAddr
  (JNIEnv *env, jclass cls, jstring jname) {
	jbyteArray hwaddr;
	const char *name = (*env)->GetStringUTFChars(env, jname, 0);
	struct ifreq ifr;
	printf("%s", name);
        size_t name_len = strlen(name);
        if(name_len < sizeof(ifr.ifr_name)) {
                memcpy(ifr.ifr_name, name, name_len);
                ifr.ifr_name[name_len] = 0;
		(*env)->ReleaseStringUTFChars(env, jname, name);
        } else {
		(*env)->ReleaseStringUTFChars(env, jname, name);
                puts("Interface name is too long.");
		return NULL;
        }
        int fd = socket(AF_INET, SOCK_DGRAM, 0);
        if(fd == -1) {
                puts(strerror(errno));
		return NULL;
        }
        if(ioctl(fd, SIOCGIFADDR, &ifr) == -1) {
                close(fd);
                puts(strerror(errno));
		return NULL;
        }
        close(fd);
	hwaddr = (*env)->NewByteArray(env, (jsize) 6);
     	(*env)->SetByteArrayRegion(env, hwaddr, 0, 6, (jbyte *) ifr.ifr_hwaddr.sa_data);
	return hwaddr;
}
