package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.RectF;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;

public class Fruit extends AnimSprite implements IBoxCollidable {
    public enum Type {
        APPLE(R.mipmap.fruit_apple),
        BANANA(R.mipmap.fruit_bananas),
        ORANGE(R.mipmap.fruit_orange),
        STRAWBERRY(R.mipmap.fruit_strawberry),
        GRAPE(R.mipmap.fruit_cherries);

        public final int resId;
        Type(int resId) { this.resId = resId; }
    }

    private final Type type;
    private final RectF collisionRect = new RectF();

    public Fruit(Type type, float x, float y, float width, float height) {
        super(type.resId, 20, 17);
        this.type = type;
        setPosition(x, y, width, height);
        updateCollisionRect();
    }

    public Type getType() {
        return type;
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    private void updateCollisionRect() {
        collisionRect.set(dstRect);
        collisionRect.inset(10f, 10f); // 약간 줄여서 충돌 범위 정확히
    }

    @Override
    public void update() {
        super.update();
    }
}