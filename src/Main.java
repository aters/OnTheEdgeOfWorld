import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {
    public static final boolean LOG = true;
    public static String PATH;
    public static final String SEPARATOR = System.getProperty("file.separator");
    public static Game game;

    public static void main(String[] args) {
        InitPath();
        Engine engine = new Engine(Game.WIDTH_IN_PIXELS, Game.HEIGHT_IN_PIXELS);
        game = new Game();
        Random r = new Random();
        while(!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glLoadIdentity();
//            float off = -r.nextFloat()*6;
//            glTranslatef(off, off, off);


            game.cycle();
            Display.sync(60);
            Display.update();
        }
        clear();
    }

    public static void clear() {
        Display.destroy();
        System.exit(0);
    }

    public static void InitPath() {
        String myJarPath = (new Object(){}).getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        String dirPath = new File(myJarPath).getParent();
        try {
            PATH =  URLDecoder.decode(dirPath, "UTF-8") + SEPARATOR;
        } catch (UnsupportedEncodingException e){
        System.setProperty("org.lwjgl.librarypath", PATH  + "native");
            e.printStackTrace();
        }
    }

}
