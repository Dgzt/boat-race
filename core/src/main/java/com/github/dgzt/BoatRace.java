package com.github.dgzt;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.dgzt.mundus.plugin.joltphysics.runtime.InitResult;
import com.github.dgzt.mundus.plugin.joltphysics.runtime.JoltPhysicsPlugin;
import com.github.dgzt.mundus.plugin.joltphysics.runtime.converter.JoltPhysicsComponentConverter;
import com.github.dgzt.screen.LoadingScreen;
import com.mbrlabs.mundus.commons.assets.meta.MetaFileParseException;
import com.mbrlabs.mundus.runtime.Mundus;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class BoatRace extends Game {

    private Mundus mundus;

    @Override
    public void create() {
        final var config = new Mundus.Config();
        config.autoLoad = false; // Do not autoload, we want to queue custom assets
        config.asyncLoad = true; // Do asynchronous loading

        // Start asynchronous loading
        mundus = new Mundus(Gdx.files.internal("boat-race"), config, new JoltPhysicsComponentConverter());
        try {
            mundus.getAssetManager().queueAssetsForLoading(true);
        } catch (MetaFileParseException e) {
            e.printStackTrace();
        }

        final InitResult initResult = JoltPhysicsPlugin.init();
        Gdx.app.log("", "Jolt Physics loaded: " + initResult.isSuccess());
        if (!initResult.isSuccess()) {
            Gdx.app.error("", "Jolt Physics can not load", initResult.getException());
        }

        final var loadingScene = new LoadingScreen(this);
        setScreen(loadingScene);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK, true);
        super.render();
    }

    @Override
    public void dispose() {
        mundus.dispose();
    }

    public Mundus getMundus() {
        return mundus;
    }
}
