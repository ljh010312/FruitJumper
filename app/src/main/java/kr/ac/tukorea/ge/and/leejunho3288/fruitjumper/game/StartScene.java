package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class StartScene extends Scene {
    private static final String TAG = StartScene.class.getSimpleName();
    private final ButtonObject startButton;
    public enum Layer {
        background, controller;
        public static final int COUNT = values().length;
    }
    public StartScene() {
        Metrics.setGameSize(1600, 900);
        initLayers(Layer.COUNT);
        Bitmap image = BitmapPool.get(R.mipmap.start_fruit_jumper);
        add(Layer.background, new ImageObject(image, 0, 0, Metrics.width, Metrics.height));
        Bitmap btnBitmap = BitmapPool.get(R.mipmap.button_play);
        Bitmap pressedBtnBitmap = BitmapPool.get(R.mipmap.button_play_pressed);
        startButton = new ButtonObject(
                btnBitmap,
                pressedBtnBitmap,
                700, 600, 900, 800,
                () -> new MainScene().push()
        );
        add(Layer.controller, startButton);

    }

    public boolean onTouchEvent(MotionEvent event) {
        return startButton.handleTouch(event);
    }
}