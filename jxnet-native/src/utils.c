/**
 * Copyright (C) 2015-2018 Jxnet
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
#include <pcap.h>

#include "ids.h"
#include "utils.h"

#include <sys/time.h>

#if defined(WIN32) || defined(WIN64)
#include <winsock2.h>
#include <Ws2tcpip.h>
#include <iphlpapi.h>
#else
#include <sys/socket.h>
#include <netinet/in.h>
#endif

void SetContextIDs(JNIEnv *env) {
    SetPcapDirectionIDs(env);
    SetPcapDumperIDs(env);
    SetPcapStatIDs(env);
    SetPcapPktHdrIDs(env);
    SetByteBufferIDs(env);
}

void ThrowNew(JNIEnv *env, const char *class_name, const char *message) {
	(*env)->ThrowNew(env, (*env)->FindClass(env, class_name), message);
}

jlong PointerToJlong(void *pointer) {
	jlong address = 0;
#if defined(WIN32) || defined(WIN64)
	address = (UINT_PTR) pointer;
#else
	address = (intptr_t) pointer;
#endif
	return address;
}

void *JlongToPointer(jlong address) {
	void *pointer = NULL;
#if defined(WIN32) || defined(WIN64)
	pointer = (void *) ((UINT_PTR) address);
#else
	pointer = (void *) ((intptr_t) address);
#endif
	return pointer;
}


void SetStringBuilder(JNIEnv *env, jobject obj, const char *str) {
	if (obj == NULL) {
		ThrowNew(env, NULL_PTR_EXCEPTION, NULL);
	}
	SetStringBuilderIDs(env);
	(*env)->CallVoidMethod(env, obj, StringBuilderSetLengthMID, (jint) 0);
	(*env)->CallObjectMethod(env, obj, StringBuilderAppendMID, (*env)->NewStringUTF(env, str));
}

jobject NewObject(JNIEnv *env, const char *class_name, const char *name, const char *signature) {
	jclass class = (*env)->FindClass(env, class_name);
	return (*env)->NewObject(env, class, (*env)->GetMethodID(env, class, name, signature));
}

jbyteArray NewByteAddr(JNIEnv *env, struct sockaddr *addr) {
    if (addr == NULL) {
        return (*env)->NewByteArray(env, 0);
    }
    jbyteArray address = NULL;
    switch(addr->sa_family){
        case AF_INET:
            address = (*env)->NewByteArray(env, 4);
            (*env)->SetByteArrayRegion(env, address, 0, 4, (jbyte *) & ((struct sockaddr_in *) addr)->sin_addr);
            break;
        case AF_INET6:
            address = (*env)->NewByteArray(env, 16);
            (*env)->SetByteArrayRegion(env, address, 0, 16, (jbyte *) & ((struct sockaddr_in6 *) addr)->sin6_addr);
            break;
        default:
            address = (*env)->NewByteArray(env, sizeof(addr->sa_data));
            (*env)->SetByteArrayRegion(env, address, 0, sizeof(addr->sa_data), (jbyte *) & addr->sa_data);
    }
    return address;
}

jobject NewSockAddr(JNIEnv *env, struct sockaddr *addr) {
    jobject sockaddr = NewObject(env, "com/ardikars/jxnet/SockAddr", "<init>", "()V");
    if (addr == NULL) {
        (*env)->SetShortField(env, sockaddr, SockAddrSaFamilyFID, (jshort) 0);
        (*env)->SetObjectField(env, sockaddr, SockAddrDataFID, NewByteAddr(env, NULL));
    } else {
        (*env)->SetShortField(env, sockaddr, SockAddrSaFamilyFID, (jshort) addr->sa_family);
        (*env)->SetObjectField(env, sockaddr, SockAddrDataFID, NewByteAddr(env, addr));
    }
    return sockaddr;
}

jobject SetPcap(JNIEnv *env, pcap_t *pcap) {
	jobject obj = NewObject(env, "com/ardikars/jxnet/Pcap", "<init>", "()V");
	(*env)->SetLongField(env, obj, PcapAddressFID, PointerToJlong(pcap));
	SetContextIDs(env);
	return obj;
}

jobject SetDeadPcap(JNIEnv *env, pcap_t *pcap) {
	jobject obj = NewObject(env, "com/ardikars/jxnet/Pcap", "<init>", "()V");
	(*env)->SetLongField(env, obj, PcapAddressFID, PointerToJlong(pcap));
	(*env)->SetBooleanField(env, obj, PcapIsDeadFID, JNI_TRUE);
	SetContextIDs(env);
	return obj;
}

pcap_t *GetPcap(JNIEnv *env, jobject jpcap) {
	if (jpcap == NULL) {
		ThrowNew(env, NULL_PTR_EXCEPTION, NULL);
		return NULL;
	}
	jlong pcap = 0;
	if ((pcap = (*env)->CallLongMethod(env, jpcap, PcapGetAddressMID)) == 0) {
		ThrowNew(env, PCAP_CLOSE_EXCEPTION, NULL);
		return NULL;
	}
	return JlongToPointer(pcap);
}

pcap_t *GetNotDeadPcap(JNIEnv *env, jobject jpcap) {
	if (jpcap == NULL) {
		ThrowNew(env, NULL_PTR_EXCEPTION, NULL);
		return NULL;
	}
	if (JNI_TRUE == (*env)->CallBooleanMethod(env, jpcap, PcapIsDeadMID)) {
        ThrowNew(env, NATIVE_EXCEPTION, "Operation not supported on a PcapOpenDead():Pcap.");
        return NULL;
    }
	jlong pcap = 0;
	if ((pcap = (*env)->CallLongMethod(env, jpcap, PcapGetAddressMID)) == 0) {
		ThrowNew(env, PCAP_CLOSE_EXCEPTION, NULL);
		return NULL;
	}
	return JlongToPointer(pcap);
}

jobject SetPcapDumper(JNIEnv *env, pcap_dumper_t *pcap_dumper) {
	jobject obj = NewObject(env, "com/ardikars/jxnet/PcapDumper", "<init>", "()V");
	(*env)->SetLongField(env, obj, PcapDumperAddressFID, PointerToJlong(pcap_dumper));
	return obj;
}

pcap_dumper_t *GetPcapDumper(JNIEnv *env, jobject jpcap_dumper) {
	if (jpcap_dumper == NULL) {
		ThrowNew(env, NULL_PTR_EXCEPTION, NULL);
		return NULL;
	}
	jlong pcap_dumper = 0;
	if ((pcap_dumper = (*env)->CallLongMethod(env, jpcap_dumper, PcapDumperGetAddressMID)) == 0) {
		ThrowNew(env, PCAP_DUMPER_CLOSE_EXCEPTION, NULL);
		return NULL;
	}
	return JlongToPointer(pcap_dumper);
}

jobject SetBpfProgram(JNIEnv *env, jobject obj, struct bpf_program *fp) {
	(*env)->SetLongField(env, obj, BpfProgramAddressFID, PointerToJlong(fp));
	return obj;
}

struct bpf_program *GetBpfProgram(JNIEnv *env, jobject jbpf_program) {
	if (jbpf_program == NULL) {
		ThrowNew(env, NULL_PTR_EXCEPTION, NULL);
		return NULL;
	}
	jlong bpf_program = 0;
	if ((bpf_program = (*env)->CallLongMethod(env, jbpf_program, BpfProgramGetAddressMID)) == 0) {
		ThrowNew(env, BPF_PROGRAM_CLOSE_EXCEPTION, NULL);
		return NULL;
	}
	return JlongToPointer(bpf_program);
}

void pcap_callback(u_char *user, const struct pcap_pkthdr *pkt_header, const u_char *pkt_data) {
	pcap_user_data_t *user_data = (pcap_user_data_t *) user;
	JNIEnv *env = user_data->env;
	jobject pkt_hdr = (*env)->CallStaticObjectMethod(env, PcapPktHdrClass, PcapPktHdrNewInstance,
													 (jint) pkt_header->caplen,
													 (jint) pkt_header->len,
													 (jint) pkt_header->ts.tv_sec,
													 (jlong) pkt_header->ts.tv_usec);
	(*env)->CallNonvirtualVoidMethod(env,
									 user_data->callback,
									 user_data->PcapHandlerClass,
									 user_data->PcapHandlerNextPacketMID,
									 user_data->user,
									 pkt_hdr,
									 (*env)->NewDirectByteBuffer(env, (void *) pkt_data, (jint) pkt_header->caplen));
	(*env)->DeleteLocalRef(env, pkt_hdr);
}

void pcap_callback0(u_char *user, const struct pcap_pkthdr *pkt_header, const u_char *pkt_data) {

	pcap_user_data_t *user_data = (pcap_user_data_t *) user;
	JNIEnv *env = user_data->env;

	(*env)->CallNonvirtualVoidMethod(env,
									 user_data->callback,
									 user_data->PcapHandlerClass,
									 user_data->PcapHandlerNextPacketMID,
									 user_data->user,
									 (jint) pkt_header->caplen,
									 (jint) pkt_header->len,
									 (jint) pkt_header->ts.tv_sec,
									 (jlong) pkt_header->ts.tv_usec,
									 (jlong) &(*pkt_data));

}
