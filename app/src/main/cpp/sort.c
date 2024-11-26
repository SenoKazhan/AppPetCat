#include <jni.h>
#include <string.h>
#include <stdlib.h>

// Функция сравнения строк для qsort
int compareStrings(const void *a, const void *b) {
    const char *str1 = *(const char **)a;
    const char *str2 = *(const char **)b;
    return strcmp(str1, str2); // Используем стандартную функцию сравнения строк
}

JNIEXPORT jobjectArray JNICALL
Java_com_example_apppetcat_MainActivity_sortCatNames(JNIEnv *env, jobject obj, jobjectArray names) {
    jsize length = (*env)->GetArrayLength(env, names);

    // Создаем новый массив строк для хранения имен
    const char **nativeNames = (const char **)malloc(length * sizeof(const char *));

    if (nativeNames == NULL) {
        return NULL; // Проверка на ошибку выделения памяти
    }

    // Копируем строки из Java массива в C массив
    for (jsize i = 0; i < length; i++) {
        jstring name = (jstring)(*env)->GetObjectArrayElement(env, names, i);
        const char *nativeName = (*env)->GetStringUTFChars(env, name, NULL);

        // Копируем указатель на строку в массив
        nativeNames[i] = strdup(nativeName); // Используем strdup для копирования строки

        // Освобождаем ссылку на строку, чтобы избежать утечек памяти
        (*env)->ReleaseStringUTFChars(env, name, nativeName);
    }

    // Сортируем массив строк
    qsort(nativeNames, length, sizeof(const char *), compareStrings);

    // Создаем новый Java массив строк для возвращения результата
    jobjectArray sortedNames = (*env)->NewObjectArray(env, length, (*env)->FindClass(env, "java/lang/String"), NULL);

    // Копируем отсортированные строки обратно в Java массив
    for (jsize i = 0; i < length; i++) {
        jstring sortedName = (*env)->NewStringUTF(env, nativeNames[i]);
        (*env)->SetObjectArrayElement(env, sortedNames, i, sortedName);

        // Освобождаем память, выделенную для строки в C
        free((void *)nativeNames[i]);
    }

    // Освобождаем память
    free(nativeNames);

    return sortedNames;
}
