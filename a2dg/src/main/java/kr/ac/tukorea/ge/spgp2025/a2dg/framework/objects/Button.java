package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ITouchable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Button extends Sprite implements ITouchable {
    public interface OnTouchListener {
        boolean onTouch(boolean pressed);
    }

    protected OnTouchListener listener;
    protected boolean captures;
    private final int defaultImageResId;
    private final int pressedImageResId;

    public Button(int defaultResId, int pressedResId, float cx, float cy, float width, float height, OnTouchListener listener) {
        super(defaultResId, cx, cy, width, height);
        this.defaultImageResId = defaultResId;
        this.pressedImageResId = pressedResId;
        this.listener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int action = e.getAction();
        float[] pts = Metrics.fromScreen(e.getX(), e.getY());
        float x = pts[0], y = pts[1];

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!dstRect.contains(x, y)) return false;
                captures = true;
                setImageResourceId(pressedImageResId); // 눌림 이미지로 변경
                return listener.onTouch(true);
            case MotionEvent.ACTION_UP:
                if (captures) {
                    setImageResourceId(defaultImageResId); // 기본 이미지로 복원
                    captures = false;
                    return listener.onTouch(false);
                }
                break;
        }
        return captures;
    }
}