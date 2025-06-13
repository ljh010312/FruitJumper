package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;

public class HealthHud implements IGameObject {
    private static HealthHud instance;
    public static HealthHud get() {
        if (instance == null) instance = new HealthHud();
        return instance;
    }

    private int maxHp = 5;
    private int currentHp = 5;

    private final int fullHeartResId = R.mipmap.heart_full;
    private final int emptyHeartResId = R.mipmap.heart_empty;

    private final float iconSize = 60f;
    private final float margin = 20f;
    private final float spacing = 10f;

    private final Paint backgroundPaint = new Paint();
    private final Paint borderPaint = new Paint();

    private HealthHud() {
        backgroundPaint.setColor(Color.WHITE);

        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4f); // 테두리 두께
    }

    public void setHp(int current, int max) {
        this.currentHp = current;
        this.maxHp = max;
    }

    @Override
    public void draw(Canvas canvas) {
        float totalWidth = iconSize * maxHp + spacing * (maxHp - 1);
        float left = margin;
        float top = margin;
        float right = left + totalWidth;
        float bottom = top + iconSize;

        // 1. 흰색 배경
        float padding = 12f;
        canvas.drawRect(left - padding, top - padding, right + padding, bottom + padding, backgroundPaint);

        // 2. 검은 테두리
        canvas.drawRect(left - padding, top - padding, right + padding, bottom + padding, borderPaint);

        // 3. 하트 아이콘들
        for (int i = 0; i < maxHp; i++) {
            int resId = (i < currentHp) ? fullHeartResId : emptyHeartResId;
            Bitmap bmp = BitmapPool.get(resId);
            float x = left + i * (iconSize + spacing);
            RectF dst = new RectF(x, top, x + iconSize, top + iconSize);
            canvas.drawBitmap(bmp, null, dst, null);
        }
    }

    @Override
    public void update() {
        // 필요 시 체력 애니메이션 등 추가 가능
    }

    public boolean isDie() {
        return currentHp == 0;
    }
}