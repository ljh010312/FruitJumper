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
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.Sound;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MainScene extends Scene {
    public enum Layer {
        background, platform, fruit, checkpoint, enemy, player, touch, controller;
        public static final int COUNT = values().length;
    }

    private final Player player;
    private int stageIndex;
    public int checkpointStarCount = 0;


    public MainScene(int stageIndex) {
        Metrics.setGameSize(1600, 900);
        initLayers(Layer.COUNT);
        this.stageIndex = stageIndex;
        this.player = new Player();
        add(Layer.player, player);
        addButton();
        add(Layer.background, new VertScrollBackground(R.mipmap.background_brown, 40));
        add(Layer.platform, new MapLoader(this, this.stageIndex));
        FruitHud.get().clear();
        add(Layer.controller, FruitHud.get());
        HealthHud.get().setHp(5, 5);
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
                new PauseScene(stageIndex).push();
                return true;
            }
        }));
    }


    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }

    public static void restart(int stageIndex) {
        Scene.pop(); // 기존 MainScene 제거
        new MainScene(stageIndex).push();
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

        if (1800 < targetY || HealthHud.get().isDie()) {
            Sound.playEffect(R.raw.fail_sound);

            new ClearScene(checkpointStarCount, this.stageIndex, false).push();
        }

        if (checkpointStarCount > 0) {
            Sound.playEffect(R.raw.clear_sound);

            new ClearScene(checkpointStarCount, this.stageIndex, true).push();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

    }
    @Override
    public boolean onBackPressed() {
        new PauseScene(stageIndex).push();
        return true;
    }
    @Override
    public void onEnter() {
        Sound.playMusic(R.raw.gamebgm);
    }
    @Override
    public void onPause() {
        Sound.pauseMusic();
    }

    @Override
    public void onResume() {
        Sound.resumeMusic();
    }
    @Override
    public void onExit() {
        Sound.stopMusic();
    }
}



