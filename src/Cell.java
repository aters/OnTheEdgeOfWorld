import org.newdawn.slick.opengl.*;

public class Cell {
    private Texture texture;
    protected int x;
    protected int y;
    protected Map map;

    public Texture getTexture() {
        return texture;
    }
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public int getScreenX() { return Map.CELL_SIZE * x - map.getOffsetX(); }
    public int getScreenY() { return Map.CELL_SIZE * y - map.getOffsetY(); }

    public Cell(Map map, int x, int y, Texture texture ) {
        this.x  = x;
        this.y  = y;
        this.map = map;
        this.texture = texture;
    }



    public void draw() {
        if(texture != null) {
            Engine.drawTexture(getScreenX(), getScreenY(), texture);
        } else System.out.println("TEXTURE NOT FOUND IN CELL x = " + x + " y = " + y);
       // System.out.println(map.getOffsetX());
    }

    public void drawActive(String color) {
        Engine.setColor(color, 80);
        Engine.quad(getScreenX(), getScreenY(), Map.CELL_SIZE);
    }


}
