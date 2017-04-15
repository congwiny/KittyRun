package com.hc.lab.kittyrun.sprite;

import com.hc.lab.kittyrun.action.Action;
import com.hc.lab.kittyrun.screenplay.ScreenPlay;
import com.hc.lab.kittyrun.util.CommonUtil;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCJumpTo;
import org.cocos2d.actions.interval.CCSequence;

/**
 * Created by congwiny on 2017/4/14.
 */

public class KittySpirite2 extends ActionSprite {

    private boolean isWalking;
    private boolean isFlying;

    public KittySpirite2(ScreenPlay play) {
        super(play);
    }

    @Override
    public void run(Action action) {
        super.run(action);
        switch (action.type) {
            case Action.TYPE_WALK:
                walk();
                break;
            case Action.TYPE_JUMP:
                break;
        }
    }

    private void walk() {
        if (!isWalking) {
            isWalking = true;
            isFlying = false;
            this.stopAllActions();
            this.runAction(CommonUtil.getRepeatAnimation(null, 0, 5, "image/kitty/run000%01d.png", 0.15f));
            CCSequence ccSequence = CCSequence.actions(CCDelayTime.action(5), CCDelayTime.action(0.5F), CCCallFunc.action(this, "startJump"));
            this.runAction(ccSequence);
        }
    }

    private void jump() {
        if (!isFlying) {
            isWalking = false;
            isFlying = true;
            this.stopAllActions();
            CCJumpTo ccJumpTo = CCJumpTo.action(2.0f, getPosition(), 300f, 1);
            CCSequence ccSequence = CCSequence.actions(ccJumpTo, CCCallFunc.action(this, "endJump"));
            this.runAction(ccSequence);
            this.runAction(CommonUtil.getRepeatAnimation(null, 1, 3, "image/kitty/fly000%01d.png", 0.15f));
        }
    }

    public void startJump() {
        jump();
    }


    public void endJump() {
        this.stopAllActions();
        walk();
    }

}
