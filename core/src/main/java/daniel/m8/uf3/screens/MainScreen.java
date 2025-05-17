package daniel.m8.uf3.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import daniel.m8.uf3.helpers.AssetManager;
import daniel.m8.uf3.helpers.Fruita;


public class MainScreen implements Screen {
    private int faseActual = 1;
    private float tempsCanviFase = 0;
    private String missatgeFase = "";
    private int fase = 1;
    private float tempsTotal = 0;
    private int ultimaFaseTemps = -1;

    private int tabacsPerduts = 0;
    private boolean jocAcabat = false;
    private Game joc;
    private Batch batch;
    private OrthographicCamera camera;
    private Rectangle mono;
    private Array<Fruita> fruites;
    private Array<Rectangle> tabacs;
    private long momentoUltimaTabac;
    private long momentoUltimaFruita;
    private long tabacsRecogidas;
    private Animation<TextureRegion> animacioDreta;
    private Animation<TextureRegion> animacioEsquerra;
    private float elapsedTime;
    private boolean mirantDreta = true;
    private Texture background = new Texture(Gdx.files.internal("imatges/fons.jpg"));


    public MainScreen(Game joc, Batch batch, OrthographicCamera camera) {
        this.joc = joc;
        this.batch = batch;
        this.camera = camera;
        AssetManager.loadAnims();
        animacioDreta = AssetManager.animacioDreta;
        animacioEsquerra = AssetManager.animacioEsquerra;
        crearMonkey();
        crearTabac();
        crearFruita();
        AssetManager.musica.setVolume(0.1f);
        AssetManager.musica.setLooping(true);
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        TextureRegion frameActual = mirantDreta
            ? animacioDreta.getKeyFrame(elapsedTime, true)
            : animacioEsquerra.getKeyFrame(elapsedTime, true);

        batch.draw(frameActual, mono.x, mono.y, mono.width, mono.height);
        for(Fruita fruita : fruites)
            batch.draw(fruita.textura, fruita.rectangle.x, fruita.rectangle.y, fruita.rectangle.width, fruita.rectangle.height);

        for (Rectangle tabac : tabacs)
            batch.draw(AssetManager.tabac, tabac.x, tabac.y, tabac.width, tabac.height);

        AssetManager.font.draw(batch, tabacsRecogidas + " punts", 1024 - 275, 768 - 50);
        AssetManager.font.draw(batch, "Vides: " + (5 - tabacsPerduts), 50, 768 - 50);

        batch.end();

        boolean moving = false;

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            mono.x -= 2500 * delta;
            mirantDreta = false;
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            mono.x += 2500 * delta;
            mirantDreta = true;
            moving = true;
        }

        if (Gdx.input.isTouched()) {
            Vector3 posicion = new Vector3();
            posicion.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(posicion);

            if (Math.abs(mono.x - posicion.x) > 1f) {
                mirantDreta = posicion.x > mono.x;
                moving = true;
            }

            mono.x = posicion.x - 32 / 2;
        }

        if (moving) {
            elapsedTime += delta;
        }

        if (mono.x < 0)
            mono.x = 0;

        if (mono.x > 1024 - 32)
            mono.x = 1024 - 32;

        tempsTotal += delta;

        int segons = (int) tempsTotal;
        if (segons % 15 == 0 && segons != ultimaFaseTemps && faseActual < 10) {
            faseActual++;
            missatgeFase = "Fase " + faseActual;
            tempsCanviFase = 3;
            ultimaFaseTemps = segons;
        }

        long intervalTabac = 1000000000 / (faseActual / 2);

        if (TimeUtils.nanoTime() - momentoUltimaTabac > intervalTabac) {
            generarTabacs();
        }
        if (TimeUtils.nanoTime() - momentoUltimaFruita > 2_000_000_000L)
            generarFruita();

        Iterator<Rectangle> iter = tabacs.iterator();

        while (iter.hasNext()) {
            Rectangle tabac = iter.next();
            tabac.y -= (200 + faseActual * 30) * Gdx.graphics.getDeltaTime();
            if (tabac.y + tabac.height < 0) {
                iter.remove();
                tabacsPerduts++;

                if (tabacsPerduts >= 5) {
                    jocAcabat = true;
                }
            }
            if (tabac.overlaps(mono)) {
                AssetManager.tabacSound.play(1.0f);
                iter.remove();
                tabacsRecogidas++;

                if (tabacsRecogidas % 20 == 0 && tabacsRecogidas > 0) {
                    faseActual++;
                    missatgeFase = "Fase " + faseActual;
                    tempsCanviFase = 3;
                }
            }
        }
        if (tempsCanviFase > 0) {
            batch.begin();
            AssetManager.font.draw(batch, missatgeFase, 1024 / 2f - 100, 768 / 2f);
            batch.end();
            tempsCanviFase -= delta;
        }
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
