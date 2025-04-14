package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class ImageObject implements IGameObject {
    private final Bitmap bitmap;
    private final RectF dstRect;

    public ImageObject(Bitmap bitmap, float left, float top, float right, float bottom) {
        this.bitmap = bitmap;
        this.dstRect = new RectF(left, top, right, bottom);
    }

    public void update() {
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}