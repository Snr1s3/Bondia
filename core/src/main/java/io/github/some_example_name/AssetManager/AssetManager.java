package io.github.some_example_name.AssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;


public class AssetManager {
    public static Texture mono;
    public static Texture coco;
    public static Texture banana;
    public static Texture sandia;
    public static Texture tabac;
    public static Texture totes_fruites;
    public static BitmapFont font;
    public static BitmapFont font2;
    public static Sound tabacSound;
    public static Music musica;
    public static Array<TextureRegion> fruita = new com.badlogic.gdx.utils.Array<TextureRegion>();

    public static void load() {
        mono = new Texture(Gdx.files.internal("imatges/Monkey.jpg"));
        tabac = new Texture(Gdx.files.internal("imatges/Tabaco.png"));
        coco = new Texture(Gdx.files.internal("imatges/Coco.png"));
        sandia = new Texture(Gdx.files.internal("imatges/Sandia.jpg"));
        banana = new Texture(Gdx.files.internal("imatges/Banana.jpg"));
        coco.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sandia.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tabac.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        mono.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font = new BitmapFont(Gdx.files.internal("font/joc.fnt"));
        font2 = new BitmapFont(Gdx.files.internal("font/joc.fnt"));
        tabacSound = Gdx.audio.newSound(Gdx.files.internal("sons/Succes.mp3"));
        musica = Gdx.audio.newMusic(Gdx.files.internal("sons/musicfons.mp3"));
        totes_fruites = new Texture(Gdx.files.internal("imatges/htc7hj6ctff21-removebg-preview.png"));
        TextureRegion[][] tmp = TextureRegion.split(totes_fruites, 95, 95);
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                fruita.add(tmp[i][j]);
            }
        }
    }

    public static void dispose() {
        mono.dispose();
        tabac.dispose();
        coco.dispose();
        sandia.dispose();
        font.dispose();
        tabacSound.dispose();
        musica.dispose();
    }
}
