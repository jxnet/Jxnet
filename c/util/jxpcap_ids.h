

#include <jni.h>
#include "../include/ifaddrs.h"
#ifdef WIN32

#else
#include <sys/socket.h>
#endif

extern jclass JxpcapClass;
extern jclass JxpcapIfClass;
extern jclass JxpcapAddrClass;
extern jclass SockAddrClass;

extern jmethodID JxpcapInitMID;
extern jmethodID JxpcapIfInitMID;
extern jmethodID JxpcapAddrInitMID;
extern jmethodID SockAddrInitMID;

extern jfieldID JxpcapPcapFID;

extern jfieldID JxpcapIfNextFID;
extern jfieldID JxpcapIfNameFID;
extern jfieldID JxpcapIfDescriptionFID;
extern jfieldID JxpcapIfAddressesFID;
extern jfieldID JxpcapIfFlagsFID;

extern jfieldID JxpcapAddrNextFID;
extern jfieldID JxpcapAddrAddrFID;
extern jfieldID JxpcapAddrNetmaskFID;
extern jfieldID JxpcapAddrBroadAddrFID;
extern jfieldID JxpcapAddrDstAddrFID;

/* Start New */
extern struct ifaddrs *ifap;
extern struct ifaddrs *ifa;
/* End New*/

extern jfieldID SockAddrSaFamilyFID;
extern jfieldID SockAddrDataFID;