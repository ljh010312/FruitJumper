package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Spike extends Sprite implements IBoxCollidable {
    private final RectF collisionRect = new RectF();

    public Spike(float x, float y, float width, float height) {
        super(R.mipmap.spikes_idle, x, y, width, height);
        updateCollisionRect();
    }

    @Override
    public void update() {
        updateCollisionRect();
    }

    private void updateCollisionRect() {
        collisionRect.set(dstRect);
        collisionRect.inset(4f, 0f); // 살짝 줄여서 충돌이 자연스럽게
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