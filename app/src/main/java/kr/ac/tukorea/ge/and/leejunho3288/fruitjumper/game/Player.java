package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.Sound;
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
    private int maxHp = 5;
    private int hp = 5;
    private State prevState = State.idle;

    public float getVelocity() {
        return velocityY;
    }


    public enum State {
        idle, move, jump, doubleJump, fall, hit
    }
    protected State state = State.idle;

    public Player(){
        super(R.mipmap.player_idle, 20, 11);
        this.x = 100;
        this.y = Metrics.height * 2 - 200;
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
                setState(prevState);
            }
            return; // hit 중엔 다른 로직 무시
        }

        // 항상 먼저 땅 위가 아니라고 가정 → 충돌 시 다시 true로 됨
        isOnGround = false;

        // 중력 적용
        velocityY += GRAVITY * deltaTime;
        y += velocityY * deltaTime;

        // 좌우 이동
        if (moveDir != 0) {
            x += moveDir * SPEED * deltaTime;
        }

        checkPlatformCollision(deltaTime);

        // 낙하 상태 진입 판단
        if (!isOnGround && velocityY > 0 && state != State.fall && state != State.jump ) {
            setState(State.fall);
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
            Sound.playEffect(R.raw.jump1);
        } else if (!hasDoubleJumped) {
            velocityY = -JUMP_POWER;
            hasDoubleJumped = true;
            setState(State.doubleJump);
            Sound.playEffect(R.raw.jump1);
        }
    }

    public void bounce(){
        velocityY = -JUMP_POWER * 0.5f;
        isOnGround = false;
        hasDoubleJumped = false;
        setState(State.jump);
        Sound.playEffect(R.raw.jump1);
    }

    public void hit(){
        if (isInvincible())
            return;
        Sound.playEffect(R.raw.hurt);
        hp = Math.max(hp - 1, 0);
        HealthHud.get().setHp(hp, maxHp); // ← 여기에서 HUD 자동 업데이트
        prevState = state;
        setState(State.hit);
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
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

            // ONE_WAY 플랫폼은 수평/상단 충돌 무시
            if (platform.canPassFromBelow()) {
                // 1. 바닥 충돌만 허용
                boolean falling = velocityY > 0;
                boolean abovePlatform = collisionRect.bottom <= platRect.top;
                boolean willLand = collisionRect.bottom + velocityY * deltaTime >= platRect.top;

                if (!(falling && abovePlatform && willLand)) {
                    continue; // 아래에서 위로 진입 중이 아니면 무시
                }
            }

            // 충돌 체크
            RectF nextRect = new RectF(collisionRect);
            nextRect.offset(moveDir * SPEED * deltaTime, velocityY * deltaTime);

            if (!RectF.intersects(nextRect, platRect)) continue;

            // 수직 충돌
            if (collisionRect.bottom <= platRect.top && velocityY > 0) {
                // 아래로 낙하 중 바닥에 충돌
                y = platRect.top - PLAYER_WIDTH_HEIGHT / 2f;
                velocityY = 0;
                isOnGround = true;
                if (state == State.fall || state == State.jump || state == State.doubleJump) {
                    setState(moveDir != 0 ? State.move : State.idle);
                }
            } else if (collisionRect.top >= platRect.bottom && velocityY < 0) {
                // 천장에 머리 부딪힘
                if (!platform.canPassFromBelow()) { // SOLID만 막음
                    y = platRect.bottom + PLAYER_WIDTH_HEIGHT / 2f;
                    velocityY = 0;
                }
            } else {
                // 좌우 충돌 - ONE_WAY는 무시, SOLID만 막음
                if (!platform.canPassFromBelow()) {
                    if (moveDir > 0 && collisionRect.right <= platRect.left) {
                        x += -1 * SPEED * deltaTime;
                    } else if (moveDir < 0 && collisionRect.left >= platRect.right) {
                        x += 1 * SPEED * deltaTime;
                    }
                }
            }

            setPosition(x, y, PLAYER_WIDTH_HEIGHT, PLAYER_WIDTH_HEIGHT);
            updateCollisionRect();
        }
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
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        int frameIndex = Math.round(time * fps) % frameCount;
        srcRect.set(frameIndex * frameWidth, 0, (frameIndex + 1) * frameWidth, frameHeight);

        // 화면 좌표로 보정
        RectF screenDst = new RectF(dstRect);
        screenDst.offset(-Metrics.cameraX, -Metrics.cameraY); // 카메라 보정

        // 좌우 반전 처리
        if (facingLeft) {
            canvas.save();
            float px = screenDst.centerX();
            canvas.scale(-1, 1, px, screenDst.centerY()); // X축 기준 좌우 반전
            canvas.drawBitmap(bitmap, srcRect, screenDst, null);
            canvas.restore();
        } else {
            canvas.drawBitmap(bitmap, srcRect, screenDst, null);
        }
    }
}