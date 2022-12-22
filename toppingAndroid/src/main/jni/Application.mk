LOCAL_PATH := $(call my-dir)

APP_MODULES := lualib
APP_PLATFORM := android-18
APP_STL := c++_shared
APP_OPTIM := debug
APP_CFLAGS += -U_FORTIFY_SOURCE -D_FORTIFY_SOURCE=0
