package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class FruitHud implements IGameObject {
    private static FruitHud instance;
    public static FruitHud get() {
        if (instance == null) instance = new FruitHud();
        return instance;
    }

    private final List<Fruit.Type> collectedFruits = new ArrayList<>();
    private final Paint backgroundPaint = new Paint();
    private final Paint borderPaint = new Paint();
    private FruitHud() {
        backgroundPaint.setColor(Color.WHITE);

        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4f); // 테두리 두께
    }

    public void addFruit(Fruit.Type type) {
        collectedFruits.add(type);
    }

    @Override
    public void draw(Canvas canvas) {
        float iconSize = 64f;
        float spacing = 8f;
        float totalWidth = iconSize * collectedFruits.size() + spacing * (collectedFruits.size() - 1);
        float startX = Metrics.width / 2f - totalWidth / 2f;
        float y = 20f;

        float padding = 16f;
        float bgLeft = startX - padding;
        float bgTop = y - padding;
        float bgRight = startX + totalWidth + padding;
        float bgBottom = y + iconSize + padding;

        canvas.drawRect(bgLeft, bgTop, bgRight, bgBottom, backgroundPaint);
        canvas.drawRect(bgLeft, bgTop, bgRight, bgBottom, borderPaint);

        Rect src = new Rect(
                0 , 0,
                32, 32
        );

        for (int i = 0; i < collectedFruits.size(); i++) {
            Bitmap bmp = BitmapPool.get(collectedFruits.get(i).resId);
            RectF dst = new RectF(startX + i * iconSize, y, startX + (i + 1) * iconSize, y + iconSize);
            canvas.drawBitmap(bmp, src, dst, null);
        }
    }

    @Override public void update() {} // 필요 시 애니메이션 등 추가

    public int getCollectedCount() {
        return collectedFruits.size();
    }

    public void clear() {
        collectedFruits.clear();
    }
}