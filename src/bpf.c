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

