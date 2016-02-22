#include <sys/socket.h>
#include <jni.h>

jobject NewSockAddr(JNIEnv *env, struct sockaddr *address);