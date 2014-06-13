import org.lwjgl.input.Mouse;

public class Game {
    public static final int WIDTH_IN_PIXELS = 800;
    public static final int  HEIGHT_IN_PIXELS = 600;
    public static final int HEIGHT_IN_CELLS = HEIGHT_IN_PIXELS / Map.CELL_SIZE + 1;
    public static final int WIDTH_IN_CELLS = WIDTH_IN_PIXELS / Map.CELL_SIZE + 1;
    private final Map map;
    public static final SpriteManager spriteManager = new SpriteManager();
    private Ship ship;
    private Loader loader;

    public Ship getShip() { return ship; }

    public Game() {

        loader = new Loader(this);
        loader.loadTextures("");

        map = new Map(this);
        ship = new Ship(WIDTH_IN_PIXELS / 2, HEIGHT_IN_PIXELS / 2);
    }

    public void draw() {
        map.draw();
       ship.draw();
    }

    public void cycle() {
        logic();
        draw();
    }

    public void logic() {
        ship.logic();
        map.logic();
    }

    public static boolean intersects(IBounds a, IBounds b) {
        return !((b.getXM() < a.getX() || b.getX() > a.getXM()) ||
                (b.getYM() < a.getY() || b.getY() > a.getYM()) );
    }

}
