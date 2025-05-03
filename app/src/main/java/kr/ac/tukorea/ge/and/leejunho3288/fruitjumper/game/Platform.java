package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;

public class Platform extends Sprite implements IBoxCollidable {

    public Platform(float x, float y, float width, float height) {
        super(R.mipmap.ground);
        setPosition(x, y, width, height);
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }
}
