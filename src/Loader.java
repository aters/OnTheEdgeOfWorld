
import java.io.File;

public class Loader {
    private Game game;

    public Loader(Game game) {
        this.game = game;
    }

    private String getExt(File file) {
        String[] f = file.getName().split("\\.");
        return f[f.length - 1].toString();
    }
//
    private String getFileName(File file) {
        String[] f = file.getName().split("\\.");
        StringBuilder s = new StringBuilder("");
        for(int i = 0; i < f.length - 1; ++i)
            s.append(f[i]);
        return s.toString();
    }

    public void loadTextures(String dir) {
        File fd = preLoad(SpriteManager.TEXTURE_PATH + dir);
        System.out.println(SpriteManager.TEXTURE_PATH + dir);
        for(File f : fd.listFiles()) {
            if(f.isDirectory() || (!getExt(f).toLowerCase().equals("png"))) continue;
            Game.spriteManager.addSprite(getFileName(f), f.getAbsolutePath());
        }
    }

    public void loadAllAnimation() {
        File fd = preLoad(SpriteManager.ANIMATION_PATH);
        for(File f : fd.listFiles())
            if(f.isDirectory())
                loadAnimation(f.getName(), f.getName());
    }

    public void loadAnimation(String name, String dir) {
        File fd = preLoad(SpriteManager.ANIMATION_PATH + dir);
        if(Main.LOG) System.out.println(fd.getAbsolutePath());
        for(File f : fd.listFiles()) {
            if(f.isDirectory() || (!getExt(f).toLowerCase().equals("png"))) continue;
            Game.spriteManager.addToAnimation(name, f.getAbsolutePath());
        }
    }


    private File preLoad(String path) {
        if(Main.LOG) System.out.println("Texture loading...");
        File fd = new File(path);
        if( !(fd.exists() || fd.isDirectory() || (fd.listFiles() == null))) {
            System.out.println("Директорию " + fd.getAbsolutePath() + " невозможно загрузить");
            Main.clear();
            return null;
        }
        return fd;
    }
}
