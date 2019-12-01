package com.star.app.screen;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.star.app.game.Background;
import com.star.app.game.GameController;
import com.star.app.game.WorldRenderer;
import com.star.app.screen.utils.Assets;
import com.star.app.screen.utils.OptionsUtils;

public class GameScreen extends AbstractScreen {
    private GameController gameController;
    private WorldRenderer worldRenderer;
    private BitmapFont font24;
    private Stage stage;
    private boolean isPause = false;

    public GameScreen(SpriteBatch batch) {
        super(batch);
    }

    public boolean getIsPause() {
        return isPause;
    }

    @Override
    public void show() {
        Assets.getInstance().loadAssets(ScreenManager.ScreenType.GAME);
        this.gameController = new GameController();
        this.worldRenderer = new WorldRenderer(gameController, batch);
        this.stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
        this.font24 = Assets.getInstance().getAssetManager().get("fonts/font32.ttf");
        isPause = false;

        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("smButton");
        textButtonStyle.font = font24;
        skin.add("smButtom", textButtonStyle);


        Button btnPause = new TextButton("Pause", textButtonStyle);
        btnPause.setPosition(20, 500);

        Button btnResume = new TextButton("Resume", textButtonStyle);
        btnPause.setPosition(20, 400);

        Button btnToMenue = new TextButton("To Menu", textButtonStyle);
        btnToMenue.setPosition(20, 300);

        btnPause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pause();
            }
        });

        btnResume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resume();
            }
        });

        btnToMenue.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);
            }
        });

        stage.addActor(btnPause);
        stage.addActor(btnResume);
        stage.addActor(btnToMenue);
        skin.dispose();

        if (!OptionsUtils.isOptionsExists()) {
            OptionsUtils.createDefaultProperties();
        }
    }

    @Override
    public void render(float delta) {
        if (!isPause) {
            gameController.update(delta);
        }
        worldRenderer.render();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void pause() {
        isPause = true;
    }

    @Override
    public void resume() {
        isPause = false;
    }

    @Override
    public void dispose() {
        gameController.dispose();
    }
}
