package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.RectF;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;

public class Checkpoint extends AnimSprite implements IBoxCollidable {
    private final RectF collisionRect = new RectF();
    private boolean activated = false;

    public boolean getActive() {
        return activated;
    }

    public enum State { IDLE, OUT, NOFLAG }
    private State state = State.IDLE;

    public Checkpoint(float x, float y, float width, float height) {
        super(R.mipmap.checkpoint_flag_idle_64x64, 20, 10);
        setPosition(x, y, width, height);
        updateCollisionRect();
    }

    @Override
    public void update() {
        super.update();
        updateCollisionRect();

        if (state == State.OUT && getCurrentFrame() >= getFrameCount() - 1) {
            setImageResourceId(R.mipmap.checkpoint_no_flag, 1, 1);
            state = State.NOFLAG; // 더 이상 상태 전환 없음
        }
    }

    private void updateCollisionRect() {
        collisionRect.set(dstRect);
        collisionRect.inset(20f, 20f);
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    public void activate() {
        if (activated) return;
        activated = true;

        setImageResourceId(R.mipmap.checkpoint_flag_out_64x64, 20, 26);
        state = State.OUT;




    }
}
