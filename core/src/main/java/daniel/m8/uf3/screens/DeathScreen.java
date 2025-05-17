package daniel.m8.uf3.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import daniel.m8.uf3.helpers.AssetManager;


public class DeathScreen implements Screen {

    private final Game joc;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final long record;

    private Texture background = new Texture(Gdx.files.internal("imatges/fons.jpg"));
    private final BitmapFont font;

    public DeathScreen(Game joc, long punts) {
        this.joc = joc;
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 1024, 768);

        Preferences prefs = Gdx.app.getPreferences("punts");
        long savedHighscore = prefs.getLong("highscore", 0);

        if (punts > savedHighscore) {
            prefs.putLong("highscore", punts);
            prefs.flush();
            record = punts;
        } else {
            record = savedHighscore;
        }

        this.font = AssetManager.font;
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

        String scoreText = "Your Points Record is: " + record;
        String promptText = "Tap anywhere to restart";

        float scoreX = (1024 - font.getRegion().getRegionWidth()) / 2f;
        font.draw(batch, scoreText, 200, 400);
        font.draw(batch, promptText, 200, 300);

        batch.end();

        if (Gdx.input.justTouched()) {
            joc.setScreen(new MainScreen(joc, batch, camera));
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
    }
}
