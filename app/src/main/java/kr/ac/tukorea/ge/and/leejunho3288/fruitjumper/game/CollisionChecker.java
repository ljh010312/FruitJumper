package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.CollisionHelper;

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
    }

    private void checkEnemyCollision() {
        ArrayList<IGameObject> obstacles = scene.objectsAt(MainScene.Layer.enemy);
        for (int i = obstacles.size() - 1; i >= 0; i--) {
            IGameObject gobj = obstacles.get(i);
            if (!(gobj instanceof MovingObstacle)) {
                continue;
            }
            MovingObstacle obstacle = (MovingObstacle) gobj;
            if (CollisionHelper.collides(player, obstacle)) {
                player.hit();
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
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {}
}
