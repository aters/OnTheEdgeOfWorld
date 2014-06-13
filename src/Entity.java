import org.newdawn.slick.opengl.Texture;

public class Entity implements IBounds{

    protected double x;
    protected double y;
    protected int height;
    protected int width;
    protected String name;
    protected int angle = 0;
    protected boolean killed = false;
   // protected Animation animation = null;
    protected Texture texture = null;
    protected final boolean animated;

    public void kill() { killed = true; }
    public boolean isKilled() { return killed; }

    public double getY() {
        return y;
    }
    public double getX() {
        return x;
    }
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public double getXM() { return width + getX(); }
    public double getYM() { return height + getY(); }
    public void setY(int y) {
        this.y = y;
    }
    public void setX(int x) {
        this.x = x;
    }

    public Entity(int x, int y, String name, boolean animated) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.animated = animated;
        if(animated) {
            //animation = new Animation(name);
        } else {
            texture = Game.spriteManager.getSprite(name).getTexture();
            if(texture != null) {
                width = texture.getImageWidth();
                height = texture.getImageHeight();
            }
        }
    }

    public void draw() {
        if(animated) {
          // animation.draw(getX(), getY());
        } else {
            if(texture != null)
                Engine.drawTextureAngle(getX() - width / 2, getY() - height / 2, angle, texture);
        }
    }
}

