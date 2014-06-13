import org.newdawn.slick.opengl.Texture;

public class Sprite {
    protected Texture texture;
    protected String name;

    public String getName() {
        return name;
    }
    public Texture getTexture() {
        return texture;
    }

    public Sprite(String name, String path) {
        this.name = name;
        texture = Engine.addPngTexture(path);
    }
}
