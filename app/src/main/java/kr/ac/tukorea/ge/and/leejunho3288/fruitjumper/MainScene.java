package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();

    public MainScene() {
        //Metrics.setGameSize(1000, 1600);
        //Metrics.setGameSize(700, 1600);
        Metrics.setGameSize(1000, 600);

    }
}
