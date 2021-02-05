#include "androidio.h"
#include <stdlib.h>
#include <string.h>

jobject android_java_asset_manager = NULL;
JavaVM *jvm = NULL;

char *substring(char *str, int position, int length)
{
    char *pointer;
    int c;

    pointer = malloc(length+1);

    if (pointer == NULL)
    {
        printf("Unable to allocate memory.\n");
    }

    for (c = 0 ; c < length ; c++)
    {
        *(pointer+c) = *(str+position-1);
        str++;
    }

    *(pointer+c) = '\0';

    return pointer;
}

static int android_readun(void* cookie, char* buf, int size) {
    return AAsset_read((AAsset*)cookie, buf, size);
}

static int android_writeun(void* cookie, const char* buf, int size) {
    return EACCES; // can't provide write access to the apk
}

static fpos_t android_seekun(void* cookie, fpos_t offset, int whence) {
    return AAsset_seek((AAsset*)cookie, offset, whence);
}

static int android_closeun(void* cookie) {
    AAsset_close((AAsset*)cookie);
    return 0;
}

extern FILE* android_fopensafe(const char *filename, const char *mode)
{
    if(mode[0] == 'w') return NULL;

    JNIEnv *env;
    (*jvm)->AttachCurrentThread(jvm, &env, 0);
    AAssetManager* manager = AAssetManager_fromJava(env, android_java_asset_manager);

    AAsset* asset = AAssetManager_open(manager, filename, AASSET_MODE_RANDOM);
    if(!asset)
    {
        return NULL;
    }

    return asset;

    //return funopen(asset, android_read, android_write, android_seek, android_close);
}

extern FILE* android_fopen(const char *filename, const char *mode)
{
    if(mode[0] == 'w') return NULL;
    char *fSafe = substring(filename, 3, strlen(filename));

    JNIEnv *env;
    (*jvm)->AttachCurrentThread(jvm, &env, 0);
    AAssetManager* manager = AAssetManager_fromJava(env, android_java_asset_manager);

    AAsset* asset = AAssetManager_open(manager, fSafe, AASSET_MODE_RANDOM);
    free(fSafe);
    if(!asset)
    {
        char cpydest[2048];
#ifdef __arm__
        strcat(cpydest, "armeabi-v7a/");
        strcat(cpydest, filename);
#elif __aarch64__
        strcat(cpydest, "arm64-v8a/");
        strcat(cpydest, filename);
#elif __i386__
        strcat(cpydest, "x86/");
        strcat(cpydest, filename);
#elif __x86_64__
        strcat(cpydest, "x86_64/");
        strcat(cpydest, filename);
#endif
        LOGD("fopen trying %s", cpydest);
        FILE* ret = android_fopensafe(cpydest, mode);
        if(ret != NULL)
            LOGD("fopen found");
        memset(cpydest, 0, 2048);
        return ret;
    }

    return asset;

    //return funopen(asset, android_read, android_write, android_seek, android_close);
}

extern FILE* android_freopen(const char *filename, const char *mode, FILE *stream)
{
    if(stream)
        AAsset_close(stream);

    return android_fopen(filename, mode);
}

extern size_t android_fread(void *ptr, size_t size, size_t count, FILE *stream)
{
    size_t r = AAsset_read((AAsset*)stream, ptr, count);
    return r;
}

extern size_t android_fwrite(void *ptr, size_t size, size_t count, FILE *stream)
{
    return EACCES;
}

extern int android_fseek(FILE *stream, long offset, int origin)
{
    return AAsset_seek(stream, offset, origin);
}

extern int android_ferror(FILE *stream)
{
    if(!stream)
        return 1;
    return 0;
}

extern size_t android_fclose(FILE *stream)
{
    AAsset_close(stream);
    return 0;
}

extern long android_ftell(FILE *stream)
{
    long l = AAsset_getLength(stream);
    long c = AAsset_getRemainingLength(stream);
    return l - c;
}

extern int android_feof(FILE *stream)
{
    return AAsset_getRemainingLength(stream) > 0 ? 0 : 1;
}

extern int android_getc(FILE *stream)
{
    int ch;
    android_fread(&ch, sizeof(char), 1, stream);
    return ch;
}

extern int android_ungetc(int chr, FILE *stream)
{
    return AAsset_seek(stream, -1, SEEK_CUR);
}
