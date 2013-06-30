LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := game_shared

LOCAL_MODULE_FILENAME := libgame

LOCAL_SRC_FILES := ./main.cpp \
                   ../../Classes/AppDelegate.cpp \
                   ../../Classes/ParticleViewScene.cpp \
                   ../../Classes/Utils/Log.cpp
                   
LOCAL_C_INCLUDES := $(LOCAL_PATH)/../../Classes                   

LOCAL_WHOLE_STATIC_LIBRARIES := cocos2dx_static cocosdenshion_static cocos_extension_static
            
include $(BUILD_SHARED_LIBRARY)

$(call import-add-path, /home/pharspher/SDK/Cocos2d-x/cocos2d-2.1rc0-x-2.1.3) \
$(call import-add-path,/home/pharspher/SDK/Cocos2d-x/cocos2d-2.1rc0-x-2.1.3/cocos2dx/platform/third_party/android/prebuilt) \
$(call import-module,CocosDenshion/android) \
$(call import-module,cocos2dx) \
$(call import-module,extensions)
