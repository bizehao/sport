#include <jni.h>
#include <iostream>
#include <string>
#include "my/utils.h"
#include <android/log.h>
#include <iomanip>
#include <nlohmann/json.hpp>
#include <leveldb/db.h>

#define LOG_TAG  "C_TAG"
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)


extern "C"
JNIEXPORT jstring JNICALL
Java_com_sport_natives_NativeApi_formatImg(JNIEnv *env, jclass type, jstring imgPath_, jint weight, jint height) {
    using json = nlohmann::json;

    const char *imgPath = env->GetStringUTFChars(imgPath_, nullptr);

    // TODO
    auto *imgData = imageData(imgPath, weight, height);

    json imgDataJson = *imgData;
    delete imgData;
    env->ReleaseStringUTFChars(imgPath_, imgPath);
    std::string jst = imgDataJson.dump();
    return env->NewStringUTF(jst.data());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_sport_natives_NativeApi_formatVideo(JNIEnv *env, jclass type, jstring videoPath_, jint weight, jint height) {
    using json = nlohmann::json;

    const char *videoPath = env->GetStringUTFChars(videoPath_, nullptr);

    // TODO
    auto *vdoData = videoData(videoPath, weight, height);

    json videoDataJson = *vdoData;
    delete vdoData;
    env->ReleaseStringUTFChars(videoPath_, videoPath);
    std::string jst = videoDataJson.dump();
    return env->NewStringUTF(jst.data());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_sport_natives_NativeApi_testLevelDB(JNIEnv *env, jclass type) {
    leveldb::DB *db;
    leveldb::Options options;
    leveldb::Status status;

    std::string key1 = "key1";
    std::string val1 = "val1", val;

    options.create_if_missing = true;
    status = leveldb::DB::Open(options, "/sdcard/sport/testdb", &db); //打开数据库
    if (!status.ok()) {
        LOGD("%s", "111111111");
        LOGD("%s", status.ToString().data());
        exit(1);
    }

    status = db->Put(leveldb::WriteOptions(), key1, val1); //放数据
    if (!status.ok()) {
        LOGD("%s", "2222222222");
        LOGD("%s", status.ToString().data());
        exit(2);
    }

    status = db->Get(leveldb::ReadOptions(), key1, &val); //取数据
    LOGD("读数据 %s", val.data());
    if (!status.ok()) {
        LOGD("%s", "3333333333");
        LOGD("%s", status.ToString().data());
        exit(3);
    }
    std::cout << "Get val: " << val << std::endl;

    status = db->Delete(leveldb::WriteOptions(), key1); //删除数据
    if (!status.ok()) {
        LOGD("%s", "44444444444");
        LOGD("%s", status.ToString().data());
        exit(4);
    }

    status = db->Get(leveldb::ReadOptions(), key1, &val); //取数据
    if (!status.ok()) {
        LOGD("%s", "5555555555");
        LOGD("%s", status.ToString().data());
        //exit(5);
    }
    LOGD("%s", "完毕");
    LOGD("%s", val.data());
    // TODO
}