package com.star.app.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.star.app.game.Background;
import com.star.app.game.GameController;
import com.star.app.game.Hero;
import com.star.app.screen.utils.Assets;
import com.star.app.screen.utils.OptionsUtils;

public class GameOverScreen extends AbstractScreen {
        private Background background;
        private BitmapFont font72;
        private BitmapFont font24;
        private Stage stage;

        public GameOverScreen(SpriteBatch batch) {
            super(batch);
        }

        @Override
        public void show() {
            this.background = new Background(null);
            this.stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
            this.font72 = Assets.getInstance().getAssetManager().get("fonts/font72.ttf");
            this.font24 = Assets.getInstance().getAssetManager().get("fonts/font24.ttf");

            Gdx.input.setInputProcessor(stage);

//            Skin skin = new Skin();
//            skin.addRegions(Assets.getInstance().getAtlas());
//            skin.dispose();

            if (!OptionsUtils.isOptionsExists()) {
                OptionsUtils.createDefaultProperties();
            }
        }

        public void update(float dt) {
            background.update(dt);
            stage.act(dt);
            //по клику выходим в МЕНЮ
            if (Gdx.input.isButtonJustPressed(0)){
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);
            }
        }

        @Override
        public void render(float delta) {
            update(delta);
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            background.render(batch);
            font72.draw(batch, "Game is Over", 0, 600, 1280, 1, false);
            font24.draw(batch, "Your Score is " + Hero.resultScore, 0, 400, 1280, 1, false);
            font24.draw(batch, "Click on screen to go to MENU", 0, 350, 1280, 1, false);
            batch.end();
            stage.draw();
        }

        @Override
        public void dispose() {
            background.dispose();
        }
}
