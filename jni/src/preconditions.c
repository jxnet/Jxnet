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

#ifndef _Included_com_ardikars_jxnet_util_Preconditions
#define _Included_com_ardikars_jxnet_util_Preconditions
#ifdef __cplusplus
extern "C" {
#endif

#include <jni.h>

#include "utils.h"
#include <stdio.h>


int CheckArgument(JNIEnv *env, int expression, const char *error_message) {
        if (!expression) {
                ThrowNew(env, ILLEGAL_ARGUMENT_EXCEPTION, error_message);
		return !expression;
        }
	return expression;
}

int CheckState(JNIEnv *env, int expression, const char *error_message) {
        if (!expression) {
                ThrowNew(env, ILLEGAL_STATE_EXCEPTION, error_message);
		return !expression;
        }
	return expression;
}

jobject CheckNotNull(JNIEnv *env, jobject jobj, const char *error_message) {
        if (jobj == NULL) {
                ThrowNew(env, NULL_PTR_EXCEPTION, error_message);
                return NULL;
        }
        return jobj;
}

/*
 * Class:     com_ardikars_jxnet_util_Preconditions
 * Method:    CheckArgument
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_com_ardikars_jxnet_util_Preconditions_CheckArgument__Z
  (JNIEnv *env, jclass jclazz, jboolean jexpression) {
	CheckArgument(env, jexpression, NULL);
  }

/*
 * Class:     com_ardikars_jxnet_util_Preconditions
 * Method:    CheckArgument
 * Signature: (ZLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_ardikars_jxnet_util_Preconditions_CheckArgument__ZLjava_lang_String_2
  (JNIEnv *env, jclass jclazz, jboolean jexpression, jstring jerror_message) {
	const char *error_message = (*env)->GetStringUTFChars(env, jerror_message, 0);
	CheckArgument(env, jexpression, error_message);
  	(*env)->ReleaseStringUTFChars(env, jerror_message, error_message);  
  }

/*
 * Class:     com_ardikars_jxnet_util_Preconditions
 * Method:    CheckState
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_com_ardikars_jxnet_util_Preconditions_CheckState__Z
  (JNIEnv *env, jclass jclazz, jboolean jexpression) {
	CheckState(env, jexpression, NULL);
  }

/*
 * Class:     com_ardikars_jxnet_util_Preconditions
 * Method:    CheckState
 * Signature: (ZLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_ardikars_jxnet_util_Preconditions_CheckState__ZLjava_lang_String_2
  (JNIEnv *env, jclass jclazz, jboolean jexpression, jstring jerror_message) {
	const char *error_message = (*env)->GetStringUTFChars(env, jerror_message, 0);
	CheckState(env, jexpression, error_message);
	(*env)->ReleaseStringUTFChars(env, jerror_message, error_message);  
  }

/*
 * Class:     com_ardikars_jxnet_util_Preconditions
 * Method:    CheckNotNull
 * Signature: (Ljava/lang/Object;)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_com_ardikars_jxnet_util_Preconditions_CheckNotNull__Ljava_lang_Object_2
  (JNIEnv *env, jclass jclazz, jobject jobj) {
	jobj = CheckNotNull(env, jobj, NULL);
	return jobj;    
  }

/*
 * Class:     com_ardikars_jxnet_util_Preconditions
 * Method:    CheckNotNull
 * Signature: (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_com_ardikars_jxnet_util_Preconditions_CheckNotNull__Ljava_lang_Object_2Ljava_lang_String_2
  (JNIEnv *env, jclass jclazz, jobject jobj, jstring jerror_message) {
	const char *error_message = (*env)->GetStringUTFChars(env, jerror_message, 0);
	jobj = CheckNotNull(env, jobj, error_message);
	(*env)->ReleaseStringUTFChars(env, jerror_message, error_message);
	return jobj;  
  }


#ifdef __cplusplus
}
#endif
#endif
