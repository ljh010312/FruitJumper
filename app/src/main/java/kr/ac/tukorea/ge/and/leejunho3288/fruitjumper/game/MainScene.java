package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.HorzScrollBackground;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.VertScrollBackground;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MainScene extends Scene {
    public enum Layer {
        background, platform, fruit, checkpoint, enemy, player, touch, controller;
        public static final int COUNT = values().length;
    }

    private final Player player;
    public int checkpointStarCount = 0;

    public MainScene() {
        Metrics.setGameSize(1600, 900);
        initLayers(Layer.COUNT);

        this.player = new Player();
        add(Layer.player, player);
        addButton();
        add(Layer.background, new VertScrollBackground(R.mipmap.background_brown, 40));
        add(Layer.platform, new MapLoader(this, 0));
        add(Layer.controller, FruitHud.get());
        add(Layer.controller, HealthHud.get());
        add(Layer.controller, new CollisionChecker(this, player));
    }

    private void addButton() {
        add(Layer.touch, new Button(R.mipmap.button_play_inverse, R.mipmap.button_play_pressed_inverse, 125f, 725f, 200f, 200f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                player.moveLeft(pressed);
                return true;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.button_play, R.mipmap.button_play_pressed, 325f, 725f, 200f, 200f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                player.moveRight(pressed);
                return true;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.button_jump, R.mipmap.button_jump_pressed, 1350f, 750f, 300f, 200f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                if (!pressed) player.jump(); // 손 뗄 때 점프
                return true;
            }
        }));

        // 옵션 버튼
        add(Layer.touch, new Button(R.mipmap.button_settings, R.mipmap.button_settings, 1550f, 50f, 100f, 100f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                new PauseScene().push();
                return true;
            }
        }));
    }


    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }

    public static void restart() {
        Scene.pop(); // 기존 MainScene 제거
        new MainScene().push();
    }

    @Override
    public void update() {
        super.update();

        // 카메라를 플레이어 기준으로 이동
        float targetX = player.getCollisionRect().centerX() - Metrics.width / 2f;
        float maxX = 3000f - Metrics.width;
        Metrics.cameraX = Math.max(0f, Math.min(targetX, maxX));

        // Y축 카메라 이동 추가
        float targetY = player.getCollisionRect().centerY() - Metrics.height / 2f;
        float maxY = 1800f - Metrics.height; // 스테이지 전체 높이 기준
        Metrics.cameraY = Math.max(0f, Math.min(targetY, maxY));


        if (checkpointStarCount > 0) {
            new ClearScene(checkpointStarCount).push();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

    }


    private void drawCollectedStars(Canvas canvas, int count) {
        if (count <= 0) return;

        int maxCount = Math.min(count, 3); // 최대 3개
        Bitmap star = BitmapPool.get(R.mipmap.yellow_star);
        float iconSize = 80f;
        float spacing = 20f;

        // 위치 계산
        float totalWidth = iconSize * maxCount + spacing * (maxCount - 1);
        float centerX = Metrics.width / 2f;
        float centerY = Metrics.height / 2f;
        float startX = centerX - totalWidth / 2f;
        float y = centerY - iconSize / 2f;

        float padding = 24f;
        float bgLeft = startX - padding;
        float bgTop = y - padding;
        float bgRight = startX + totalWidth + padding;
        float bgBottom = y + iconSize + padding;

        Paint bgPaint = new Paint();
        bgPaint.setColor(Color.WHITE);
        bgPaint.setAlpha(200); // 반투명

        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4f);

        canvas.drawRect(bgLeft, bgTop, bgRight, bgBottom, bgPaint);   // 배경
        canvas.drawRect(bgLeft, bgTop, bgRight, bgBottom, borderPaint); // 테두리

        for (int i = 0; i < maxCount; i++) {
            float x = startX + i * (iconSize + spacing);
            RectF dst = new RectF(x, y, x + iconSize, y + iconSize);
            canvas.drawBitmap(star, null, dst, null);
        }
    }

    @Override
    public boolean onBackPressed() {
        new PauseScene().push();
        return true;
    }

}

