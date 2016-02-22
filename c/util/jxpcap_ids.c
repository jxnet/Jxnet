
#include <jni.h>

#include "jxpcap_ids.h"
#include "jxpcap_utils.h"
#include "jxpcap_exception.h"
#include "../jni/com_ardikars_jxpcap_JxpcapAddr.h"
#include "../jni/com_ardikars_jxpcap_JxpcapIf.h"
#include "../jni/com_ardikars_jxpcap_SockAddr.h"

#include "../include/ifaddrs.h"
#ifdef WIN32

#else
#include <sys/socket.h>
#endif

jclass JxpcapClass;
jclass JxpcapIfClass;
jclass JxpcapAddrClass;
jclass SockAddrClass;

jmethodID JxpcapInitMID;
jmethodID JxpcapIfInitMID;
jmethodID JxpcapAddrInitMID;
jmethodID SockAddrInitMID;

jfieldID JxpcapIfNextFID;
jfieldID JxpcapIfNameFID;
jfieldID JxpcapIfDescriptionFID;
jfieldID JxpcapIfAddressesFID;
jfieldID JxpcapIfNaddressesFID;
jfieldID JxpcapIfFlagsFID;

jfieldID JxpcapAddrNextFID;
jfieldID JxpcapAddrAddrFID;
jfieldID JxpcapAddrNetmaskFID;
jfieldID JxpcapAddrBroadAddrFID;
jfieldID JxpcapAddrDstAddrFID;

/* Start New */
struct ifaddrs *ifap;
struct ifaddrs *ifa;
/* End New*/

jfieldID SockAddrSaFamilyFID;
jfieldID SockAddrDataFID;

JNIEXPORT void JNICALL Java_com_ardikars_jxpcap_JxpcapIf_initIDs
  (JNIEnv *env, jclass cls) {
    puts("InitIDs (JxpcapIf)");
  	jclass jcls;
  	
  	jcls = (*env)->FindClass(env, "com/ardikars/jxpcap/JxpcapIf");
  	
  	JxpcapIfClass = jcls;
  	
  	if(JxpcapIfClass == NULL) {
  		if((ThrowNewException(env, CLASS_NOT_FOUND_EXCEPTION,
  			"Unable to initialize class com.ardikars.jxpcap.JxpcapIf")) == 0) {
  			return;
  		}
  	}
    if((JxpcapIfInitMID = (*env)->GetMethodID(env, jcls, "<init>", "()V")) == NULL) {
  		if(ThrowNewException(env, NO_SUCH_METHOD_EXCEPTION, 
  			"Unable to initialize constructor com.ardikars.jxpcap.JxpcapIf()") == 0) {
  			return;
  		}
  	}
  	if((JxpcapIfNextFID = (*env)->GetFieldID(env, jcls, "next", "Lcom/ardikars/jxpcap/JxpcapIf;")) == NULL) {
  		if(ThrowNewException(env, NO_SUCH_FIELD_EXCEPTION,
  			"Unable to initialize field JxpcapIf.next:JxpcapIf") == 0) {
  			return;
  		}
  	}
  	if((JxpcapIfNameFID = (*env)->GetFieldID(env, jcls, "name", "Ljava/lang/String;")) == NULL) {
  		if(ThrowNewException(env, NO_SUCH_FIELD_EXCEPTION,
  			"Unable to initialize field JxpcapIf.name:String") == 0) {
  			return;
  		}
  	}
  	
  	if((JxpcapIfDescriptionFID = (*env)->GetFieldID(env, jcls, "description", "Ljava/lang/String;")) == NULL) {
  		if(ThrowNewException(env, NO_SUCH_FIELD_EXCEPTION,
  			"Unable to initialize field JxpcapIf.description:String") == 0) {
  			return;
  		}
  	}
  	
  	if((JxpcapIfAddressesFID = (*env)->GetFieldID(env, jcls, "addresses", "Ljava/util/List;")) == NULL) {
  		if(ThrowNewException(env, NO_SUCH_FIELD_EXCEPTION,
  			"Unable to initialize field JxpcapIf.addresses:JxpcapAddr") == 0) {
  			return;
  		}
  	}
  	 	
  	if((JxpcapIfFlagsFID = (*env)->GetFieldID(env, jcls, "flags", "I")) == NULL) {
  		if((ThrowNewException(env, NO_SUCH_FIELD_EXCEPTION,
  			"Unable to initialize field JxpcapIf.flags:int")) == 0) {
  			return;
  		}
  	}
}

JNIEXPORT void JNICALL Java_com_ardikars_jxpcap_JxpcapAddr_initIDs
  (JNIEnv *env, jclass cls) {
 	puts("InitIDs (JxpcapAddr)");
 	jclass jcls;
  	
  	jcls = (*env)->FindClass(env, "com/ardikars/jxpcap/JxpcapAddr");
  
	JxpcapAddrClass = jcls;
  
  	if(JxpcapAddrClass == NULL) {
  		if((ThrowNewException(env, CLASS_NOT_FOUND_EXCEPTION,
  			"Unable to initialize class com.ardikars.jxpcap.JxpcapAddr")) == 0) {
  			return;
  		}
  	}
  	if((JxpcapAddrInitMID = (*env)->GetMethodID(env, jcls, "<init>", "()V")) == NULL) {
  		if(ThrowNewException(env, NO_SUCH_METHOD_EXCEPTION, 
  			"Unable to initialize constructor com.ardikars.jxpcap.JxpcapAddr()") == 0) {
  			return;
  		}
  	}
  	if((JxpcapAddrAddrFID = (*env)->GetFieldID(env, jcls, "addr", "Lcom/ardikars/jxpcap/SockAddr;")) == NULL) {
  		if(ThrowNewException(env, NO_SUCH_FIELD_EXCEPTION,
  			"Unable to initialize field JxpcapAddr.addr:SockAddr") == 0) {
  			return;
  		}
  	}
  	if((JxpcapAddrNetmaskFID = (*env)->GetFieldID(env, jcls, "netmask", "Lcom/ardikars/jxpcap/SockAddr;")) == NULL) {
  		if(ThrowNewException(env, NO_SUCH_FIELD_EXCEPTION,
  			"Unable to initialize field JxpcapAddr.netmask:SockAddr") == 0) {
  			return;
  		}
  	}
  	if((JxpcapAddrBroadAddrFID = (*env)->GetFieldID(env, jcls, "broadaddr", "Lcom/ardikars/jxpcap/SockAddr;")) == NULL) {
  		if(ThrowNewException(env, NO_SUCH_FIELD_EXCEPTION,
  			"Unable to initialize field JxpcapAddr.broadaddr:SockAddr") == 0) {
  			return;
  		}
  	}
  	if((JxpcapAddrDstAddrFID = (*env)->GetFieldID(env, jcls, "dstaddr", "Lcom/ardikars/jxpcap/SockAddr;")) == NULL) {
  		if(ThrowNewException(env, NO_SUCH_FIELD_EXCEPTION,
  			"Unable to initialize field JxpcapAddr.dstaddr:SockAddr") == 0) {
  			return;
  		}
  	}
 }
 
 
 JNIEXPORT void JNICALL Java_com_ardikars_jxpcap_SockAddr_initIDs
  	(JNIEnv *env, jclass cls) {
  	puts("InitIDs (SockAddr)");
 	jclass jcls;
  	
  	jcls = (*env)->FindClass(env, "com/ardikars/jxpcap/SockAddr");
  
	SockAddrClass = jcls;
  
  	if(SockAddrClass == NULL) {
  		if((ThrowNewException(env, CLASS_NOT_FOUND_EXCEPTION,
  			"Unable to initialize class com.ardikars.jxpcap.SockAddr")) == 0) {
  			return;
  		}
  	}
  	if((SockAddrInitMID = (*env)->GetMethodID(env, jcls, "<init>", "()V")) == NULL) {
  		if(ThrowNewException(env, NO_SUCH_METHOD_EXCEPTION, 
  			"Unable to initialize constructor com.ardikars.jxpcap.SockAddr()") == 0) {
  			return;
  		}
  	}
  	if((SockAddrSaFamilyFID = (*env)->GetFieldID(env, jcls, "sa_family", "S")) == NULL) {
  		if(ThrowNewException(env, NO_SUCH_FIELD_EXCEPTION,
  			"Unable to initialize field JxpcapIf.sa_family:short") == 0) {
  			return;
  		}
  	}
  	if((SockAddrDataFID = (*env)->GetFieldID(env, jcls, "data", "[B")) == NULL) {
  		if(ThrowNewException(env, NO_SUCH_FIELD_EXCEPTION,
  			"Unable to initialize field JxpcapIf.data:byte[]") == 0) {
  			return;
  		}
  	}
}