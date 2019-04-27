#include <jni.h>
#include "utils.h"

extern "C" {
JNIEXPORT jstring JNICALL Java_com_sport_natives_DataHandle_say(JNIEnv *env, jclass type) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
}
