package daniel.m8.uf3.helpers;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Fruita {
    public Rectangle rectangle;
    public TextureRegion textura;

    public Fruita(Rectangle rectangle, TextureRegion textura) {
        this.rectangle = rectangle;
        this.textura = textura;
    }
}
