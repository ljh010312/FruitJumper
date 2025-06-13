package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class ClearScene extends Scene {
    public enum ClearLayer {
        bg, stars, ui, COUNT
    }

    public ClearScene(int starCount) {
        initLayers(ClearLayer.COUNT.ordinal());

        Sprite bg = new Sprite(R.mipmap.trans_50b);
        float w = Metrics.width, h = Metrics.height;
        bg.setPosition(w / 2, h / 2, w, h);
        add(ClearScene.ClearLayer.bg, bg);

        // 별 표시
        float starSize = 128;
        float spacing = 40;
        float totalWidth = starCount * starSize + (starCount - 1) * spacing;
        float startX = (w - totalWidth) / 2;
        float y = h / 2 - 200;

        for (int i = 0; i < starCount; i++) {
            Sprite star = new Sprite(R.mipmap.yellow_star); // full star 이미지
            float x = startX + i * (starSize + spacing);
            star.setPosition(x + starSize / 2, y, starSize, starSize);
            add(ClearLayer.stars, star);
        }

        // 버튼 공통
        float btnWidth = 256, btnHeight = 160;
        float btnY = h / 2 + 200;
        float gap = 80;
        float centerX = w / 2;
        float btnX1 = centerX - btnWidth - gap; // 재시작
        float btnX2 = centerX;                 // 다음 스테이지
        float btnX3 = centerX + btnWidth + gap; // 뒤로가기

        // 재시작 버튼
        add(ClearLayer.ui, new Button(R.mipmap.button_restart, R.mipmap.button_restart, btnX1, btnY, btnWidth, btnHeight, pressed -> {
            Scene.pop(); // ClearScene 제거
            MainScene.restart(); // mainScene 재시작
            return false;
        }));

        // 다음 스테이지 버튼
        add(ClearLayer.ui, new Button(R.mipmap.button_next, R.mipmap.button_next, btnX2, btnY, btnWidth, btnHeight, pressed -> {
            Scene.pop(); // ClearScene 제거
            //MainScene.loadNextStage(); // 다음 스테이지 로드 메서드 필요
            return false;
        }));

        // 뒤로가기 버튼
        add(ClearLayer.ui, new Button(R.mipmap.button_back, R.mipmap.button_back, btnX3, btnY, btnWidth, btnHeight, pressed -> {
            Scene.pop(); // ClearScene 제거
            Scene.pop(); // MainScene도 제거 (예: Stage선택 화면으로)
            return true;
        }));
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    protected int getTouchLayerIndex() {
        return ClearLayer.ui.ordinal();
    }
}