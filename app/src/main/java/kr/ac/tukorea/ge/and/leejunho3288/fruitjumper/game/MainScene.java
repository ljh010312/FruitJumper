package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.HorzScrollBackground;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.VertScrollBackground;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MainScene extends Scene {
    public enum Layer {
        background, platform, enemy, player, button;
        public static final int COUNT = values().length;
    }
    private final Player player;
    private final ButtonObject leftButton;
    private final ButtonObject rightButton;
    private final ButtonObject jumpButton;

    public MainScene() {
        Metrics.setGameSize(1600, 900);
        initLayers(Layer.COUNT);

        this.player = new Player();
        add(Layer.player, player);

        ButtonObject[] buttons = initButtons();
        leftButton = buttons[0];
        rightButton = buttons[1];
        jumpButton = buttons[2];

        add(Layer.background, new VertScrollBackground(R.mipmap.background_brown, 40));

        //addFloorPlatforms();
        //addOneWayPlatforms();

        // Moving obstacle
        //add(Layer.enemy, new MovingObstacle(MovingObstacle.Direction.VERTICAL, 200f, 2f, 500f, 200f));
    }

    private void addFloorPlatforms() {
        float startX = 50f;
        float endX = 1550f;
        float platformWidth = 100f;
        float platformHeight = 100f;
        float y = 850f;

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
    private ButtonObject[] initButtons() {
        Bitmap leftBtnBitmap = BitmapPool.get(R.mipmap.button_play_inverse);
        Bitmap leftPressedBtnBitmap = BitmapPool.get(R.mipmap.button_play_pressed_inverse);
        Bitmap rightBtnBitmap = BitmapPool.get(R.mipmap.button_play);
        Bitmap rightPressedBtnBitmap = BitmapPool.get(R.mipmap.button_play_pressed);
        Bitmap jumpBtnBitmap = BitmapPool.get(R.mipmap.button_jump);
        Bitmap jumpPressedBtnBitmap = BitmapPool.get(R.mipmap.button_jump_pressed);

        ButtonObject left = new ButtonObject(
                leftBtnBitmap, leftPressedBtnBitmap,
                200, 700, 350, 850, () -> {}
        );
        add(Layer.button, left);

        ButtonObject right = new ButtonObject(
                rightBtnBitmap, rightPressedBtnBitmap,
                350, 700, 500, 850, () -> {}
        );
        add(Layer.button, right);

        ButtonObject jump = new ButtonObject(
                jumpBtnBitmap, jumpPressedBtnBitmap,
                1300, 700, 1500, 825, () -> {}
        );
        add(Layer.button, jump);

        return new ButtonObject[] { left, right, jump };
    }

    // Game Loop Functions
    @Override
    public void update() {
        super.update();
        if (leftButton.isPressed()) {
            player.moveLeft();
        }
        if (rightButton.isPressed()) {
            player.moveRight();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        leftButton.handleTouch(event);
        rightButton.handleTouch(event);
        jumpButton.handleTouch(event);
        return true;
    }
}
