package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

public class StartScene extends Scene {
    private static final String TAG = StartScene.class.getSimpleName();
    private final ButtonObject startButton;

    public StartScene() {
        Metrics.setGameSize(1600, 900);
        Bitmap image = BitmapPool.get(R.mipmap.start_fruit_jumper);
        gameObjects.add(new ImageObject(image, 0, 0, Metrics.width, Metrics.height));
        Bitmap btnBitmap = BitmapPool.get(R.mipmap.button_play);
        Bitmap pressedBtnBitmap = BitmapPool.get(R.mipmap.button_play_pressed);
        startButton = new ButtonObject(
                btnBitmap,
                pressedBtnBitmap,
                700, 600, 900, 800,
                () -> new MainScene().push()
        );
        gameObjects.add(startButton);

    }

    public boolean onTouchEvent(MotionEvent event) {
        return startButton.handleTouch(event);
    }
}
