package com.star.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.star.game.ScreenManager.SCREEN_WIDTH;

public class Asteriod {
    private Texture texture;
    private Vector2 position;
    private float angle;
    private final int SPEED = 260;

    public Asteriod(){
        this.texture = new Texture("asteroid.png");
        this.position = new Vector2 (MathUtils.random(0, SCREEN_WIDTH), MathUtils.random(0, ScreenManager.SCREEN_HEIGHT));
        this.angle = (float)MathUtils.random(0, 360);
    }

    public void render (SpriteBatch batch){
        batch.draw(texture, this.position.x-128, this.position.y-128, 128, 128, 256, 256, 1,1, angle, 0,0, 256, 256, false, false);
    }

    public void update (float dt){
        position.x += Math.cos(Math.toRadians(angle)) * SPEED * dt;
        position.y += Math.sin(Math.toRadians(angle)) * SPEED * dt;
        if (position.x < (-128)) {
            position.x = SCREEN_WIDTH+128;
        }
        if (position.x > ScreenManager.SCREEN_WIDTH+128) {
            position.x = (-128);
        }
        if (position.y < (-128)) {
            position.y = ScreenManager.SCREEN_HEIGHT+128;
        }
        if (position.y > ScreenManager.SCREEN_HEIGHT+128) {
            position.y = (-128);
        }
    }


}
