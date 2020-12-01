LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := lualib
LOCAL_SRC_FILES := jnlua.c androidio.c lapi.c lauxlib.c lbaselib.c lcode.c ldblib.c ldebug.c ldo.c ldump.c lfunc.c lgc.c linit.c liolib.c llex.c lmathlib.c lmem.c loadlib.c lobject.c lopcodes.c loslib.c lparser.c lstate.c lstring.c lstrlib.c ltable.c ltablib.c ltm.c lundump.c lvm.c lzio.c print.c

LOCAL_LDLIBS := -llog -landroid

# POSIX as we're on linux, and compatibility mode in case you'll be running scripts written for LUA <5.2
LOCAL_CFLAGS += -DLUA_USE_POSIX -DLUA_COMPAT_ALL

include $(BUILD_SHARED_LIBRARY)
