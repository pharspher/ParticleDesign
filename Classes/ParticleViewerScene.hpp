#ifndef __HELLOWORLD_SCENE_H__
#define __HELLOWORLD_SCENE_H__

#include "cocos2d.h"
#include "touch_dispatcher/CCTouch.h"

class ParticleViewerScene : public cocos2d::CCLayer
{
public:
    // Here's a difference. Method 'init' in cocos2d-x returns bool, instead of returning 'id' in cocos2d-iphone
    virtual bool init();  

    // there's no 'id' in cpp, so we recommand to return the exactly class pointer
    static cocos2d::CCScene* scene();
    
    // a selector callback
    void menuCloseCallback(CCObject* pSender);
    void keyBackClicked(void);

    virtual void registerWithTouchDispatcher(void);
    virtual bool ccTouchBegan(cocos2d::CCTouch *pTouch, cocos2d::CCEvent *pEvent);
    virtual void ccTouchMoved(cocos2d::CCTouch *pTouch, cocos2d::CCEvent *pEvent);
    //virtual void ccTouchEnded(CCTouch *pTouch, CCEvent *pEvent);
    //virtual void ccTouchCancelled(CCTouch *pTouch, CCEvent *pEvent);

    // implement the "static node()" method manually
    CREATE_FUNC(ParticleViewerScene);

    static ParticleViewerScene* getLayerInstance();

private:
    static ParticleViewerScene *mLayerInstance;
};

#endif // __HELLOWORLD_SCENE_H__
