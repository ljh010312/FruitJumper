package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.Bitmap;
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
        background, platform, fruit, enemy, player, touch, controller;
        public static final int COUNT = values().length;
    }
    private final Player player;

    public MainScene() {
        Metrics.setGameSize(1600, 900);
        initLayers(Layer.COUNT);

        this.player = new Player();
        add(Layer.player, player);

        add(Layer.touch, new Button(R.mipmap.button_play_inverse, R.mipmap.button_play_pressed_inverse,200f, 700f, 150f, 150f, new Button.OnTouchListener() {
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


        add(Layer.touch, new Button(R.mipmap.button_jump, R.mipmap.button_jump_pressed, 1300f, 300f, 200f, 125f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                player.hit();
                return false;
            }
        }));


        add(Layer.background, new VertScrollBackground(R.mipmap.background_brown, 40));

        addFloorPlatforms();
        addOneWayPlatforms();

        // Moving obstacle
        add(Layer.enemy, new MovingObstacle(MovingObstacle.Direction.VERTICAL, 200f, 2f, 500f, 200f));

        add(Layer.fruit, new Fruit(Fruit.Type.BANANA, 800f, 700f, 64f, 64.f));
        add(Layer.fruit, new Fruit(Fruit.Type.APPLE, 1000f, 700f, 64f, 64.f));
        add(Layer.fruit, new Fruit(Fruit.Type.ORANGE, 1200f, 700f, 64f, 64.f));
        add(Layer.controller, FruitHud.get());
        add(Layer.controller, new CollisionChecker(this, player));
    }

    private void addFloorPlatforms() {
        float startX = 0f;
        float endX = 1600f;
        float platformWidth = 100f;
        float platformHeight = 100f;
        float y = 850f;

        for (float x = startX; x <= endX; x += platformWidth) {
            add(Layer.platform, new Platform(R.mipmap.ground, x, y, platformWidth, platformHeight,Platform.Type.SOLID));
        }

        y = 600f;
        endX = 1000;
        for (float x = startX; x <= endX; x += platformWidth) {
            add(Layer.platform, new Platform(R.mipmap.ground, x, y, platformWidth, platformHeight,Platform.Type.SOLID));
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

}
