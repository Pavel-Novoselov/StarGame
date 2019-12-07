package com.star.app.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.star.app.StarGame;
import com.star.app.game.helpers.Poolable;
import com.star.app.screen.ScreenManager;
import com.star.app.screen.utils.Assets;

public class Bot implements Poolable {
    private GameController gc;
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private int hpMax;
    private int hp;
    private float scale;
    private float angle;
    private boolean active;
    private Circle hitArea;
    private float fireTimer;

    private final float BASE_SIZE = 64.0f;
    private final float BASE_RADIUS = BASE_SIZE / 2.0f;

    public int getHpMax() {
        return hpMax;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Circle getHitArea() {
        return hitArea;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public float getScale() {
        return scale;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

    public Bot(GameController gc) {
        this.gc = gc;
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.hitArea = new Circle(0, 0, 0);
        this.active = false;
        this.texture = new Texture("images/ufo.png");
    }

    public boolean takeDamage(int amount) {
        hp -= amount;
        if (hp <= 0) {
            deactivate();
            return true;
        }
        return false;
    }

    public void activate(float x, float y, float vx, float vy, float scale) {
        this.position.set(x, y);
        this.velocity.set(vx, vy);
        if (this.velocity.len() < 50.0f) {
            this.velocity.nor().scl(50.0f);
        }
        this.hpMax = 20;
        this.hp = this.hpMax;
        this.angle = MathUtils.random(0.0f, 360.0f);
        this.hitArea.setPosition(position);
        this.active = true;
        this.scale = scale;
        this.hitArea.setRadius(BASE_RADIUS *  0.9f);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x-32, position.y-32);
        if(position.x > GameController.SPACE_WIDTH - ScreenManager.HALF_SCREEN_WIDTH) {
            batch.draw(texture, position.x - 32 - GameController.SPACE_WIDTH, position.y - 32);
        }
        if(position.x < ScreenManager.HALF_SCREEN_WIDTH) {
            batch.draw(texture, position.x - 32 + GameController.SPACE_WIDTH, position.y - 32);
        }
    }

    public void update(float dt) {
//всегда двигается к Герою
        this.velocity.set(gc.getHero().getPosition()).sub(this.position);

        position.mulAdd(velocity, dt);
        if (position.x < -BASE_RADIUS * scale) {
            position.x = GameController.SPACE_WIDTH + BASE_RADIUS * scale;
        }
        if (position.x > GameController.SPACE_WIDTH + BASE_RADIUS * scale) {
            position.x = -BASE_RADIUS * scale;
        }
        if (position.y < -BASE_RADIUS * scale) {
            position.y = GameController.SPACE_HEIGHT + BASE_RADIUS * scale;
        }
        if (position.y > GameController.SPACE_HEIGHT + BASE_RADIUS * scale) {
            position.y = -BASE_RADIUS * scale;
        }
        hitArea.setPosition(position);

//бот постоянно стреляет в героя
            fireTimer += dt;
                if (fireTimer > 0.2f) {
                    fireTimer = 0.0f;
                    gc.getBulletController().setup(position.x, position.y, velocity.x*2, velocity.y*2, angle, "bot");

                }
    }
}