package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Player extends AnimSprite implements IBoxCollidable {
    private static final float SPEED = 300f;
    private static final float PLAYER_WIDTH_HEIGHT = 100f;
    private float x, y;
    private RectF collisionRect = new RectF();

    public Player(){
        super(R.mipmap.player_idle, 20, 11);
        this.x = Metrics.width / 2;
        this.y = Metrics.height - 200;
        setPosition(x, y, PLAYER_WIDTH_HEIGHT, PLAYER_WIDTH_HEIGHT);
        updateCollisionRect();
    }

    public void moveLeft() {
        x -= SPEED * GameView.frameTime;
        setPosition(x, y, PLAYER_WIDTH_HEIGHT, PLAYER_WIDTH_HEIGHT);
        updateCollisionRect();
    }

    public void moveRight() {
        x += SPEED * GameView.frameTime;
        setPosition(x, y, PLAYER_WIDTH_HEIGHT, PLAYER_WIDTH_HEIGHT);
        updateCollisionRect();
    }

    private void updateCollisionRect() {
        collisionRect.set(dstRect);
        collisionRect.inset(20f, 0f); // 조금 작게 설정
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}