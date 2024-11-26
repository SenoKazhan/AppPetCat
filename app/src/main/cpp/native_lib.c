#include <jni.h>
#include <string.h>

JNIEXPORT jstring JNICALL
Java_com_example_apppetcat_MainActivity_getMessageFromNative(JNIEnv *env, jobject obj) {
    return (*env)->NewStringUTF(env, "Привет из C!");
}
