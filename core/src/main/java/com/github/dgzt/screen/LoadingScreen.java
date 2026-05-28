package com.github.dgzt.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.dgzt.BoatRace;

public class LoadingScreen extends ScreenAdapter {

    private static final Color LOADING_BAR_COLOR = new Color(0x00b695ff);

    private final BoatRace game;
    private final ShapeRenderer shapeRenderer;

    public LoadingScreen(final BoatRace game) {
        this.game = game;
        final var guiCamera = new OrthographicCamera();
        guiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(guiCamera.combined);
    }

    @Override
    public void render(float delta) {
        final var mundus = game.getMundus();

        // Render progress bar
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(0f, Gdx.graphics.getHeight() * .5f, Gdx.graphics.getWidth(), 20f);
        shapeRenderer.setColor(LOADING_BAR_COLOR);
        shapeRenderer.rect(0f, Gdx.graphics.getHeight() * .5f, mundus.getProgress() * Gdx.graphics.getWidth(), 20f);
        shapeRenderer.end();

        if (mundus.continueLoading()) {
            // Loading complete, load game scene.
            final var gameScreen = new GameScreen(mundus);
            game.setScreen(gameScreen);
        }
    }

    @Override
    public void hide() {
        shapeRenderer.dispose();
    }
}
