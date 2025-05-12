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

        add(Layer.touch, new Button(R.mipmap.button_play_inverse, R.mipmap.button_play_pressed_inverse, 200f, 700f, 150f, 150f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                player.moveLeft(pressed);
                return true;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.button_play, R.mipmap.button_play_pressed, 350f, 700f, 150f, 150f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                player.moveRight(pressed);
                return true;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.button_jump, R.mipmap.button_jump_pressed, 1300f, 700f, 200f, 125f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                if (!pressed) player.jump(); // 손 뗄 때 점프
                return true;
            }
        }));

        add(Layer.background, new VertScrollBackground(R.mipmap.background_brown, 40));

        addFloorPlatforms();
        addOneWayPlatforms();

        // Moving obstacle
        add(Layer.enemy, new MovingObstacle(MovingObstacle.Direction.VERTICAL, 200f, 2f, 500f, 200f));
        add(Layer.enemy, new Spike(600f, 830f, 64f, 32f)); // x, y, width, height


        add(Layer.fruit, new Fruit(Fruit.Type.BANANA, 800f, 700f, 64f, 64.f));
        add(Layer.fruit, new Fruit(Fruit.Type.APPLE, 1000f, 700f, 64f, 64.f));
        add(Layer.fruit, new Fruit(Fruit.Type.ORANGE, 1200f, 700f, 64f, 64.f));
        add(Layer.controller, FruitHud.get());
        add(Layer.controller, HealthHud.get());
        add(Layer.checkpoint, new Checkpoint(1500, 800, 100, 100));

        add(Layer.controller, new CollisionChecker(this, player));


    }

    private void addFloorPlatforms() {
        float startX = 0f;
        float endX = 1600f;
        float platformWidth = 100f;
        float platformHeight = 100f;
        float y = 850f;

        for (float x = startX; x <= endX; x += platformWidth) {
            add(Layer.platform, new Platform(R.mipmap.ground, x, y, platformWidth, platformHeight, Platform.Type.SOLID));
        }

        y = 600f;
        endX = 1000;
        for (float x = startX; x <= endX; x += platformWidth) {
            add(Layer.platform, new Platform(R.mipmap.ground, x, y, platformWidth, platformHeight, Platform.Type.SOLID));
        }
    }

    private void addOneWayPlatforms() {
        float platformWidth = 100f;
        float platformHeight = 10f;
        float startY = 550f;
        float verticalGap = 150f;
        float horizontalGap = 250f;
        int rows = 2;

        for (int row = 0; row < rows; row++) {
            float y = startY - row * verticalGap;
            float offset = (row % 2 == 0) ? 50f : 150f;

            for (float x = offset; x <= 1600 - platformWidth; x += horizontalGap) {
                add(Layer.platform, new Platform(
                        R.mipmap.plat,
                        x + platformWidth / 2, y + platformHeight / 2,
                        platformWidth, platformHeight,
                        Platform.Type.ONE_WAY
                ));
            }
        }
    }

    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }

    @Override
    public void update() {
        super.update();

        // 카메라를 플레이어 기준으로 이동
        float targetX = player.getCollisionRect().centerX() - Metrics.width / 2f;
        float maxX = 3000f - Metrics.width;
        Metrics.cameraX = Math.max(0f, Math.min(targetX, maxX));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (checkpointStarCount > 0) {
            drawCollectedStars(canvas, checkpointStarCount);
        }
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
}