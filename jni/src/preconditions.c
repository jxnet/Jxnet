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
