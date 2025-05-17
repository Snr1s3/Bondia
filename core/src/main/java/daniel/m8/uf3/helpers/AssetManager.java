package daniel.m8.uf3.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;
import daniel.m8.uf3.SmokerMonkeys;

public class AssetManager {
    public static Texture mono;
    public static Texture tabac;
    public static Texture totes_fruites;
    public static BitmapFont font;
    public static BitmapFont font2;
    public static Sound tabacSound;
    public static Music musica;
    public static Animation<TextureRegion> animacioEsquerra;
    public static Animation<TextureRegion> animacioDreta;
    public static Array<TextureRegion> fruita = new com.badlogic.gdx.utils.Array<TextureRegion>();


    public static void load() {
        mono = new Texture(Gdx.files.internal("imatges/Mono.png"));
        tabac = new Texture(Gdx.files.internal("imatges/Tabaco.png"));
        tabac.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font = new BitmapFont(Gdx.files.internal("font/joc.fnt"));
        font2 = new BitmapFont(Gdx.files.internal("font/joc.fnt"));
        tabacSound = Gdx.audio.newSound(Gdx.files.internal("sons/Succes.mp3"));
        musica = Gdx.audio.newMusic(Gdx.files.internal("sons/musicfons.mp3"));
        totes_fruites = new Texture(Gdx.files.internal("imatges/Fruites.png"));
        TextureRegion[][] tmp = TextureRegion.split(totes_fruites, 95, 95);
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                fruita.add(tmp[i][j]);
            }
        }
        tmp = TextureRegion.split(mono, 32, 32);
        // Frames para la derecha (sin modificar)
        Array<TextureRegion> framesDreta = new Array<>();
        for (int i = 0; i < 2; i++) {
            framesDreta.add(tmp[0][i]);
        }

// Frames para la izquierda (volteados)
        Array<TextureRegion> framesEsquerra = new Array<>();
        for (int i = 2; i < 4; i++) {
            TextureRegion flipped = new TextureRegion(tmp[0][i]);
            framesEsquerra.add(flipped);
        }
        animacioDreta = new Animation<>(0.1f, framesDreta, Animation.PlayMode.LOOP);
        animacioEsquerra = new Animation<>(0.1f, framesEsquerra, Animation.PlayMode.LOOP);

    }
    public static void loadAnims() {
        Texture sheet = new Texture(Gdx.files.internal("imatges/Mono.png"));

        TextureRegion[][] tmp = TextureRegion.split(sheet, 52, 64);
        TextureRegion[] framesDreta = new TextureRegion[tmp[0].length];
        for (int i = 0; i < tmp[0].length; i++) {
            framesDreta[i] = tmp[0][i];

            framesDreta[i].flip(true, false);
        }

        animacioDreta = new Animation<>(0.1f, framesDreta);

        TextureRegion[] framesEsquerra = new TextureRegion[framesDreta.length];
        for (int i = 0; i < framesDreta.length; i++) {
            framesEsquerra[i] = new TextureRegion(framesDreta[i]);

            framesEsquerra[i].flip(true, false);
        }

        animacioEsquerra = new Animation<>(0.1f, framesEsquerra);
    }

    public static void dispose() {
        mono.dispose();
        tabac.dispose();
        font.dispose();
        tabacSound.dispose();
        musica.dispose();
    }
}
