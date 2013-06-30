#include "ParticleViewerScene.hpp"
#include "SimpleAudioEngine.h"

#include "platform/android/jni/JniHelper.h"
#include <jni.h>
#include <android/log.h>

#include <string>
#include <sstream>

#include "Utils/Log.hpp"

using namespace cocos2d;
using namespace CocosDenshion;

cocos2d::CCScene *pScene;
cocos2d::CCParticleSystem *pEmitter;

ParticleViewerScene* ParticleViewerScene::mLayerInstance = 0;

CCScene* ParticleViewerScene::scene()
{
    CCScene *scene = CCScene::create();

    ParticleViewerScene *layer = ParticleViewerScene::create();
    scene->addChild(layer);

    pScene = scene;
    return scene;
}

ParticleViewerScene* ParticleViewerScene::getLayerInstance() {
    return ParticleViewerScene::mLayerInstance;
}

void ParticleViewerScene::registerWithTouchDispatcher(void) {
    CCDirector::sharedDirector()->getTouchDispatcher()->addTargetedDelegate(this, 0, true);
}

bool ParticleViewerScene::init()
{
    if ( !CCLayer::init() )
    {
        return false;
    }
    mLayerInstance = this;

    pEmitter = cocos2d::CCParticleGalaxy::createWithTotalParticles(200);
    CCSize winSize = CCDirector::sharedDirector()->getWinSize();
    pEmitter->setPosition(ccp(winSize.width / 2.0f, winSize.height / 2.0f));
    this->addChild(pEmitter);

    this->setTouchEnabled(true);
    this->setKeypadEnabled(true);

    return true;
}

void ParticleViewerScene::keyBackClicked(void)
{
    cocos2d::CCDirector::sharedDirector()->end();
}

bool ParticleViewerScene::ccTouchBegan(cocos2d::CCTouch *pTouch, cocos2d::CCEvent *pEvent) {
    ph::Log::d("roger_tag", "ccTouchBegan");
    return true;
}

void ParticleViewerScene::ccTouchMoved(cocos2d::CCTouch *pTouch, cocos2d::CCEvent *pEvent) {
    ph::Log::d("roger_tag", "ccTouchMoved");
    // CCTouch uses upper left corner as (0, 0), while OpenGL uses bottom left
    CCPoint touchPos = CCDirector::sharedDirector()->convertToGL(pTouch->getLocationInView());
    CCPoint moveDelta = ccpSub(touchPos, pTouch->getPreviousLocation());
    CCPoint nextPos = ccpAdd(pEmitter->getPosition(), moveDelta);
    pEmitter->setPosition(nextPos);
}

void ParticleViewerScene::menuCloseCallback(CCObject* pSender)
{
    CCDirector::sharedDirector()->end();

#if (CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    exit(0);
#endif
}

extern "C" {

void Java_ph_particledesign_MainActivity_onGLViewSizeChanged() {
}

void Java_ph_particledesign_ConfigurationListAdapter_setProgressValue(
        JNIEnv *env, jobject obj,
        jstring tag,
        jint progress,
        jint type) {

    const char *cTag = env->GetStringUTFChars(tag, NULL);
    if (NULL == cTag) {
        return;
    }

    pEmitter->resetSystem();

    cocos2d::CCString cppTag(cTag);
    if (cppTag.m_sString == "speed") {
        pEmitter->setSpeed(progress);
    } else if (cppTag.m_sString == "duration") {
        pEmitter->setDuration(progress);
    } else if (cppTag.m_sString == "life") {
        pEmitter->setLife(progress);
    } else if (cppTag.m_sString == "max_particle") {
        pEmitter->initWithTotalParticles((progress + 1) * 100);
    }

    env->ReleaseStringUTFChars(tag, cTag);
}

void Java_ph_particledesign_MainActivity_applyPreset(JNIEnv *env, jobject obj, jint type) {
    int totalParticle = 200;
    cocos2d::CCParticleSystem *pLocalEmitter;

    std::stringstream ss;
    ss << type;

    ph::Log::d("roger_tag", ss.str());
    switch (type) {
    case 0:
        pLocalEmitter = cocos2d::CCParticleExplosion::createWithTotalParticles(totalParticle);
        break;
    case 1:
        pLocalEmitter = cocos2d::CCParticleFire::createWithTotalParticles(totalParticle);
        break;
    case 2:
        pLocalEmitter = cocos2d::CCParticleFireworks::createWithTotalParticles(totalParticle);
        break;
    case 3:
        pLocalEmitter = cocos2d::CCParticleFlower::createWithTotalParticles(totalParticle);
        break;
    case 4:
        pLocalEmitter = cocos2d::CCParticleGalaxy::createWithTotalParticles(totalParticle);
        break;
    case 5:
        pLocalEmitter = cocos2d::CCParticleMeteor::createWithTotalParticles(totalParticle);
        break;
    case 6:
        pLocalEmitter = cocos2d::CCParticleRain::createWithTotalParticles(totalParticle);
        break;
    case 7:
        pLocalEmitter = cocos2d::CCParticleSmoke::createWithTotalParticles(totalParticle);
        break;
    case 8:
        pLocalEmitter = cocos2d::CCParticleSnow::createWithTotalParticles(totalParticle);
        break;
    case 9:
        pLocalEmitter = cocos2d::CCParticleSpiral::createWithTotalParticles(totalParticle);
        break;
    case 10:
        pLocalEmitter = cocos2d::CCParticleSun::createWithTotalParticles(totalParticle);
        break;
    }
    CCSize winSize = CCDirector::sharedDirector()->getWinSize();
    pLocalEmitter->setPosition(pEmitter->getPosition());
    ParticleViewerScene::getLayerInstance()->removeChild(pEmitter);
    pEmitter = pLocalEmitter;
    ParticleViewerScene::getLayerInstance()->addChild(pEmitter);
}

}
