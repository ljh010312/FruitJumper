package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Slime extends AnimSprite implements IBoxCollidable {


    public State getState() {
        return state;
    }

    public enum State { run, hit }

    private static final float WIDTH = 88f;
    private static final float HEIGHT = 60f;

    private State state = State.run;
    private float elapsedTime = 0f;
    private float hitTime = 0f;
    private final float startX, startY, range, speed;
    private final RectF collisionRect = new RectF();
    private boolean facingLeft = true;
    private float prevOffset = 0f;

    public Slime(float range, float speed, float startX, float startY) {
        super(R.mipmap.slime_idle_run, 20, 10);
        this.range = range;
        this.speed = speed;
        this.startX = startX;
        this.startY = startY;
        setPosition(startX, startY, WIDTH, HEIGHT);
        updateCollisionRect();
    }

    @Override
    public void update() {
        switch (state) {
            case run:
                elapsedTime += GameView.frameTime;
                float offset = (float) Math.sin(elapsedTime * speed) * range;

                // 방향 판별: offset 값의 변화량을 기준으로
                if (offset < prevOffset) {
                    facingLeft = false;
                } else if (offset > prevOffset) {
                    facingLeft = true;
                }
                prevOffset = offset;

                float x = startX + offset;
                float y = startY;
                setPosition(x, y, WIDTH, HEIGHT);
                break;

            case hit:
                hitTime += GameView.frameTime;
                if (hitTime > 0.2f) { // 0.2초 후 다시 run 상태로
                    setState(State.run);
                }
                break;
        }



        super.update();
        updateCollisionRect();
    }

    public void onStepped() {
        setState(State.hit); // 현재 hit 상태 구현됨
    }
    private void setState(State newState) {
        if (this.state == newState) return;
        this.state = newState;

        switch (newState) {
            case run:
                setImageResourceId(R.mipmap.slime_idle_run, 20, 10);
                break;
            case hit:
                setImageResourceId(R.mipmap.slime_hit, 20, 5);
                hitTime = 0;
                break;
        }
    }

    private void updateCollisionRect() {
        collisionRect.set(dstRect);
        collisionRect.inset(10f, 10f); // 충돌 박스 축소
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        int frameIndex = Math.round(time * fps) % frameCount;
        srcRect.set(frameIndex * frameWidth, 0, (frameIndex + 1) * frameWidth, frameHeight);

        // 화면 좌표로 보정
        RectF screenDst = new RectF(dstRect);
        screenDst.offset(-Metrics.cameraX, -Metrics.cameraY); // 카메라 보정

        // 좌우 반전 처리
        if (facingLeft) {
            canvas.save();
            float px = screenDst.centerX();
            canvas.scale(-1, 1, px, screenDst.centerY()); // X축 기준 좌우 반전
            canvas.drawBitmap(bitmap, srcRect, screenDst, null);
            canvas.restore();
        } else {
            canvas.drawBitmap(bitmap, srcRect, screenDst, null);
        }
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

}