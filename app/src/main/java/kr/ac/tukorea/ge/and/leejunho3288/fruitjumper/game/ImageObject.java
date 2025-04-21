package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;

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