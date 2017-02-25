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

#include <jni.h>

#include "../src/utils.h"
#include <stdio.h>


void CheckArgument(JNIEnv *env, int expression, const char *error_message) {
        if (expression != 1) {
                ThrowNew(env, ILLEGAL_ARGUMENT_EXCEPTION, error_message);
        }
}

void CheckState(JNIEnv *env, int expression, const char *error_message) {
        if (expression != 1) {
                ThrowNew(env, ILLEGAL_STATE_EXCEPTION, error_message);
        }
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
 * Signature: (ZLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_ardikars_jxnet_util_Preconditions_CheckArgument
  (JNIEnv *env, jclass jclass, jboolean jexpression, jstring jerror_message) {
	const char *error_message = (*env)->GetStringUTFChars(env, jerror_message, 0);
	CheckArgument(env, jexpression, error_message);
  	(*env)->ReleaseStringUTFChars(env, jerror_message, error_message);
}

/*
 * Class:     com_ardikars_jxnet_util_Preconditions
 * Method:    CheckState
 * Signature: (ZLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_ardikars_jxnet_util_Preconditions_CheckState
  (JNIEnv *env, jclass jclass, jboolean jexpression, jstring jerror_message) {
	const char *error_message = (*env)->GetStringUTFChars(env, jerror_message, 0);
        CheckState(env, jexpression, error_message);
        (*env)->ReleaseStringUTFChars(env, jerror_message, error_message);
}

/*
 * Class:     com_ardikars_jxnet_util_Preconditions
 * Method:    CheckNotNull
 * Signature: (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_com_ardikars_jxnet_util_Preconditions_CheckNotNull
  (JNIEnv *env, jclass jclass, jobject jobj, jstring jerror_message) {
	const char *error_message = (*env)->GetStringUTFChars(env, jerror_message, 0);
        jobj = CheckNotNull(env, jobj, error_message);
        (*env)->ReleaseStringUTFChars(env, jerror_message, error_message);
	return jobj;
}

