package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.Bitmap;
import android.graphics.RectF;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MovingObstacle extends AnimSprite implements IBoxCollidable, IRecyclable {
    private static final float WIDTH = 108f;
    private static final float HEIGHT = 100f;

    public enum Direction { HORIZONTAL, VERTICAL }

    private final Direction direction;
    private final float range;
    private final float speed;
    private final float startX, startY;
    private float elapsedTime = 0f;
    private final RectF collisionRect = new RectF();

    public MovingObstacle(Direction direction, float range, float speed, float startX, float startY) {
        super(R.mipmap.obstacle_blink, 10, 4);
        this.direction = direction;
        this.range = range;
        this.speed = speed;
        this.startX = startX;
        this.startY = startY;
        setPosition(startX, startY, WIDTH, HEIGHT);
        updateCollisionRect();
    }

    @Override
    public void update() {
        elapsedTime += GameView.frameTime;
        float offset = (float) Math.sin(elapsedTime * speed) * range;

        float x = startX;
        float y = startY;

        if (direction == Direction.HORIZONTAL) {
            x += offset;
        } else {
            y += offset;
        }

        setPosition(x, y, WIDTH, HEIGHT);
        updateCollisionRect();
    }

    private void updateCollisionRect() {
        collisionRect.set(dstRect);
        collisionRect.inset(8f, 8f); // 약간 축소된 충돌 박스
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    @Override
    public void onRecycle() {
        // 필요 시 오브젝트 재사용 처리
    }
}
