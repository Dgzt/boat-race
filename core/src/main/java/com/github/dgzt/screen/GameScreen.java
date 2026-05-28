package com.github.dgzt.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.github.dgzt.mundus.plugin.joltphysics.runtime.JoltPhysicsPlugin;
import com.mbrlabs.mundus.commons.Scene;
import com.mbrlabs.mundus.runtime.Mundus;

public class GameScreen extends ScreenAdapter {

    private static final String SCENE_NAME = "Main Scene.mundus";

    private final Scene scene;

    public GameScreen(final Mundus mundus) {
        scene = mundus.loadScene(SCENE_NAME);
        scene.cam.position.set(0, 40, 0);
    }

    @Override
    public void render(float delta) {
        JoltPhysicsPlugin.update(delta);

        scene.sceneGraph.update(delta);
        scene.render(delta);
    }

    @Override
    public void hide() {
        JoltPhysicsPlugin.dispose();
    }
}
