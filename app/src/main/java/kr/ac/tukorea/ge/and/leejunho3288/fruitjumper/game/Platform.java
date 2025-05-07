package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;

public class Platform extends Sprite implements IBoxCollidable {
    public enum Type {
        SOLID,      // 모든 방향 충돌
        ONE_WAY     // 아래에서 위로는 통과, 위에서 아래는 충돌
    }

    private final Type type;

    public Platform(int mipmapResId, float x, float y, float width, float height, Type type) {
        super(mipmapResId);
        setPosition(x, y, width, height);
        this.type = type;
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    public Type getType() {
        return type;
    }
}