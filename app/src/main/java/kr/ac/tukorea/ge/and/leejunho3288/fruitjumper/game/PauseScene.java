package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.widget.Toast;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class PauseScene extends Scene{
    enum PauseLayer {
        bg, ui, COUNT
    }

    private long createdOn;
    //private final Toast toast;

    public PauseScene(int stageIndex) {
        initLayers(PauseLayer.values().length);
        Sprite bg = new Sprite(R.mipmap.trans_50b);
        float w = Metrics.width, h = Metrics.height;
        bg.setPosition(w / 2, h / 2, w, h);
        add(PauseLayer.bg, bg);

        // 버튼 공통 크기
        float btnWidth = 256;
        float btnHeight = 192;

        // 버튼 y좌표는 화면 정중앙
        float btnY = h / 2;

        // 버튼 간 간격
        float spacing = 80;

        // 버튼의 x좌표 계산
        float centerX = w / 2;
        float btnX1 = centerX - btnWidth - spacing; // 왼쪽 버튼
        float btnX2 = centerX;                      // 가운데 버튼
        float btnX3 = centerX + btnWidth + spacing; // 오른쪽 버튼

        // 왼쪽: 재시작 버튼
        add(PauseLayer.ui, new Button(R.mipmap.button_restart, R.mipmap.button_restart, btnX1, btnY, btnWidth, btnHeight, pressed -> {
            Scene.pop(); // pauseScene pop
            Scene.pop(); // mainScene pop
            new MainScene(stageIndex).push(); // 맵 여러개 되면 수정해야함
            return false;
        }));

        // 가운데: 재개 버튼 (예: 일시정지 해제)
        add(PauseLayer.ui, new Button(R.mipmap.button_resume, R.mipmap.button_resume, btnX2, btnY, btnWidth, btnHeight, pressed -> {
            Scene.pop(); // pauseScene pop
            return false;
        }));

        // 오른쪽: 메인으로 돌아가기 버튼
        add(PauseLayer.ui, new Button(R.mipmap.button_back, R.mipmap.button_back, btnX3, btnY, btnWidth, btnHeight, pressed -> {
            Scene.pop(); // pauseScene pop
            Scene.pop(); // mainScene pop
            return false;
        }));


        createdOn = System.currentTimeMillis();
//        toast = Toast.makeText(GameView.view.getContext(), R.string.back_press_msg, Toast.LENGTH_SHORT);
//        toast.show();
    }

    // Overridables


    @Override
    public boolean onBackPressed() {
        long now = System.currentTimeMillis();
        if (now - createdOn < 500) {
            Scene.popAll();
            //toast.cancel();
            return true;
        }
        return super.onBackPressed(); // pop this scene
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    protected int getTouchLayerIndex() {
        return PauseLayer.ui.ordinal();
    }
}
