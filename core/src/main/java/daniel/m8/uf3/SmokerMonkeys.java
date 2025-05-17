package daniel.m8.uf3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import daniel.m8.uf3.helpers.AssetManager;
import daniel.m8.uf3.screens.InitialScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class SmokerMonkeys extends Game {
    private static final String TAG = "SmokerMonkey: ";

    @Override
    public void create() {
        Gdx.app.log(TAG, "SmokerMonkey:  Game Created");

        try {
            Gdx.app.log(TAG, "SmokerMonkey: Loading assets...");
            AssetManager.load();
            Gdx.app.log(TAG, "SmokerMonkey: Assets loaded successfully.");
        } catch (Exception e) {
            Gdx.app.error(TAG, "SmokerMonkey: Error loading assets: " + e.getMessage(), e);
        }

        setScreen(new InitialScreen(this));
        Gdx.app.log(TAG, "SmokerMonkey: Initial screen set.");
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        Gdx.app.log(TAG, "SmokerMonkey: Window resized to: " + width + "x" + height);
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "SmokerMonkey: Disposing game resources...");
        AssetManager.dispose(); // Dispose of assets
        Gdx.app.log(TAG, "SmokerMonkey: Game resources disposed.");
    }
}
