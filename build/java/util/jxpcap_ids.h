

#include <jni.h>

extern jclass JxpcapClass;
extern jclass JxpcapIfClass;
extern jclass JxpcapAddrClass;
extern jclass JxpcapNaddrClass; /* New */
extern jclass SockAddrClass;

extern jmethodID JxpcapInitMID;
extern jmethodID JxpcapIfInitMID;
extern jmethodID JxpcapAddrInitMID;
extern jmethodID JxpcapNaddrInitMID; /* New */
extern jmethodID SockAddrInitMID;

extern jfieldID JxpcapIfNextFID;
extern jfieldID JxpcapIfNameFID;
extern jfieldID JxpcapIfDescriptionFID;
extern jfieldID JxpcapIfNaddressesFID; /* New */
extern jfieldID JxpcapIfAddressesFID;
extern jfieldID JxpcapIfFlagsFID;

extern jfieldID JxpcapAddrNextFID;
extern jfieldID JxpcapAddrAddrFID;
extern jfieldID JxpcapAddrNetmaskFID;
extern jfieldID JxpcapAddrBroadAddrFID;
extern jfieldID JxpcapAddrDstAddrFID;

/* Start New */
extern jfieldID JxpcapNaddrAddrFID;
extern jfieldID JxpcapNaddrNetmaskFID;
extern jfieldID JxpcapNaddrBroadAddrFID;
extern jfieldID JxpcapNaddrDstAddrFID;
/* End New */

extern jfieldID SockAddrSaFamilyFID;
extern jfieldID SockAddrDataFID;