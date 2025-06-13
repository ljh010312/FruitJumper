package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game.MainScene;
import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game.Platform;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MapLoader implements IGameObject {
    private final MainScene scene;
    private final Random random = new Random();
    private int index;
    public MapLoader(MainScene mainScene, int stageIndex) {
        this.scene = mainScene;
        loadStage(stageIndex);
    }



    private static final int STAGE_WIDTH = 30;
    private static final int STAGE_HEIGHT = 18;

    // JSON 에 있던 int[] 를 가져온다.
    private static final int[][] STAGES = {
            {
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0,
                    5, 5, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 6, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 5, 5, 5,
                    0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 5, 5, 6, 6, 6, 6, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0,
                    0, 0, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 5, 5, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 6, 6, 5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0,
                    16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 6, 6, 6, 5, 5, 0, 0, 0, 0, 17, 0, 0, 0, 0, 0, 0, 0, 5,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 6, 5, 5, 0, 0, 0, 0, 0, 0, 11, 0, 0, 0, 0, 0, 0, 5, 0,
                    0, 0, 0, 0, 0, 12, 12, 0, 0, 0, 0, 5, 5, 0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0,
                    3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0
            }
    };

    private void loadStage(int stageIndex) {
        for (int col = 0; col < STAGE_WIDTH; col++) {
            createColumn(col, stageIndex);
        }
    }

    private void createColumn(int col, int stageIndex) {
        float x = 100 * col; // 열 위치 계산
        for (int row = 0; row < STAGE_HEIGHT; row++) {
            int tile = getAt(col, row, stageIndex);
            float y = 100 * row;
            createObject(tile, x, y); // 타일 생성
        }
    }
    private void createObject(int tile, float left, float top) {
        // 플랫폼 타일인 경우.
        if (tile == 3 || tile == 5) {
            Platform.Type ptype = tile == 3 ? Platform.Type.SOLID : Platform.Type.ONE_WAY;
            int resId = tile == 3 ? R.mipmap.ground : R.mipmap.plat;
            float height = tile == 3 ? 100 : 10;
            Platform platform = new Platform(resId,left, top, 100, height, ptype);

            scene.add(MainScene.Layer.platform, platform);
            return;
        }

        // 과일
        if (tile >= 15 && tile <= 19) {
            int typeIndex = tile - 15; // 15 → 0, 16 → 1, ..., 19 → 4
            Fruit.Type[] types = Fruit.Type.values();
            Fruit.Type ftype = types[typeIndex];

            Fruit fruit = new Fruit(ftype, left + 50.f , top + 50.f, 64.f, 64.f);
            scene.add(MainScene.Layer.fruit, fruit);
            return;
        }

        // 1 == 체크포인트
        if (tile == 1) {
            scene.add(MainScene.Layer.checkpoint, new Checkpoint(left + 50, top + 50, 100, 100));
            return;
        }

        // 10, 11, 12 장애물
        if (tile == 10 || tile == 11) {
            MovingObstacle.Direction dir = tile == 10 ? MovingObstacle.Direction.HORIZONTAL : MovingObstacle.Direction.VERTICAL;
            scene.add(MainScene.Layer.enemy, new MovingObstacle(dir, 200f, 2f, left + 50, top + 50));
            return;
        }

        if (tile == 12){
            scene.add(MainScene.Layer.enemy, new Spike(left + 50f, top + 75f, 100f, 50f)); // x, y, width, height
            return;
        }

    }

    private int getAt(int col, int row, int stageIndex) {
        int idx = row * STAGE_WIDTH + col;
        if (idx >= STAGES[stageIndex].length) return 0;
        return STAGES[stageIndex][idx]; // STAGES[0] 이 1번째 스테이지 이다
    }

    @Override
    public void update() {}

    @Override
    public void draw(Canvas canvas) {}
}
