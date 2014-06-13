import org.lwjgl.input.*;
import org.newdawn.slick.opengl.Texture;

public class Ship extends Entity {
    protected int clickX = Game.WIDTH_IN_PIXELS / 2 - 100;
    protected int clickY = Game.HEIGHT_IN_PIXELS / 2 - 50;
    protected double maxSpeed = 2;
    protected double minSpeed = 0.5;
    protected double speed = maxSpeed;
    protected double maxAngleSpeed = 3;
    protected double minAngleSpeed = 0.1;
    protected double angleSpeed = maxAngleSpeed;
    protected double vectorShipX;
    protected double vectorShipY;
    protected Texture tick = Game.spriteManager.getSprite("tick").getTexture();

    public Ship(int x, int y) {
        super(x, y, "ship128", false);
    }

    public void logic() {

        if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
            y += speed * Math.sin(angle * Math.PI / 180);
            x += speed * Math.cos(angle * Math.PI / 180);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
            y += - speed * Math.sin(angle * Math.PI / 180);
            x += - speed * Math.cos(angle * Math.PI / 180);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A))
            angle += angleSpeed;
        if(Keyboard.isKeyDown(Keyboard.KEY_D))
            angle -= angleSpeed;

        if(Mouse.isButtonDown(0)) {
            clickX = Mouse.getX();
            clickY = Mouse.getY();
        }

        double dx = clickX - x;
        double dy = clickY - y;
        double modD = Math.sqrt(dx*dx+dy*dy);
        vectorShipX = Math.cos(Math.PI * angle / 180 );
        vectorShipY = Math.sin(Math.PI * angle / 180 );

        double cosAngle = (vectorShipX * dx + vectorShipY * dy ) / modD;
        double dAngle = 180 * Math.acos(cosAngle) / Math.PI;

        double det = vectorShipX * (clickY - y) - vectorShipY * (clickX - x);

        if(modD < 32)
            angleSpeed = maxAngleSpeed + 16; else angleSpeed = maxAngleSpeed;
        if(modD < 4)
            speed = 0; else speed = maxSpeed;

        if(Math.abs(dAngle)  >  angleSpeed + 1) {
            angle = (int) (det > 0 ? angle + angleSpeed : angle - angleSpeed);
            if (det > 0) angle += angleSpeed;
            if (det < 0) angle -= angleSpeed;
        } else {
            if (det > 0) angle += dAngle;
            if (det < 0) angle -= dAngle;
        }
        y += speed * Math.sin(angle * Math.PI / 180);
        x += speed * Math.cos(angle * Math.PI / 180);
    }

    @Override
    public void draw() {
        Engine.setColor("EE1E54");
        Engine.setLineWidth(4);
        Engine.dashedLine(clickX, clickY, (int)x, (int)y);
        Engine.drawTexture(clickX - tick.getTextureWidth() / 2, clickY - tick.getTextureHeight() / 2, tick);

       // Engine.drawTextureAngle(x - 64, y - 64 , angle, texture);
        super.draw();
    }
}
