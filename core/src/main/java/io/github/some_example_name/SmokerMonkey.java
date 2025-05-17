package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import io.github.some_example_name.AssetManager.AssetManager;
import io.github.some_example_name.Screens.InitialScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

public class SmokerMonkey extends Game {

    private static final String TAG = "SmokerMonkey: "; // Tag for the log messages

    @Override
    public void create() {
        Gdx.app.log(TAG, "SmokerMonkey:  Game Created"); // Log when the game is created

        try {
            Gdx.app.log(TAG, "SmokerMonkey: Loading assets...");
            AssetManager.load(); // Load assets
            Gdx.app.log(TAG, "SmokerMonkey: Assets loaded successfully.");
        } catch (Exception e) {
            Gdx.app.error(TAG, "SmokerMonkey: Error loading assets: " + e.getMessage(), e); // Log any error
        }

        setScreen(new InitialScreen(this)); // Set initial screen
        Gdx.app.log(TAG, "SmokerMonkey: Initial screen set.");
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        Gdx.app.log(TAG, "SmokerMonkey: Window resized to: " + width + "x" + height); // Log window resize
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "SmokerMonkey: Disposing game resources...");
        AssetManager.dispose(); // Dispose of assets
        Gdx.app.log(TAG, "SmokerMonkey: Game resources disposed.");
    }
}
