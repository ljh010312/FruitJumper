package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MainScene extends Scene {
    private final Player player;
    private final ButtonObject leftButton;
    private final ButtonObject rightButton;

    public MainScene() {
        Metrics.setGameSize(1600, 900);
        this.player = new Player();
        add(player);

        Bitmap leftBtnBitmap = BitmapPool.get(R.mipmap.button_play_inverse);
        Bitmap leftPressedBtnBitmap = BitmapPool.get(R.mipmap.button_play_pressed_inverse);
        Bitmap rightBtnBitmap = BitmapPool.get(R.mipmap.button_play);
        Bitmap rightPressedBtnBitmap = BitmapPool.get(R.mipmap.button_play_pressed);
        leftButton = new ButtonObject(
                leftBtnBitmap,
                leftPressedBtnBitmap,
                200, 700, 350, 850,
                () -> {}
        );
        add(leftButton);
        rightButton = new ButtonObject(
                rightBtnBitmap,
                rightPressedBtnBitmap,
                350, 700, 500, 850,
                () -> {}
        );
        add(rightButton);
    }

    // Game Loop Functions
    @Override
    public void update() {
        super.update();
        if (leftButton.isPressed()) {
            player.moveLeft();
        }
        if (rightButton.isPressed()) {
            player.moveRight();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        leftButton.handleTouch(event);
        rightButton.handleTouch(event);
        return true;
    }
}
