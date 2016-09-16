
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

#include <jni.h>
#include <pcap.h>

#define CLASS_NOT_FOUND_EXCEPTION "java/lang/ClassNotFoundException"
#define NO_SUCH_METHOD_EXCEPTION "java.lang.NoSuchMethodException"
#define NO_SUCH_FIELD_EXCEPTION "java/lang/NoSuchFieldException"
#define NULL_PTR_EXCEPTION "java/lang/NullPointerException"
#define PCAP_CLOSE_EXCEPTION "com/ardikars/jxnet/exception/PcapCloseException"
#define BPF_PROGRAM_CLOSE_EXCEPTION "com/ardikars/jxnet/exception/BpfProgramCloseException"
#define PCAP_DUMPER_CLOSE_EXCEPTION "com/ardikars/jxnet/exception/PcapDumperCloseException"
#define JXNET_EXCEPTION "com/ardikars/jxnet/exception/JxnetException"

typedef struct pcap_user_data_t {
        JNIEnv *env;
        jobject callback;
        jobject user;
        jclass PcapHandlerClass;
        jmethodID PcapHandlerNextPacketMID;
        /*jclass PcapPktHdrClass;
        jfieldID PcapPktHdrCaplenFID;
        jfieldID PcapPktHdrLenFID;
		jfieldID PcapPktHdrTvSecFID;
		jfieldID PcapPktHdrTvUsecFID;*/
} pcap_user_data_t;

jlong PointerToJlong(void *pointer);

void *JlongToPointer(jlong address);

void ThrowNew(JNIEnv *env, const char *class_name, const char *message);

void SetStringBuilder(JNIEnv *env, jobject obj, const char *str);

jobject NewObject(JNIEnv *env, jclass class, const char *name, const char *signature);

jbyteArray NewByteAddr(JNIEnv *env, struct sockaddr *addr);

jobject NewSockAddr(JNIEnv *env, struct sockaddr *addr);

jobject SetPcap(JNIEnv *env, pcap_t *pcap);

jobject SetFile(JNIEnv *env, FILE *file);

pcap_t *GetPcap(JNIEnv *env, jobject jpcap);

FILE *GetFile(JNIEnv *env, jobject jf);

jobject SetPcapDumper(JNIEnv *env, pcap_dumper_t *pcap_dumper);

pcap_dumper_t *GetPcapDumper(JNIEnv *env, jobject jpcap_dumper);

struct bpf_program *GetBpfProgram(JNIEnv *env, jobject jbpf_program);

void pcap_callback(u_char *user, const struct pcap_pkthdr *pkt_header, const u_char *pkt_data);
