package com.star.app.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.star.app.game.helpers.ObjectPool;

public class AsteroidController extends ObjectPool<Asteroid> {
    private Texture asteroidTexture;
    private Asteroid asteroid;
    private float asteroidTimer;

    @Override
    protected Asteroid newObject() {
        asteroid = new Asteroid();
        return asteroid;
    }

    public Asteroid getAsteroid() {
        return asteroid;
    }

    public AsteroidController() {
        this.asteroidTexture = new Texture("asteroid.png");
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            Asteroid a = activeList.get(i);
            batch.draw(asteroidTexture, a.getPosition().x - 128, a.getPosition().y - 128);
        }
    }

    public void setup() {
        getActiveElement().activate();
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        checkPool();
        //выпускаем астероиды по нажатию клавишы "O"
        asteroidTimer += dt;
        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            if (asteroidTimer > 0.2f) {
                asteroidTimer = 0.0f;
                setup();
            }
        }
    }
}
