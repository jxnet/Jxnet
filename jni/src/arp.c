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

#ifndef _Included_com_ardikars_jxnet_Arp
#define _Included_com_ardikars_jxnet_Arp
#ifdef __cplusplus
extern "C" {
#endif

#include <dnet.h>
#include <string.h> /* memset */
#include <unistd.h> /* close */

#include "../include/jxnet/com_ardikars_jxnet_Arp.h"
#include "ids.h"
#include "utils.h"
#include "preconditions.h"

/*
 * Class:     com_ardikars_jxnet_Arp
 * Method:    ArpOpen
 * Signature: ()Lcom/ardikars/jxnet/Arp;
 */
JNIEXPORT jobject JNICALL Java_com_ardikars_jxnet_Arp_ArpOpen
  (JNIEnv *env, jclass jclazz) {
	arp_t *arp;
	if ((arp = arp_open()) == NULL) {
		return NULL;
	}
	return SetArp(env, arp); 
  }

/*
 * Class:     com_ardikars_jxnet_Arp
 * Method:    ArpLoop
 * Signature: (Lcom/ardikars/jxnet/Arp;Lcom/ardikars/jxnet/ArpHandler;Ljava/lang/Object;)I
 */
JNIEXPORT jint JNICALL Java_com_ardikars_jxnet_Arp_ArpLoop
  (JNIEnv *env, jclass jclazz, jobject jarp, jobject jcallback, jobject jarg) {
	
	if (CheckNotNull(env, jarp, "") == NULL) return -1;
	if (CheckNotNull(env, jcallback, "") == NULL) return -1;

	SetArpEntryIDs(env);
	SetAddrIDs(env);
 	
 	arp_t *arp = GetArp(env, jarp); // Exception already thrown
 	
 	if(arp == NULL) {
		ThrowNew(env, ARP_CLOSE_EXCEPTION, NULL);
 		return -1;
 	}
	
	arp_user_data_t user_data;
 	memset(&user_data, 0, sizeof(user_data));
 	user_data.env = env;
 	user_data.callback = jcallback;
 	user_data.user = jarg;
 	user_data.ArpHandlerClass = (*env)->GetObjectClass(env, jcallback);
 	user_data.ArpHandlerNextArpEntryMID = (*env)->GetMethodID(env,
			user_data.ArpHandlerClass,
			"nextArpEntry", "(Lcom/ardikars/jxnet/ArpEntry;Ljava/lang/Object;)I");
			
  	return arp_loop(arp, arp_callback, (void *) &user_data);
  }

/*
 * Class:     com_ardikars_jxnet_Arp
 * Method:    ArpAdd
 * Signature: (Lcom/ardikars/jxnet/Arp;Lcom/ardikars/jxnet/ArpEntry;)I
 */
JNIEXPORT jint JNICALL Java_com_ardikars_jxnet_Arp_ArpAdd
  (JNIEnv *env, jclass jclass, jobject jarp, jobject jarp_entry) {

	if (CheckNotNull(env, jarp, "") == NULL) return  -1;
	if (CheckNotNull(env, jarp_entry, "") == NULL) return -1;

	SetArpEntryIDs(env);
	SetAddrIDs(env);
	
	arp_t *arp = GetArp(env, jarp); // Exception already thrown
 	
 	if(arp == NULL) {
		ThrowNew(env, ARP_CLOSE_EXCEPTION, NULL);
 		return -1;
 	}
 	 
    jobject arp_pa = (*env)->GetObjectField(env, jarp_entry, ArpEntryArpPaFID);
    
    if (arp_pa == NULL) {
        ThrowNew(env, NULL_PTR_EXCEPTION, "ArpEntry.arp_pa is null.");
        return -1;
    }
    
    jobject arp_ha = (*env)->GetObjectField(env, jarp_entry, ArpEntryArpHaFID);
    
    if (arp_ha == NULL) {
        ThrowNew(env, NULL_PTR_EXCEPTION, "ArpEntry.arp_ha is null..");
        return -1;
    }
    
    struct arp_entry entry;
    jstring pa = (*env)->CallObjectMethod(env, arp_pa, AddrGetStringAddressMID);
    
    if (pa == NULL) {
        ThrowNew(env, NULL_PTR_EXCEPTION, "ArpEntry.arp_pa.data is null.");
        return -1;
    }
    
    jstring ha = (*env)->CallObjectMethod(env, arp_ha, AddrGetStringAddressMID);
    
    if (ha == NULL) {
        ThrowNew(env, NULL_PTR_EXCEPTION, "ArpEntry.arp_ha.data is null.");
        return -1;
    }
    
    const char *str_pa = (*env)->GetStringUTFChars(env, pa, 0);
    const char *str_ha = (*env)->GetStringUTFChars(env, ha, 0);
 
    if (addr_pton(str_pa, &entry.arp_pa) < 0 || addr_pton(str_ha, &entry.arp_ha) < 0) {
        (*env)->ReleaseStringUTFChars(env, pa, str_pa);
        (*env)->ReleaseStringUTFChars(env, ha, str_ha);
        (*env)->DeleteLocalRef(env, arp_pa);
        (*env)->DeleteLocalRef(env, arp_ha);
        (*env)->DeleteLocalRef(env, pa);
        (*env)->DeleteLocalRef(env, ha);
        return -1;
    }
 
    (*env)->ReleaseStringUTFChars(env, pa, str_pa);
    (*env)->ReleaseStringUTFChars(env, ha, str_ha);
    (*env)->DeleteLocalRef(env, arp_pa);
    (*env)->DeleteLocalRef(env, arp_ha);
    (*env)->DeleteLocalRef(env, pa);
    (*env)->DeleteLocalRef(env, ha);

    return arp_add(arp, &entry);
  }

/*
 * Class:     com_ardikars_jxnet_Arp
 * Method:    ArpDelete
 * Signature: (Lcom/ardikars/jxnet/Arp;Lcom/ardikars/jxnet/ArpEntry;)I
 */
JNIEXPORT jint JNICALL Java_com_ardikars_jxnet_Arp_ArpDelete
  (JNIEnv *env, jclass jclass, jobject jarp, jobject jarp_entry) {
	  
	if (CheckNotNull(env, jarp, "") == NULL) return -1;
	if (CheckNotNull(env, jarp_entry, "") == NULL) return -1;    

	SetArpEntryIDs(env);
	SetAddrIDs(env);
    
    arp_t *arp = GetArp(env, jarp); // Exception already thrown
 	
 	if(arp == NULL) {
		ThrowNew(env, ARP_CLOSE_EXCEPTION, NULL);
 		return -1;
 	} 
    
    jobject arp_pa = (*env)->GetObjectField(env, jarp_entry, ArpEntryArpPaFID);
    
    if (arp_pa == NULL) {
        ThrowNew(env, NULL_PTR_EXCEPTION, "ArpEntry.arp_pa is null.");
        return -1;
    }
    
    struct arp_entry entry;
    jstring pa = (*env)->CallObjectMethod(env, arp_pa, AddrGetStringAddressMID);
    
    if (pa == NULL) {
        ThrowNew(env, NULL_PTR_EXCEPTION, "ArpEntry.arp_pa.data is null.");
        return -1;
    }
    
    const char *str_pa = (*env)->GetStringUTFChars(env, pa, 0);
    
    if (addr_pton(str_pa, &entry.arp_pa) < 0) {
        (*env)->ReleaseStringUTFChars(env, pa, str_pa);
        (*env)->DeleteLocalRef(env, arp_pa);
        (*env)->DeleteLocalRef(env, pa);
        return -1;
    }
    
    (*env)->ReleaseStringUTFChars(env, pa, str_pa);
    (*env)->DeleteLocalRef(env, arp_pa);
    (*env)->DeleteLocalRef(env, pa);
    
    return arp_delete(arp, &entry);
  }

/*
 * Class:     com_ardikars_jxnet_Arp
 * Method:    ArpGet
 * Signature: (Lcom/ardikars/jxnet/Arp;Lcom/ardikars/jxnet/ArpEntry;)I
 */
JNIEXPORT jint JNICALL Java_com_ardikars_jxnet_Arp_ArpGet
  (JNIEnv *env, jclass jclass, jobject jarp, jobject jarp_entry) {
	
	if (CheckNotNull(env, jarp, "") == NULL) return -1;
	if (CheckNotNull(env, jarp_entry, "") == NULL) return -1;

	SetArpEntryIDs(env);
	SetAddrIDs(env);
    
    arp_t *arp = GetArp(env, jarp); // Exception already thrown
 	
 	if(arp == NULL) {
		ThrowNew(env, ARP_CLOSE_EXCEPTION, NULL);
 		return -1;
 	} 
    
    jobject arp_pa = (*env)->GetObjectField(env, jarp_entry, ArpEntryArpPaFID);
    
    if (arp_pa == NULL) {
        ThrowNew(env, NULL_PTR_EXCEPTION, "ArpEntry.arp_pa is null.");
        return -1;
    }
    
    struct arp_entry entry;
    jstring pa = (*env)->CallObjectMethod(env, arp_pa, AddrGetStringAddressMID);
    
    if (pa == NULL) {
        ThrowNew(env, NULL_PTR_EXCEPTION, "ArpEntry.arp_pa.data is null.");
        return -1;
    }
    
    const char *str_pa = (*env)->GetStringUTFChars(env, pa, 0);
    
    if (addr_pton(str_pa, &entry.arp_pa) < 0) {
		(*env)->ReleaseStringUTFChars(env, pa, str_pa);     
        return -1;
    }
    
    int r = arp_get(arp, &entry);
    jobject arp_ha;
    jbyteArray jb = (*env)->NewByteArray(env, 4);
    
    (*env)->SetByteArrayRegion(env, jb, 0, 4, (jbyte *) &entry.arp_pa.addr_data8);
    arp_pa = (*env)->CallStaticObjectMethod(env, AddrClass, AddrInitializeMID,
            entry.arp_pa.addr_type, entry.arp_pa.addr_bits, jb);
    
    jb = (*env)->NewByteArray(env, 6);
    (*env)->SetByteArrayRegion(env, jb, 0, 6, (jbyte *) &entry.arp_ha.addr_data8);
    
    arp_ha = (*env)->CallStaticObjectMethod(env, AddrClass, AddrInitializeMID,
            entry.arp_ha.addr_type, entry.arp_ha.addr_bits, jb);
    
    (*env)->SetObjectField(env, jarp_entry, ArpEntryArpPaFID, arp_pa);
    (*env)->SetObjectField(env, jarp_entry, ArpEntryArpHaFID, arp_ha);
    
    (*env)->ReleaseStringUTFChars(env, pa, str_pa);
    (*env)->DeleteLocalRef(env, arp_pa);
    (*env)->DeleteLocalRef(env, arp_ha);
    (*env)->DeleteLocalRef(env, pa);
    
    return r;
	  
  }

/*
 * Class:     com_ardikars_jxnet_Arp
 * Method:    ArpClose
 * Signature: (Lcom/ardikars/jxnet/Arp;)Lcom/ardikars/jxnet/Arp;
 */
JNIEXPORT jobject JNICALL Java_com_ardikars_jxnet_Arp_ArpClose
  (JNIEnv *env, jclass jclass, jobject jarp) {
	  
	if (CheckNotNull(env, jarp, "") == NULL) return NULL;
  	
  	arp_t *arp = GetArp(env, jarp); // Exception already thrown
	
	if (arp == NULL) {
		ThrowNew(env, ARP_CLOSE_EXCEPTION, NULL);
		return NULL;
	}
	
	arp_close(arp);
  	(*env)->SetObjectField(env, jarp, ArpAddressFID, (jlong) 0);
  	
  	return jarp;  
  }

#ifdef __cplusplus
}
#endif
#endif
