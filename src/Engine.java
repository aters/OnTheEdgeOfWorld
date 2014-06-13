

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.Color;
import org.newdawn.slick.opengl.*;


import java.io.*;
import java.util.*;


public class Engine {
    private int width;
    private int height;
    public Color bgColor = new Color(5, 5, 5, 255);

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static Texture addPngTexture(String filePath) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Файл " + filePath + " не может быть загружен");
            System.exit(0);
        }
        return texture;
    }



    private void initGL(int width, int height) {
        try {
            DisplayMode displayMode = new DisplayMode(width, height);
            Display.setDisplayMode(displayMode);
            Display.create();
            Display.setVSyncEnabled(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        GL11.glClearColor((float)bgColor.getRed() / 255,
                (float)bgColor.getGreen() / 255,
                (float)bgColor.getBlue() / 255,
                (float)bgColor.getAlpha() / 255);

        enableAlpha();

        glViewport(0,0,width,height);
  /*      glMatrixMode(GL11.GL_MODELVIEW);

        glMatrixMode(GL11.GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, 0, height, 1, -1);
        glMatrixMode(GL11.GL_MODELVIEW);
*/
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, 0, height, 500, -500);
        glMatrixMode(GL_MODELVIEW);

        // Set depth buffer elements
        glClearDepth(1.0f);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);

         enableAntialiasing();
    }

    public static void enableAntialiasing() {
        glEnable(GL11.GL_LINE_SMOOTH);
        glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
    }

    public static void enableAlpha() {
        // enable alpha blending
        glEnable(GL11.GL_BLEND);
        glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public Engine(int width, int height) {
        this.width = width;
        this.height = height;
        initGL(width, height);
    }

    public static void drawTexture(int x, int y, Texture texture) {
        glEnable(GL_TEXTURE_2D);
        glColor3f(1, 1, 1);
        texture.bind();
        int deltaY = texture.getTextureHeight() - texture.getImageHeight();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(x, y - deltaY);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(x + texture.getTextureWidth(), y  - deltaY);
        GL11.glTexCoord2f(1,0);
        GL11.glVertex2f(x + texture.getTextureWidth(),y + texture.getTextureHeight()  - deltaY);
        GL11.glTexCoord2f(0,0);
        GL11.glVertex2f(x, y + texture.getTextureHeight()  - deltaY);
        GL11.glEnd();
        glDisable(GL_TEXTURE_2D);
    }

    public static void drawTextureAngle(double xd, double yd, double angle, Texture texture) {
//        int x = (int)xd;
//        int y = (int)yd;
        if(angle == 0) {
            drawTexture((int)xd, (int)yd, texture);
            return;
        }
        glEnable(GL_TEXTURE_2D);
        glColor3f(1, 1, 1);
        texture.bind();
        GL11.glPushMatrix();
        GL11.glTranslated(xd, yd, 0);
        GL11.glTranslatef( texture.getTextureWidth() / 2, texture.getTextureHeight() / 2, 0);
        GL11.glRotated(angle, 0, 0, 1);
        GL11.glTranslatef(- texture.getTextureWidth() / 2, -texture.getTextureHeight() / 2, 0);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(0,0);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(texture.getTextureWidth(),0);
        GL11.glTexCoord2f(1,0);
        GL11.glVertex2f(texture.getTextureWidth(),texture.getTextureHeight());
        GL11.glTexCoord2f(0,0);
        GL11.glVertex2f(0, texture.getTextureHeight() );
        GL11.glEnd();
        GL11.glPopMatrix();
        glDisable(GL_TEXTURE_2D);
    }

    public void drawTexture(Texture texture) {
        drawTexture((getWidth() - texture.getImageWidth()) / 2,
                (getHeight() - texture.getImageHeight()) / 2, texture);
    }

    /**
     * Set color for draw anything
     * @param r Red
     * @param g Green
     * @param b Blue
     */
    public static void setColor(int r, int g, int b) {
        glColor3ub((byte)r,(byte)g,(byte)b);
    }

    public static void setColor(int r, int g, int b, int alpha) {
        glColor4ub((byte)r,(byte)g,(byte)b, (byte)alpha);
    }

    /**
     * Set color for draw anything
     * @param color in HEX format
     */
    public static void setColor(String color) {
        if((color.length() != 6)) return;
        setColor(Integer.parseInt(color, 16), 255);
    }

    public static void setColor(String color, int alpha) {
        if(color.length() != 6) return;
        setColor(Integer.parseInt(color, 16), alpha);
    }

    public static void setColor(int i, int alpha) {
        int r, g, b;
        b = i & 0xFF;
        i >>= 8;
        g = i & 0xFF;
        i >>= 8;
        r = i & 0xFF;
        glColor4ub((byte)r, (byte)g, (byte)b, (byte)alpha);
    }

    public static void setLineWidth(int w) {
        glLineWidth(w);
    }

    public static void dashedLine(int x1, int y1, int x2, int y2) {
        glLineStipple(2, (short)0x00FF);
        glEnable(GL_LINE_STIPPLE);
        line(x1, y1, x2, y2);
        glDisable(GL_LINE_STIPPLE);
    }

    public static void line(int x1, int y1, int x2, int y2) {
        glBegin(GL_LINES);
        glVertex2i(x1 ,y1);
        glVertex2i(x2 ,y2);

        glEnd();
    }

    public static void quad(int x, int y, int size) {
        glBegin(GL_QUADS);
        glVertex2i(x, y);
        glVertex2i(x + size, y);
        glVertex2i(x + size, y + size);
        glVertex2i(x, y + size);
        glEnd();
    }

}

