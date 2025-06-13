package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.ArrayList;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.Sound;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.CollisionHelper;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class CollisionChecker implements IGameObject {
    private final MainScene scene;
    private final Player player;

    public CollisionChecker(MainScene mainScene, Player player) {
        this.scene = mainScene;
        this.player = player;
    }

    @Override
    public void update() {
        checkEnemyCollision();
        checkFruitCollsion();
        checkCheckpointCollision();
    }

    private void checkEnemyCollision() {
        ArrayList<IGameObject> obstacles = scene.objectsAt(MainScene.Layer.enemy);
        for (int i = obstacles.size() - 1; i >= 0; i--) {
            IGameObject gobj = obstacles.get(i);
            if (gobj instanceof MovingObstacle || gobj instanceof Spike) {
                if (CollisionHelper.collides(player, (IBoxCollidable) gobj)) {
                    player.hit();


                }
            }
        }
    }

    private void checkFruitCollsion() {
        ArrayList<IGameObject> fruits = scene.objectsAt(MainScene.Layer.fruit);
        for (int i = fruits.size() - 1; i >= 0; i--) {
            IGameObject gobj = fruits.get(i);
            if (!(gobj instanceof Fruit)) {
                continue;
            }
            Fruit fruit = (Fruit) gobj;
            if (CollisionHelper.collides(player, fruit)) {
                FruitHud.get().addFruit(fruit.getType());
                scene.remove(MainScene.Layer.fruit, fruit);
                Sound.playEffect(R.raw.jelly_alphabet);

            }
        }
    }

    private void checkCheckpointCollision() {
        ArrayList<IGameObject> objs = scene.objectsAt(MainScene.Layer.checkpoint);
        for (IGameObject obj : objs) {
            if (!(obj instanceof Checkpoint)) continue;
            Checkpoint checkpoint = (Checkpoint) obj;

            if (checkpoint.getActive()) continue;

            if (CollisionHelper.collides(player, checkpoint)){
                int fruitCount = FruitHud.get().getCollectedCount();
                if (fruitCount == 0) return; // 과일 없으면 무시
                checkpoint.activate();
                scene.checkpointStarCount = fruitCount;
                FruitHud.get().clear();
            }

        }
    }

    @Override
    public void draw(Canvas canvas) {}


}
