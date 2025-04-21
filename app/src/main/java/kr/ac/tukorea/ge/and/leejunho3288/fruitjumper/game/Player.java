package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Player extends AnimSprite {
    private static final float SPEED = 300f;
    private static final float PLAYER_WIDTH_HEIGHT = 100f;
    private float x, y;

    public Player(){
        super(R.mipmap.player_idle, 20, 11);
        this.x = Metrics.width / 2;
        this.y = Metrics.height - 200;
        setPosition(x, y, PLAYER_WIDTH_HEIGHT, PLAYER_WIDTH_HEIGHT);
    }
    public void moveLeft() {
        x -= SPEED * GameView.frameTime;
        setPosition(x, y, PLAYER_WIDTH_HEIGHT, PLAYER_WIDTH_HEIGHT);
    }

    public void moveRight() {
        x += SPEED * GameView.frameTime;
        setPosition(x, y, PLAYER_WIDTH_HEIGHT, PLAYER_WIDTH_HEIGHT);
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

}
