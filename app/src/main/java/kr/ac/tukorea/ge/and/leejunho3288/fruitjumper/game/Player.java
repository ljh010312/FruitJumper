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
    private int moveDir;
    private RectF collisionRect = new RectF();

    public enum State {
        idle, move, jump
    }
    protected State state = State.idle;

    public Player(){
        super(R.mipmap.player_idle, 20, 11);
        this.x = Metrics.width / 2;
        this.y = Metrics.height - 200;
        setPosition(x, y, PLAYER_WIDTH_HEIGHT, PLAYER_WIDTH_HEIGHT);
        updateCollisionRect();
    }

    @Override
    public void update() {
        switch (state) {
            case idle:
                break;
            case move:
                x += moveDir * SPEED * GameView.frameTime;
                setPosition(x, y, PLAYER_WIDTH_HEIGHT, PLAYER_WIDTH_HEIGHT);
                updateCollisionRect();
                break;
            case jump:
                break;
        }
    }

    private void setState(State state) {
        this.state = state;
        updateCollisionRect();
    }
    public void moveLeft(boolean startMove) {
        if (state == State.idle && startMove) {
            setState(State.move);
            moveDir = -1;
            return;
        }
        if (state == State.move && !startMove) {
            setState(State.idle);
            //return;
        }
    }

    public void moveRight(boolean startMove) {
        if (state == State.idle && startMove) {
            setState(State.move);
            moveDir = 1;
            return;
        }
        if (state == State.move && !startMove) {
            setState(State.idle);
        }
    }

    private void updateCollisionRect() {
        collisionRect.set(dstRect);
        collisionRect.inset(20f, 0f);
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