package com.star.app.game;

public class GameController {
    private Background background;
    private BulletController bulletController;
    private Hero hero;
    private AsteroidController asteroidController;

    public BulletController getBulletController() {
        return bulletController;
    }

    public Background getBackground() {
        return background;
    }

    public Hero getHero() {
        return hero;
    }

    public AsteroidController getAsteroidController() {
        return asteroidController;
    }

    public GameController() {
        this.background = new Background(this);
        this.hero = new Hero(this);
        this.bulletController = new BulletController();
        this.asteroidController = new AsteroidController();
 //       asteroidController.setup();
    }

    public void update(float dt) {
        background.update(dt);
        hero.update(dt);
        bulletController.update(dt);
        asteroidController.update(dt);
        checkCollisions();
    }

    // попадание в астероид (ДЗ)
    public void checkCollisions() {
        //проверка попадания в астероид
        for (int i = 0; i < bulletController.getActiveList().size(); i++) {
            Bullet b = bulletController.getActiveList().get(i);
            for (int j = 0; j < asteroidController.getActiveList().size(); j++) {
                Asteroid a = asteroidController.getActiveList().get(j);
                if (a.getPosition().dst(b.getPosition()) < 128.0f) { // 128.0f - примерно диаметр астероида
                    a.deactivate();
                    // считаем что попали,  астероид уничножен
                }
            }
        }
        //проверка столкновения астероида и корабля
        for (int i = 0; i < asteroidController.getActiveList().size(); i++) {
            Asteroid a = asteroidController.getActiveList().get(i);
            if (a.getPosition().dst(hero.getPosition()) < 128.0f) { // 128.0f - примерно диаметр астероида
                    a.deactivate();
                    // считаем что столкнулись, астероид уничножен
            }
        }
    }
}
