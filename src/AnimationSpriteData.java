import org.newdawn.slick.opengl.Texture;

import java.util.*;

public class AnimationSpriteData {

    private String name;
    private String dirToImages;
    private ArrayList<Texture> frames = new ArrayList<Texture>();

    public int maxFrame() { return frames.size() - 1; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return getName();
    }

    public AnimationSpriteData(String name, String dirToImages) {
        this.name = name;
        this.dirToImages = dirToImages;
        if(Main.LOG) System.out.println("Создана анимация " + name);

    }
    public void addTexture(String path) {
        frames.add(Engine.addPngTexture(path));
    }

    public Texture getFrame(int index) { return frames.get(index); }

    public int getHeight() { return frames.get(0).getImageHeight(); }
    public int getWidth() { return frames.get(0).getImageWidth(); }
}
