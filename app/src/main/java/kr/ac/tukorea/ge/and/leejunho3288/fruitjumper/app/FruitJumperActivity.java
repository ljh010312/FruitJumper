package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.app;

import android.os.Bundle;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.BuildConfig;
import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game.MainScene;
import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game.StartScene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class FruitJumperActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GameView.drawsDebugStuffs = BuildConfig.DEBUG;
        super.onCreate(savedInstanceState);
        new StartScene().push();
    }
}