#include <jni.h>
#include <pcap.h>
#include "../jni/com_ardikars_jxpcap_util_JxpcapAddrUtils.h"
#include "jxpcap_exception.h"
#ifdef WIN32

#else
#include <sys/ioctl.h>
#include <net/if.h>
#include <netinet/in.h>
#include <errno.h>
#include <string.h>
#include <arpa/inet.h>
#include <unistd.h> 
#endif

JNIEXPORT jbyteArray JNICALL Java_com_ardikars_jxpcap_util_JxpcapAddrUtils_nativeGetHwAddr
  (JNIEnv *env, jclass cls, jstring jname) {
	jbyteArray hwaddr;
	const char *name;
	name = (*env)->GetStringUTFChars(env, jname, 0);
	struct ifreq ifr;
        size_t name_len = strlen(name);
        if(name_len < sizeof(ifr.ifr_name)) {
                memcpy(ifr.ifr_name, name, name_len);
                ifr.ifr_name[name_len] = 0;
		(*env)->ReleaseStringUTFChars(env, jname, name);
        } else {
		(*env)->ReleaseStringUTFChars(env, jname, name);
		if(ThrowNewException(env, JXPCAP_EXCEPTION, "Interface name is too long.") == 0) {
			return NULL;
		}
        }
        int fd = socket(PF_INET, SOCK_DGRAM, 0);
        if(fd == -1) {
		if(ThrowNewException(env, JXPCAP_EXCEPTION, strerror(errno)) == 0) {
			return NULL;
		}
        }
        if(ioctl(fd, SIOCGIFHWADDR, &ifr) == -1) {
		if(ThrowNewException(env, JXPCAP_EXCEPTION, strerror(errno)) == 0) {
			return NULL;
		}
                close(fd);
		return NULL;
        }
        close(fd);
	hwaddr = (*env)->NewByteArray(env, (jsize) 6);
     	(*env)->SetByteArrayRegion(env, hwaddr, 0, 6, (jbyte *) ifr.ifr_hwaddr.sa_data);
	return hwaddr;
}
