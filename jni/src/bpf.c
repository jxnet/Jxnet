/**
 * Copyright (C) 2017  Ardika Rommy Sanjaya
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

#ifndef _Included_com_ardikars_jxnet_BpfProgram
#define _Included_com_ardikars_jxnet_BpfProgram
#ifdef __cplusplus
extern "C" {
#endif

#include <pcap.h>
#include <stdlib.h>

#include "ids.h"
#include "utils.h"
#include "preconditions.h"
#include "../include/jxnet/com_ardikars_jxnet_BpfProgram.h"

/*
 * Class:     com_ardikars_jxnet_BpfProgram
 * Method:    initBpfProgram
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_ardikars_jxnet_BpfProgram_initBpfProgram
  (JNIEnv *env, jobject jobj) {
        
		if (CheckNotNull(env, jobj, "") == NULL) return;
		
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

#ifdef __cplusplus
}
#endif
#endif
