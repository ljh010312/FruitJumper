package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.Bitmap;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.VertScrollBackground;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class SelectScene extends Scene {
    enum SelectLayer {
        bg, ui, COUNT
    }

    public SelectScene() {
        initLayers(SelectLayer.values().length);

        // 배경 설정
        Sprite bg = new Sprite(R.mipmap.trans_50b);
        float w = Metrics.width, h = Metrics.height;
        bg.setPosition(w / 2, h / 2, w, h);
        add(SelectLayer.bg, bg);

        // 버튼 설정
        float btnWidth = 256;
        float btnHeight = 256;
        float btnY = h / 2 + 300;
        float spacing = 200;

        float centerX = w / 2;
        float btnX1 = centerX - btnWidth - spacing;
        float btnX2 = centerX;
        float btnX3 = centerX + btnWidth + spacing;
        Bitmap image = BitmapPool.get(R.mipmap.start_fruit_jumper);
        add(StartScene.Layer.background, new ImageObject(image, 0, 0, Metrics.width, Metrics.height));

        // 맵1 선택 버튼
        add(SelectLayer.ui, new Button(R.mipmap.level_01, R.mipmap.level_01, btnX1, btnY, btnWidth, btnHeight, pressed -> {
            new MainScene(0).push();
            return false;
        }));

        // 맵2 선택 버튼
        add(SelectLayer.ui, new Button(R.mipmap.level_02, R.mipmap.level_02, btnX2, btnY, btnWidth, btnHeight, pressed -> {
            new MainScene(1).push();
            return false;
        }));

        // 맵 3 선택 버튼
        add(SelectLayer.ui, new Button(R.mipmap.level_03, R.mipmap.level_03, btnX3, btnY, btnWidth, btnHeight, pressed -> {
            new MainScene(2).push();
            return false;
        }));
    }

    @Override
    protected int getTouchLayerIndex() {
        return SelectLayer.ui.ordinal();
    }

}