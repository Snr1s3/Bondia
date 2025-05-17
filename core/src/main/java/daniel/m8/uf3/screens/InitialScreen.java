package daniel.m8.uf3.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import daniel.m8.uf3.SmokerMonkeys;
import com.badlogic.gdx.graphics.Color;

import daniel.m8.uf3.helpers.AssetManager;


public class InitialScreen implements Screen {
    private final Game joc;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture background = new Texture(Gdx.files.internal("imatges/fons.jpg"));
    public InitialScreen(Game joc) {
        this.joc = joc;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1024, 768);
    }
@Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  // Draw background
        batch.end();
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        AssetManager.font.getData().setScale(1.5f);
        AssetManager.font.draw(batch, "SMOKER MONKEY\nClick per començar", 70, 500);
        AssetManager.font.getData().setScale(1f);
        batch.end();

        if (Gdx.input.justTouched()) {
            joc.setScreen(new MainScreen(joc, batch, camera));
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
    }
}
