#include <jni.h>
#include <pcap.h>
#include "../jni/com_ardikars_jxpcap_util_JxpcapAddrUtils.h"
#include "jxpcap_exception.h"
#ifdef WIN32

#else
#include <sys/ioctl.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <net/if.h>
#include <net/if_arp.h>
#include <netinet/in.h>
#include <linux/netlink.h>
#include <linux/rtnetlink.h>
#include <arpa/inet.h>
#include <errno.h>
#include <string.h>
#include <unistd.h>
#include <netdb.h>
#include <stdlib.h>
#endif


#ifdef WIN32

#else
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

JNIEXPORT jbyteArray JNICALL Java_com_ardikars_jxpcap_util_JxpcapAddrUtils_nativeGetHwAddr
  (JNIEnv *env, jclass cls, jstring jname) {
	jbyteArray hwaddr;
	const char *name;
	struct ifreq ifr;
	size_t name_len;
	int fd;
	name = (*env)->GetStringUTFChars(env, jname, 0);
	name_len = strlen(name);
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
    fd = socket(PF_INET, SOCK_DGRAM, 0);
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

JNIEXPORT jbyteArray JNICALL Java_com_ardikars_jxpcap_util_JxpcapAddrUtils_nativeGetGwAddr
  (JNIEnv *env, jclass cls, jstring name) {
  
 }
