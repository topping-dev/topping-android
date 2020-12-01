//
// Created by Edo on 24.09.2020.
//

#ifndef LUAANDROID_ANDROIDIO_H
#define LUAANDROID_ANDROIDIO_H

#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include <android/log.h>
#include <jni.h>
#include <errno.h>
#include <stdio.h>

#define LOG_TAG "androidio"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

#define FILE AAsset

extern jobject android_java_asset_manager;
extern JavaVM *jvm;

extern FILE* android_fopen(const char *filename, const char *mode);
extern FILE* android_freopen(const char *filename, const char *mode, FILE *stream);
extern size_t android_fread(void *ptr, size_t size, size_t count, FILE *stream);
extern size_t android_fwrite(void *ptr, size_t size, size_t count, FILE *stream);
extern int android_fseek(FILE *stream, long offset, int origin);
extern int android_ferror(FILE *stream);
extern size_t android_fclose(FILE *stream);
extern long android_ftell(FILE *stream);
extern int android_feof(FILE *stream);
extern int android_getc(FILE *stream);
extern int android_ungetc(int chr, FILE *stream);

#define fopen android_fopen
#define fread android_fread
#define fwrite android_fwrite
#define fseek android_fseek
#define ferror android_ferror
#define fclose android_fclose
#define ftell android_ftell
#define feof android_feof
#define getc android_getc
#define fgetc android_getc
#define ungetc android_ungetc
#define freopen android_freopen

#endif //LUAANDROID_ANDROIDIO_H
