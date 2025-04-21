package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class ButtonObject implements IGameObject {
    private final RectF rect;
    private final Bitmap bitmap;
    private final Bitmap pressedBitmap;
    private final Runnable onClick;
    private boolean isPressed = false;


    public ButtonObject(Bitmap bitmap, Bitmap pressedBitmap, float left, float top, float right, float bottom, Runnable onClick) {
        this.bitmap = bitmap;
        this.pressedBitmap = pressedBitmap;
        this.rect = new RectF(left, top, right, bottom);
        this.onClick = onClick;
    }

    public void update() {
    }
    public boolean isPressed() {
        return isPressed;
    }
    public void draw(Canvas canvas) {
        Bitmap bitmapToDraw = isPressed ? pressedBitmap : bitmap;
        canvas.drawBitmap(bitmapToDraw, null, rect, null);    }

    public boolean handleTouch(MotionEvent event) {
        float[] xy = Metrics.fromScreen(event.getX(), event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (rect.contains(xy[0], xy[1])) {
                    isPressed = true;
                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
                if (isPressed && rect.contains(xy[0], xy[1])) {
                    onClick.run();  // 클릭 처리
                }
                isPressed = false;
                return true;

            case MotionEvent.ACTION_CANCEL:
                isPressed = false;
                return true;
        }
        return false;
    }
}
