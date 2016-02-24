

#include <jni.h>
#include "../include/ifaddrs.h"
#ifdef WIN32

#else
#include <sys/socket.h>
#endif

/* Start -> New */
extern jclass StringBuilderClass;
extern jclass ListClass;
/* End */

extern jclass JxpcapClass;
extern jclass JxpcapIfClass;
extern jclass JxpcapAddrClass;
extern jclass SockAddrClass;

extern jmethodID JxpcapInitMID;
extern jmethodID JxpcapIfInitMID;
extern jmethodID JxpcapAddrInitMID;
extern jmethodID SockAddrInitMID;

/* Start -> New*/
extern jmethodID StringBuilderAppendMID;
extern jmethodID StringBuilderSetLengthMID;

extern jmethodID ListAddMID;
/* End */

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

extern jfieldID SockAddrSaFamilyFID;
extern jfieldID SockAddrDataFID;
