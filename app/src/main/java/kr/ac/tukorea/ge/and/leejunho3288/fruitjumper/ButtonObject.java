package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class ButtonObject implements IGameObject {
    private final RectF rect;
    private final Bitmap bitmap;
    private final Runnable onClick;

    public ButtonObject(Bitmap bitmap, float left, float top, float right, float bottom, Runnable onClick) {
        this.bitmap = bitmap;
        this.rect = new RectF(left, top, right, bottom);
        this.onClick = onClick;
    }

    public void update() {}

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, rect, null);
    }

    public boolean handleTouch(float x, float y) {
        if (rect.contains(x, y)) {
            onClick.run();
            return true;
        }
        return false;
    }
}
