package source;


import java.awt.*;

/**
 * Klasa przechowyje obecny poziom labiryntu
 *
 * @author Maksym Titov
 */
public class TileMap {

    private int x;
    private int y;
    public Maze maze;
    private int currentLevel;
    private int tileSize;
    private int[][] map;
    private int mapWidth;
    private int mapHeight;
    public static int hardWall = Maze.HARDWALL,
            wall = Maze.WALL,
            floor = Maze.FLOOR,
            upTeleport = Maze.UP,
            downTeleport = Maze.DOWN,
            exitTeleport = Maze.END;

    /**
     *
     * @param tileSize
     */
    public TileMap(int tileSize) {
        this.tileSize = tileSize;
        maze = MazeGenerator.generate(2, 31);
        map = maze.getLevel(0);
        mapWidth = 31;
        mapHeight = 31;
        /*
        try {
            URL path = TileMap.class.getResource(pathToLevel);
            File f = new File(path.getFile());
            BufferedReader br = new BufferedReader(new FileReader(f));
            mapWidth = Integer.parseInt(br.readLine());
            mapHeight = Integer.parseInt(br.readLine());
            map = new int[mapHeight][mapWidth];

            String delimiters = " ";
            for (int row = 0; row < mapHeight; row++) {
                String line = br.readLine();
                String[] takens = line.split(delimiters);
                for (int col = 0; col < mapWidth; col++) {
                    map[row][col] = Integer.parseInt(takens[col]);
                }
            }
        } catch (Exception e) {
        }
        */
    }
    
    public TileMap(Maze maze, int tileSize) {
        this.tileSize = tileSize;
        this.maze = maze;
        currentLevel = 0;
        int[][] level = maze.getLevel(currentLevel);

        map = level.clone();
        mapWidth = map[0].length;
        mapHeight = map.length;
    }
    public void reload(){
        map =  maze.getLevel(0);
        mapWidth = map[0].length;
        mapHeight = map.length;
        currentLevel =0;
    }
    public boolean loadNextLevel() {
        int[][] level = maze.getLevel(++currentLevel);
        if (level == null) {
            --currentLevel;
            return false;
        }
        map = level.clone();
        mapWidth = map[0].length;
        mapHeight = map.length;
        return true;
    }

    public boolean loadPreviousLevel() {
        if(currentLevel<=0) return false;
        int[][] level = maze.getLevel(--currentLevel);
        if (level == null) {
            ++currentLevel;
            return false;
        }
        map = level.clone();
        mapWidth = map[0].length;
        mapHeight = map.length;
        return true;
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public int getColTile(int x) {
        return x / tileSize;
    }

    public int getRowTile(int y) {
        return y / tileSize;
    }

    public int getTile(int row, int col) {
        return map[row][col];
    }
    public void setTile(int row, int col, int value) {
         map[row][col]=value;
    }
    public int getTileSize() {
        return tileSize;
    }

    public int getHeight() {
        return mapHeight;
    }

    public int getWidth() {
        return mapWidth;
    }
    public void setHeight(int height) {
        mapHeight = height;
    }
    public void setWidth(int width) {
        mapWidth = width;
    }
    public void setTileSize(int size) {
        tileSize = size;
    }
    public void setx(int i) {
        x = i;
    }

    public void sety(int i) {
        y = i;
    }
    
    public void draw(Graphics2D g) {
        for (int row = 0; row < mapHeight; row++) {
            for (int col = 0; col < mapWidth; col++) {
                int rc = map[row][col];
                if (rc == hardWall || rc == wall) {
                    g.setColor(Color.BLACK);
                } else if (rc == floor) {
                    g.setColor(Color.WHITE);
                } else if (rc == downTeleport) {
                    g.setColor(Color.BLUE);
                } else if (rc == upTeleport) {
                    g.setColor(Color.RED);
                } else if (rc == exitTeleport) {
                    g.setColor(Color.ORANGE);
                } else {
                    g.setColor(Color.YELLOW);
                }
                g.fillRect(x + col * tileSize, y + row * tileSize, tileSize, tileSize);
            }
        }

    }
}
