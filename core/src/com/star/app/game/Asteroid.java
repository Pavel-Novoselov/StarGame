package com.star.app.game;

import com.badlogic.gdx.math.Vector2;
import com.star.app.game.helpers.Poolable;
import com.star.app.screen.ScreenManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


public class Asteroid implements Poolable{
    private Vector2 position;
    private Vector2 velocity;
    private boolean active;
    private float angle;
    private final int SPEED = 260;

    public Vector2 getPosition() {
        return position;
    }

    public Asteroid(){
        this.position = new Vector2(MathUtils.random(0, ScreenManager.SCREEN_WIDTH), MathUtils.random(0, ScreenManager.SCREEN_HEIGHT));
        this.velocity = new Vector2(MathUtils.random(-200, 200), MathUtils.random(-200, 200));
        this.active = false;
    }

    public void activate() {
        position.set(MathUtils.random(0, ScreenManager.SCREEN_WIDTH), MathUtils.random(0, ScreenManager.SCREEN_HEIGHT));
        velocity.set(MathUtils.random(-200, 200), MathUtils.random(-200, 200));
        active = true;
    }

    public void update(float dt) {
        position.mulAdd(velocity, dt);
        if (position.x < -128.0f || position.x > ScreenManager.SCREEN_WIDTH+128.0f || position.y < -128.0f || position.y > ScreenManager.SCREEN_HEIGHT+128.0f) {
            deactivate();
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

}