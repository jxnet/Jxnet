
/*
 * Author       : Ardika Rommy Sanjaya
 * Website      : http://ardikars.com
 * Contact      : contact@ardikars.com
 * License      : Lesser GNU Public License Version 3
 */

#include <pcap.h>
#include <stdlib.h>

#include "../src/ids.h"
#include "../src/utils.h"
#include "../include/jxnet/com_ardikars_jxnet_BpfProgram.h"

JNIEXPORT void JNICALL Java_com_ardikars_jxnet_BpfProgram_initBpfProgram
  (JNIEnv *env, jobject jobj) {
        if(jobj == NULL) {
                ThrowNew(env, NULL_PTR_EXCEPTION, "");
                return;
        }
        SetPointerIDs(env);
        SetBpfProgramIDs(env);
        jobject pointer = NewObject(env, PointerClass, "<init>", "()V");
        struct bpf_program *fp = (struct bpf_program *) malloc(sizeof(struct bpf_program));
        if(fp == NULL) {
                ThrowNew(env, BPF_PROGRAM_CLOSE_EXCEPTION, "BpfProgram out of memory");
                return;
        }
        fp->bf_insns = NULL;
        fp->bf_len = 0;
        (*env)->SetLongField(env, pointer, PointerAddressFID, PointerToJlong(fp));
        (*env)->SetObjectField(env, jobj, BpfProgramPointerFID, pointer);
        (*env)->DeleteLocalRef(env, pointer);
  }

