#include <jni.h>

jmethodID GetJavaMethodID(JNIEnv *env, jobject obj, const char *field_name, const char *sig);

jobject NewJavaObject(JNIEnv *env, jclass class, const char *field_name, const char *sig);