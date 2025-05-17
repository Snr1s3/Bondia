package io.github.some_example_name.Screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import io.github.some_example_name.AssetManager.AssetManager;
import io.github.some_example_name.Helpers.Fruita;

public class MainScreen implements Screen {
    private int faseActual = 1;  // Fase inicial
    private float tempsCanviFase = 0;  // Temps per mostrar el text
    private String missatgeFase = "";  // El missatge que es mostrarà al jugador
    private int fase = 1;
    private float tempsTotal = 0;
    private int tabacsPerduts = 0;
    private boolean jocAcabat = false;
    private Game joc;
    private Batch batch;
    private OrthographicCamera camera;
    private Rectangle mono;
    private Array<Fruita> fruites;
    private Array<Rectangle> tabacs;
    private Texture background = new Texture(Gdx.files.internal("imatges/fons.jpg"));
    private long momentoUltimaTabac;
    private long momentoUltimaFruita;
    private long tabacsRecogidas;

    public MainScreen(Game joc, Batch batch, OrthographicCamera camera) {
        this.joc = joc;
        this.batch = batch;
        this.camera = camera;

        crearMonkey();
        crearTabac();
        crearFruita();
        AssetManager.musica.play();
    }

    private void crearFruita(){
        fruites = new Array<Fruita>();
        generarFruita();
    }
    private void generarFruita() {
        Rectangle fruitaRect = new Rectangle();
        fruitaRect.x = MathUtils.random(0, 1024 - 32);
        fruitaRect.y = 768;
        fruitaRect.width = 128;
        fruitaRect.height = 128;

        TextureRegion texturaAleatoria = AssetManager.fruita.random();
        fruites.add(new Fruita(fruitaRect, texturaAleatoria));

        momentoUltimaFruita = TimeUtils.nanoTime();
    }
    private void crearTabac() {
        tabacs = new Array<Rectangle>();
        generarTabacs();
    }

    private void generarTabacs() {
        Rectangle tabac = new Rectangle();
        tabac.x = MathUtils.random(0, 1024 - 32);
        tabac.y = 768;
        tabac.width = 64;
        tabac.height = 64;
        tabacs.add(tabac);
        momentoUltimaTabac = TimeUtils.nanoTime();
    }
    private void crearMonkey(){
        mono = new Rectangle();
        mono.x = 500 / 2 - 32 / 2;
        mono.y = 20;
        mono.width = 128;
        mono.height = 128;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.3f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  // Draw background
// Draw other stuff here
        batch.end();
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(AssetManager.mono, mono.x, mono.y, mono.width, mono.height);
        for(Fruita fruita : fruites)
            batch.draw(fruita.textura, fruita.rectangle.x, fruita.rectangle.y, fruita.rectangle.width, fruita.rectangle.height);

        for (Rectangle tabac : tabacs)
            batch.draw(AssetManager.tabac, tabac.x, tabac.y, tabac.width, tabac.height);

        AssetManager.font.draw(batch, tabacsRecogidas + " punts", 1024 - 230, 768 - 50);

        batch.end();

        if (Gdx.input.isTouched()) {
            Vector3 posicion = new Vector3();
            posicion.set(Gdx.input.getX(), Gdx.input.getY(), 0);

            camera.unproject(posicion);
            mono.x = posicion.x - 32 /2;
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            mono.x -= 2500 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            mono.x += 2500 * Gdx.graphics.getDeltaTime();

        if (mono.x < 0)
            mono.x = 0;

        if (mono.x > 1024 - 32)
            mono.x = 1024 - 32;

        tempsTotal += delta;

        if ((int)tempsTotal % 15 == 0 && fase < 10) {
            fase++;
        }
        long intervalTabac = 1000000000 / faseActual;  // Es redueix a mesura que augmenta la fase

        if (TimeUtils.nanoTime() - momentoUltimaTabac > intervalTabac) {
            generarTabacs();
        }
        if (TimeUtils.nanoTime() - momentoUltimaFruita > 2_000_000_000L)
            generarFruita();

        Iterator<Rectangle> iter = tabacs.iterator();

        while (iter.hasNext()) {
            Rectangle tabac = iter.next();
            tabac.y -= 200 * Gdx.graphics.getDeltaTime();
            if (tabac.y + tabac.height < 0) {
                iter.remove();
                tabacsPerduts++;

                if (tabacsPerduts >= 5) {
                    jocAcabat = true;
                }
            }
            if (tabac.overlaps(mono)) {
                AssetManager.tabacSound.play();
                iter.remove();
                tabacsRecogidas++;

// Mostrar el missatge només mentre el temps ho permeti
                if (tempsCanviFase > 0) {
                     // Centrar el missatge
                    tempsCanviFase -= delta;  // Descomptar el temps que queda
                }
            }
        }
        AssetManager.font.draw(batch, missatgeFase, 500/ 2 - 100, 768 / 2);
        Iterator<Fruita> iterFruita = fruites.iterator();
        while (iterFruita.hasNext()) {
            Fruita fruita = iterFruita.next();
            fruita.rectangle.y -= 200 * Gdx.graphics.getDeltaTime();

            if (fruita.rectangle.y + fruita.rectangle.height < 0)
                iterFruita.remove();

            if (fruita.rectangle.overlaps(mono)) {
                iterFruita.remove();
                tabacsRecogidas--;
            }
        }
        if (jocAcabat) {
            AssetManager.musica.stop();
            joc.setScreen(new DeathScreen(joc,tabacsRecogidas));
        }
    }
    @Override
    public void show() {

    }
    @Override
    public void resize(int width, int height) {

    }
    @Override
    public void pause() {

    }
    @Override
    public void resume() {

    }
    @Override
    public void hide() {

    }
    @Override
    public void dispose() {

    }

    public void setScreen(MainScreen mainScreen) {
    }
}
