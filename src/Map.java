import org.lwjgl.input.Mouse;

import java.util.*;

import static org.lwjgl.opengl.GL11.*;

class MapColumn {
    private int x;

    private ArrayList<Cell> row = new ArrayList<Cell>();

    public int getX() {
        return x;
    }

    public MapColumn(Map map, int x, int heightInCells) {
        this.x = x;
        for (int i = 0; i < heightInCells; i++) {
            row.add(new Cell(map, x, i, null));
        }
    }

    public Cell getCell(int y) {
        for(Cell cell : row)
            if(cell.getY() == y)
                return cell;
        return null;
    }
}

public class Map {
    public static final int CELL_SIZE = 64;
    private ArrayList<MapColumn> mapArray = new ArrayList<MapColumn>();
    private Cell activeCell;
    private Cell shipCell;
    private final Game game;
    private int offsetX = 0;
    private int offsetY = 0;
    /**
     * Ширина и высота в ячейках
     */
    private static final int WIDTH = 45;
    private static final int HEIGHT = 45;

    private void initMapArray() {
        for(int i = 0; i < WIDTH; ++i)
           mapArray.add(new MapColumn(this, i, HEIGHT));
    }

    public int getOffsetX() { return offsetX; }
    public int getOffsetY() { return offsetY; }
    public int getCellOffsetX() { return offsetX / CELL_SIZE; }
    public int getCellOffsetY() { return offsetY / CELL_SIZE; }
    public int getCellShiftX() { return offsetX % CELL_SIZE; }
    public int getCellShiftY() { return offsetY % CELL_SIZE; }

    /**
     * Метод возвращает ячейку по ячейковым координатам
     */
    public Cell getCell(int cellX, int cellY) {
        for(MapColumn column : mapArray )
            if(column.getX() == cellX) return column.getCell(cellY);

        System.out.println("Что то произошло не так, 100%" + cellX + "  " + cellY);
        Main.clear();
        return null; // Никогда не выполниться
    }

    /**
     *
     * @param x экранная координата х
     * @param y экранная коордата y
     * @return ячейку с задаными экранными координатами
     */
    public Cell getCellbyCoords(int x, int y) {
        return getCell((offsetX + x) / CELL_SIZE, (offsetY + y) / CELL_SIZE);
    }

    private void fillCells() {
        /**
         * TODO:
         */
        for(int i = 0; i < WIDTH; ++i)
            for(int j = 0; j < HEIGHT; ++j)
               getCell(i, j).setTexture(Game.spriteManager.getSprite("water128").getTexture());
    }

    public void draw() {
       for(int i = 0; i < WIDTH - 1; ++i)
           for(int j = 0; j < HEIGHT - 1; ++j)
                getCell(i + getCellOffsetX(), j + getCellOffsetY()).draw();
        drawCellsLine();
        activeCell.drawActive("000000");
        if(activeCell != shipCell)
            shipCell.drawActive("ff0000");
    }

    public void drawCellsLine() {
        Engine.setColor("FFFFFF", 100);
        Engine.setLineWidth(1);
        for(int i = 0; i < Game.WIDTH_IN_CELLS; ++i)
            Engine.line(i * CELL_SIZE + getCellShiftX(), 0, i*CELL_SIZE + getCellShiftX(), Game.HEIGHT_IN_PIXELS);
        for (int i = 0; i < Game.HEIGHT_IN_CELLS; i++)
            Engine.line(0, i * CELL_SIZE + getCellShiftY(), Game.WIDTH_IN_PIXELS, i * CELL_SIZE + getCellShiftY());
    }

    public void logic() {

        if((Mouse.getX() > Game.WIDTH_IN_PIXELS - 50) && (Mouse.getX() < Game.WIDTH_IN_PIXELS)
                && (Mouse.getY() < Game.HEIGHT_IN_PIXELS) && (Mouse.getY() > 0))
            moveMap(-2, 0);
        if(Mouse.getX() < 50) {
            moveMap(2, 0);
        }
        if(Mouse.getY() > Game.HEIGHT_IN_PIXELS - 50) moveMap(0, -2);
        if(Mouse.getY() < 50) moveMap(0, 2);

        shipCell = getCellbyCoords((int) game.getShip().getX(), (int) game.getShip().getY());
        if(Mouse.isButtonDown(0)) {
            activeCell = getCellbyCoords(Mouse.getX(), Mouse.getY());
        }
    }

    public void moveMap(int dx, int dy) {
        if(offsetX + dx > 0)  offsetX += dx; else offsetX = 0;
        if(offsetY + dy > 0)  offsetY += dy; else offsetY = 0;

    }

    public Map(Game game) {
        this.game = game;
        initMapArray();
        fillCells();
        activeCell = getCell(2, 2);
    }
}
