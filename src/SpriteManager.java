import org.newdawn.slick.opengl.Texture;

import java.util.*;

public class SpriteManager {
    private ArrayList<Sprite> staticSprites = new ArrayList<Sprite>();
    private ArrayList<AnimationSpriteData> animationSpriteDatas = new ArrayList<AnimationSpriteData>();
    public static final String TEXTURE_PATH = Main.PATH + "textures" + Main.SEPARATOR;
    public static final String ANIMATION_PATH = Main.PATH + "animation" + Main.SEPARATOR;

    public Texture addSprite(Sprite s) {
        staticSprites.add(s);
        if(Main.LOG)  System.out.println("Спрайт " + s.getName() + " загружен.");
        return s.getTexture();
    }

    public Texture addSprite(String name, String path) {
        return addSprite(new Sprite(name, path));
    }

    public Texture addSprite(String name) {
        return addSprite(name, TEXTURE_PATH + name + ".png");
    }


    public Sprite getSprite(String name) {
        for(Sprite s : staticSprites)
            if(s.getName().equals(name)) return s;

        System.out.println("Спрайт " + name + " не загружен");
        Main.clear();
        return null;
    }

    public AnimationSpriteData getAnimationData(String name) {
        if(animationSpriteDatas.size() > 0)
            for(AnimationSpriteData s : animationSpriteDatas)
                if(s.getName().equals(name)) return s;

        System.out.println("Анимация " + name + " не загружена");
        Main.clear();
        return null;
    }

    private AnimationSpriteData addAnimationSprite(String name, String path) {
        animationSpriteDatas.add(new AnimationSpriteData(name, path));
        return animationSpriteDatas.get(animationSpriteDatas.size() - 1);
    }

    public void addToAnimation(String name, String path) {
        AnimationSpriteData anim = null;
        if(animationSpriteDatas.size() > 0)
            for(AnimationSpriteData a : animationSpriteDatas)
                if(a.getName().equals(name))
                    anim = a;
        if(anim == null) anim = addAnimationSprite(name, path);
        System.out.println("add" + path);
        anim.addTexture(path);
    }
}
