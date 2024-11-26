#include <jni.h>
#include <stdbool.h>

JNIEXPORT jboolean JNICALL
Java_com_example_apppetcat_AddCatActivity_validateCatAge(JNIEnv *env, jobject instance, jint age) {
    if (age >= 0 && age <= 30) {
        return JNI_TRUE; // Возраст валиден
    } else {
        return JNI_FALSE; // Возраст не валиден
    }
}
