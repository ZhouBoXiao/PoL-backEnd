#include "NativeLib.h"
#include "jni_lib.hpp"
#include "uuid.hpp"
#include "genProofFun.h"

JNIEXPORT jstring JNICALL Java_com_whu_jni_NativeLib_genProof
  (JNIEnv *env, jclass type , jstring position) {
    std::string proof = genProof(jstring_to_string(env, position));

    return string_to_jstring(env, proof);
}

JNIEXPORT jstring JNICALL Java_com_whu_jni_NativeLib_generateUUID
  (JNIEnv *env, jclass type) {
//    std::string uuid = generate_hex(16);
    std::string uuid = generate_hex(1);
    return string_to_jstring(env, uuid);
}