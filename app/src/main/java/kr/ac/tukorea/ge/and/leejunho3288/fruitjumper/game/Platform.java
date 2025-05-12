package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;


import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Platform extends Sprite implements IBoxCollidable {
    public enum Type {
        SOLID, ONE_WAY;
    }

    private final Type type;
    private final RectF collisionRect = new RectF();

    public Platform(int bitmapResId, float left, float top, float width, float height, Type type) {
        super(bitmapResId, left + width / 2, top + height / 2, width, height);
        this.type = type;
        collisionRect.set(dstRect); // 기본 충돌 박스
    }

    public boolean canPassFromBelow() {
        return type == Type.ONE_WAY;
    }

    @Override
    public void draw(Canvas canvas) {
        RectF screenDst = new RectF(dstRect);
        screenDst.offset(-Metrics.cameraX, 0); // 카메라 보정
        canvas.drawBitmap(bitmap, srcRect, screenDst, null);
    }
    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }
}