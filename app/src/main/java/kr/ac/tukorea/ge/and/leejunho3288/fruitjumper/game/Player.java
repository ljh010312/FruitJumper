package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Player extends AnimSprite implements IBoxCollidable {
    private static final float SPEED = 400f;
    private static final float PLAYER_WIDTH_HEIGHT = 100f;
    private float x, y;
    private int moveDir;
    private boolean facingLeft = false; // true면 왼쪽, false면 오른쪽
    private RectF collisionRect = new RectF();
    private static final float GRAVITY = 1500f;
    private float velocityY = 0f;
    private boolean isOnGround = false;
    private static final float JUMP_POWER = 750f;
    private boolean hasDoubleJumped = false;
    private static final float HIT_DURATION = 0.35f; // 7프레임 / 20fps = 0.35초
    private float hitElapsedTime = 0f; // hit 애니메이션 시간 누적
    private boolean invincible = false;
    private float invincibleTime = 0f;
    private static final float INVINCIBLE_DURATION = 1.0f; // hit 상태 포함 1초간 무적


    public enum State {
        idle, move, jump, doubleJump, fall, hit
    }
    protected State state = State.idle;

    public Player(){
        super(R.mipmap.player_idle, 20, 11);
        this.x = Metrics.width / 2;
        this.y = Metrics.height - 200;
        setPosition(x, y, PLAYER_WIDTH_HEIGHT, PLAYER_WIDTH_HEIGHT);
        updateCollisionRect();
    }

    @Override
    public void update() {
        float deltaTime = GameView.frameTime;

        // 무적 시간 업데이트
        if (invincible) {
            invincibleTime += deltaTime;
            if (invincibleTime >= INVINCIBLE_DURATION) {
                invincible = false;
            }
        }

        if (state == State.hit) {
            hitElapsedTime += deltaTime;
            if (hitElapsedTime >= HIT_DURATION) {
                setState(State.idle);
            }
            return; // hit 중엔 다른 로직 무시
        }

        // 중력 적용
        velocityY += GRAVITY * deltaTime;
        y += velocityY * deltaTime;

        // 좌우 이동
        if (moveDir != 0) {
            x += moveDir * SPEED * deltaTime;
        }

        checkPlatformCollision(deltaTime);

        // 충돌 판정
        Platform landedPlatform = checkLanding();
        if (landedPlatform != null) {
            // 바닥에 착지한 경우
            y = landedPlatform.getCollisionRect().top - PLAYER_WIDTH_HEIGHT / 2f;
            velocityY = 0f;
            isOnGround = true;

            if (state == State.fall || state == State.jump) {
                setState(moveDir != 0 ? State.move : State.idle);
            }
        } else {
            // 공중 상태
            if (!isOnGround) {
                if (velocityY > 0 && state != State.fall) {
                    setState(State.fall);
                }
            }
            isOnGround = false;
        }



        setPosition(x, y, PLAYER_WIDTH_HEIGHT, PLAYER_WIDTH_HEIGHT);
        updateCollisionRect();
    }

    private void setState(State newState) {
        if (this.state == newState) return;


        this.state = newState;
        updateCollisionRect();

        switch (newState) {
            case idle:
                setImageResourceId(R.mipmap.player_idle, 20, 11);
                break;
            case move:
                setImageResourceId(R.mipmap.player_run, 20, 12);
                break;
            case jump:
                setImageResourceId(R.mipmap.player_jump, 20, 1);
                break;
            case doubleJump:
                setImageResourceId(R.mipmap.player_double_jump, 20, 6);
                break;
            case fall:
                setImageResourceId(R.mipmap.player_fall, 20, 1);
                break;
            case hit:
                setImageResourceId(R.mipmap.player_hit, 20, 7);
                hitElapsedTime = 0f;
                invincible = true;
                invincibleTime = 0f;
                break;
        }
    }
    public void moveLeft(boolean startMove) {
        if (startMove) {
            facingLeft = true;
            moveDir = -1;
            if (state == State.idle) {
                setState(State.move);
            }
            // 점프 중엔 상태는 유지하고 방향만 바꾼다
        } else {
            if (state == State.move) {
                setState(State.idle);
            }
            moveDir = 0;
        }
    }

    public void moveRight(boolean startMove) {
        if (startMove) {
            facingLeft = false;
            moveDir = 1;
            if (state == State.idle) {
                setState(State.move);
            }
        } else {
            if (state == State.move) {
                setState(State.idle);
            }
            moveDir = 0;
        }
    }
    public void jump() {
        if (isOnGround) {
            velocityY = -JUMP_POWER;
            isOnGround = false;
            hasDoubleJumped = false;
            setState(State.jump);
        } else if (!hasDoubleJumped) {
            velocityY = -JUMP_POWER;
            hasDoubleJumped = true;
            setState(State.doubleJump);
        }
    }

    public void hit(){
        if (isInvincible())
            return;
        setState(State.hit);
    }

    // 무적인지 확인
    public boolean isInvincible() {
        return invincible;
    }
    private void checkPlatformCollision(float deltaTime) {
        for (IGameObject obj : Scene.top().objectsAt(MainScene.Layer.platform)) {
            if (!(obj instanceof Platform)) continue;
            Platform platform = (Platform) obj;

            RectF platRect = platform.getCollisionRect();
            RectF nextRect = new RectF(collisionRect);
            nextRect.offset(moveDir * SPEED * deltaTime, velocityY * deltaTime);

            if (!RectF.intersects(nextRect, platRect)) continue;

            if (platform.canPassFromBelow()) {
                // 아래에서 위로 통과 허용
                if (velocityY < 0 && collisionRect.bottom <= platRect.top) continue;
            }

            // 플랫폼과 충돌 처리
            // 수직 충돌
            if (collisionRect.bottom <= platRect.top && velocityY > 0) {
                // 아래로 떨어질 때 바닥 충돌
                y = platRect.top - PLAYER_WIDTH_HEIGHT / 2f;
                velocityY = 0;
                isOnGround = true;
                if (state == State.fall || state == State.jump || state == State.doubleJump) {
                    setState(moveDir != 0 ? State.move : State.idle);
                }
            } else if (collisionRect.top >= platRect.bottom && velocityY < 0) {
                // 위로 점프하다가 천장 충돌
                y = platRect.bottom + PLAYER_WIDTH_HEIGHT / 2f;
                velocityY = 0;
            } else {
                // 수평 충돌
                if (moveDir > 0 && collisionRect.right <= platRect.left) {
                    x = platRect.left - PLAYER_WIDTH_HEIGHT / 2f;
                } else if (moveDir < 0 && collisionRect.left >= platRect.right) {
                    x = platRect.right + PLAYER_WIDTH_HEIGHT / 2f;
                }
            }

            setPosition(x, y, PLAYER_WIDTH_HEIGHT, PLAYER_WIDTH_HEIGHT);
            updateCollisionRect();
        }
    }

    private Platform checkLanding() {
        float footY = collisionRect.bottom;

        for (IGameObject obj : Scene.top().objectsAt(MainScene.Layer.platform)) {
            if (!(obj instanceof Platform)) continue;
            Platform platform = (Platform) obj;

            RectF rect = platform.getCollisionRect();
            if (rect.left <= x && x <= rect.right) {
                if (footY <= rect.top && footY + velocityY * GameView.frameTime >= rect.top) {
                    if (platform.canPassFromBelow() && velocityY < 0) continue;
                    return platform;
                }
            }
        }

        return null;
    }


    private void updateCollisionRect() {
        collisionRect.set(dstRect);
        collisionRect.inset(20f, 0f);
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    @Override
    public void draw(Canvas canvas) {
        if (facingLeft) {
            canvas.save();
            float px = dstRect.centerX();
            canvas.scale(-1, 1, px, dstRect.centerY()); // X축 기준 좌우 반전
            super.draw(canvas);
            canvas.restore();
        } else {
            super.draw(canvas);
        }
    }
}