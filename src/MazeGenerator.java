

import java.util.Random;
import java.util.Stack;

/**
 *
 * @author Piotr Bartman
 */
public abstract class MazeGenerator {
    private static Maze maze = null;
    /**ilość przejść między piętrami sześcianu, domniemane 1 przejście na 128 pól,
     maksymalna wartość to size^2 / 4*/
    private static int cubeDen;
    /**odchylenie w ilości przejść między piętrami, domniemane 1/2 cubeDen
     maksymalna wartość to cubeDen-1*/
    private static int cubeDenDelta;
    /**ilość przejść między sześcianami, domniemane 1 przejście na 128 pól
      maksymalna wartość to size^2 / 4*/
    private static int tessDen;
    /**odchylenie w ilości przejść między sześcianami, domniemane 1/2 tessDen
     maksymalna wartość to tessDen-1*/
    private static int tessDenDelta;
    
    /**
     * Generuje labirynt doskonały o podanych parametrach.
     * @param dimension int od 2 do 4
     * @param size int od 5
     * @param transistionDensity oczekuje od 0 do 4 argumentów 
     * (cubeDen, cubeDenDelta, tessDen, tessDenDelta) jeżeli nie podane to 
     * wartości domniemane
     * @return Maze
     */
    public static Maze generate(int dimension, int size, int... transistionDensity) {
        if (transistionDensity == null) transistionDensity = new int[0];
        if (transistionDensity.length > 0) 
            cubeDen = Math.min(transistionDensity[0],(int)(size*size/4));
        else 
            cubeDen = Math.max((int)(size*size/128), 1);
        if (transistionDensity.length > 1) 
            cubeDenDelta = Math.min(transistionDensity[1], cubeDen-1);
        else 
            cubeDenDelta = (int)(cubeDen/2);
        if (transistionDensity.length > 2) 
            tessDen = Math.min(transistionDensity[2],(int)(size*size/4));
        else 
            tessDen = Math.max((int)(size*size/128), 1);
        if (transistionDensity.length > 3) 
            tessDenDelta = Math.min(transistionDensity[1], tessDen-1);
        else 
            tessDenDelta = (int)(tessDen/2);
        
        Maze m = new Maze(dimension, size);
        maze = m;
        
        generateColumns();
        if (dimension >= 3) generate3DTransition();
        //if (dimension == 4) generate4DTransition();
        generateCorridors();
        maze.set(Maze.START,0,0,1,1);
        maze.set(Maze.END,
                maze.getDimension()<4?0:inverse(0),
                maze.getDimension()<3?0:maze.getSize()-1,
                maze.getSize()-2,
                maze.getSize()-2);
        
        maze = null;
        return m;
    }
    
    private static void generateColumns() {
        int cubeNum = (maze.getDimension()==4) ? 8 : 1;
        int lvlNum = (maze.getDimension()>=3) ? maze.getSize() : 1;
        for (int i0=0; i0<cubeNum; i0++) {//cubes
            for (int i1=0; i1<lvlNum; i1++) {//levels
                for (int i2=0; i2<maze.getSize(); i2++) {
                    if (i2%2==0)
                        for (int i3=0; i3<maze.getSize(); i3++) {
                            if (i3%2==0)
                                maze.set(Maze.HARDWALL,i0, i1, i2, i3);
                        }
                }
            }
        }
    }
    
    private static void generate3DTransition() {
        Random rnd = new Random();
        int cd;
        int cubeNum = (maze.getDimension()==4) ? 8 : 1;
        for (int i0=0; i0<cubeNum; i0++) {//cubes
            for (int i1=0; i1<maze.getSize()-1; i1++) {//levels
                if (cubeDenDelta > 0)
                    cd = cubeDen - cubeDenDelta + rnd.nextInt(2*cubeDenDelta);
                else
                    cd = cubeDen;
                for (int i=0; i<cd; i++) {
                    int x = rnd.nextInt(maze.getSize());
                    int y = rnd.nextInt(maze.getSize());
                    if (x%2 == 0) x++;
                    if (y%2 == 0) y++;
                    if (x == maze.getSize()) x = 3;
                    if (y == maze.getSize()) y = 1;
                    if (x==1 && y==1) x+=2;
                    if (maze.get(i0,i1,x,y)==-Maze.DOWN || maze.get(i0,i1,x,y)==-Maze.UP) {
                        i--; continue;
                    }
                    maze.set(-Maze.UP, i0,i1,x,y);
                    maze.set(-Maze.DOWN, i0,i1+1,x,y);
                }
            }
        }
    }
    private static void generate4DTransition() {
        Random rnd = new Random();
        int td;
            if (tessDenDelta > 0) 
                td = tessDen - tessDenDelta + rnd.nextInt(2*tessDenDelta);
            else 
                td = tessDen;
        for (int a=0; a<Maze.CUBENUMBER-1; a++)//łączymy sześcian a
            for (int b=a+1; b<Maze.CUBENUMBER; b++) {//z sześcianem b
                if (b == inverse(a)) continue;
                int x = rnd.nextInt(maze.getSize());
                int y = rnd.nextInt(maze.getSize());
                if (x%2 == 0) x++;
                if (y%2 == 0) y++;
                if (x == maze.getSize()) x = 1;
                if (y == maze.getSize()) y = 1;
                int A = b==7 ? a : b;//ściana A sześcianu a
                int B = a==0 ? inverse(b) : a;//ściana B sześcianu b
                if ((getWallPoint(a, A, x, y)!=Maze.HARDWALL && getWallPoint(a, A, x, y)!=0)
                        || (getWallPoint(b, B, x, y)!=Maze.HARDWALL && getWallPoint(b, B, x, y)!=0)) {
                    b--; continue;
                }
                /**
                 * @todo trzeba jeszcze skorygować x i y dwóch ścian
                 */
                setWallPoint(a, A, x, y, (A<5 ? Maze.DOOR : (A==5)?-Maze.DOORDOWN:-Maze.DOORUP));
                setWallPoint(b, B, x, y, (B<5 ? Maze.DOOR : (A==5)?-Maze.DOORDOWN:-Maze.DOORUP));
            }
}
    private static int inverse(int i) {
        i = i%2==0 ? (i-1)%8 : (i+1)%8;
        return i<0 ? 7 : i;
    }
    private static int getWallPoint(int cubeNr, int wallNr, int x, int y) {
        switch(wallNr) {
            case 1: return maze.get(cubeNr, x, y, 0);
            case 2: return maze.get(cubeNr, x, y, maze.getSize()-1);
            case 3: return maze.get(cubeNr, x, 0, y);
            case 4: return maze.get(cubeNr, x, maze.getSize()-1, y);
            case 5: return maze.get(cubeNr, 0, x, y);
            case 6: return maze.get(cubeNr, maze.getSize()-1, x, y);
            default: return 0;
        }
    }
    private static void setWallPoint(int cubeNr, int wallNr, int x, int y, int value) {
        switch(wallNr) {
            case 1: maze.set(value, cubeNr, x, y, 0);
            case 2: maze.set(value, cubeNr, x, y, maze.getSize()-1);
            case 3: maze.set(value, cubeNr, x, 0, y);
            case 4: maze.set(value, cubeNr, x, maze.getSize()-1, y);
            case 5: maze.set(value, cubeNr, 0, x, y);
            case 6: maze.set(value, cubeNr, maze.getSize()-1, x, y);
        }
    }
    
    private static void generateCorridors() {
        Point p = new Point();
        int dir;
        int directions;
        Random rnd = new Random();
        Stack<Point> stack = new <Point>Stack();
        stack.push(new Point(p));
        while (!stack.empty()) {
            p = stack.peek();
            directions = visit(p);
            switch (directions) {
                case 5:
                    int lvl = maze.get(p.c, p.l, p.x, p.y)==Maze.UP ? 1 : -1;
                    if (maze.get(p.c, p.l+lvl, p.x, p.y) < 1) {
                        dir = 5;
                        break;
                    }
                case 4:
                    dir = checkDirection(rnd.nextInt(4),
                            maze.get(p.c, p.l, p.x, p.y-1), maze.get(p.c, p.l, p.x+1, p.y),
                            maze.get(p.c, p.l, p.x, p.y+1), maze.get(p.c, p.l, p.x-1, p.y));
                    break;
                default:
                    dir = 0;
            }
            switch(dir) {
                case 1:
                    if (maze.get(p.c, p.l, p.x, p.y-2)<=0) {
                        maze.set(Maze.FLOOR, p.c, p.l, p.x, p.y-1);
                        p.y -= 2;
                        stack.push(new Point(p));
                    }
                    else {
                        maze.set(Maze.WALL, p.c, p.l, p.x, p.y-1);
                    }
                    break;
                case 2:
                    if (maze.get(p.c, p.l, p.x+2, p.y)<=0) {
                        maze.set(Maze.FLOOR, p.c, p.l, p.x+1, p.y);
                        p.x += 2;
                        stack.push(new Point(p));
                    }
                    else {
                        maze.set(Maze.WALL, p.c, p.l, p.x+1, p.y);
                    }
                    break;
                case 3:
                    if (maze.get(p.c, p.l, p.x, p.y+2)<=0) {
                        maze.set(Maze.FLOOR, p.c, p.l, p.x, p.y+1);
                        p.y += 2;
                        stack.push(new Point(p));
                    }
                    else {
                        maze.set(Maze.WALL, p.c, p.l, p.x, p.y+1);
                    }
                    break;
                case 4:
                    if (maze.get(p.c, p.l, p.x-2, p.y)<=0) {
                        maze.set(Maze.FLOOR, p.c, p.l, p.x-1, p.y);
                        p.x -= 2;
                        stack.push(new Point(p));
                    }
                    else {
                        maze.set(Maze.WALL, p.c, p.l, p.x-1, p.y);
                    }
                    break;
                case 5:
                    //przejście na inne piętro
                    switch (maze.get(p.c, p.l, p.x, p.y)) {
                        case Maze.UP:
                            if (maze.get(p.c, p.l+1, p.x, p.y)<=0) {
                                p.l += 1;
                                stack.push(new Point(p));
                            }
                            break;
                        case Maze.DOWN:
                            if (maze.get(p.c, p.l-1, p.x, p.y)<=0) {
                                p.l -= 1;
                                stack.push(new Point(p));
                            }
                            break;
                    }
                    break;
                default:
                    stack.pop();
                    break;
            }
        }
        erase0();
    }
    private static class Point {
        public int c = 0;
        public int l = 0;
        public int x = 1;
        public int y = 1;
        public Point(){}
        public Point(Point p) {
            this.c = p.c;
            this.l = p.l;
            this.x = p.x;
            this.y = p.y;
        }
    }
    private static void erase0() {
        int cubeNum = (maze.getDimension()==4) ? 8 : 1;
        int lvlNum = (maze.getDimension()>=3) ? maze.getSize() : 1;
        for (int i0=0; i0<cubeNum; i0++) {//cubes
            for (int i1=0; i1<lvlNum; i1++) {//levels
                for (int i2=0; i2<maze.getSize(); i2++) {
                    for (int i3=0; i3<maze.getSize(); i3++) {
                        if (maze.get(i0, i1, i2, i3) == 0)
                            maze.set(Maze.WALL,i0, i1, i2, i3);
                    }
                }
            }
        }
    }
    private static int checkDirection(int r, int... fields) {
        int count = fields.length;
        while ((fields[r]==Maze.HARDWALL || fields[r]==Maze.WALL 
                || fields[r]==Maze.DOOR || fields[r]==-Maze.DOOR || fields[r]==Maze.FLOOR
                || fields[r]==Maze.UP || fields[r]==Maze.DOWN) && count>0) {
            r = (r+1)%fields.length;
            count--;
        }
        if (count == 0)
            r=-1;
        return r+1;
    }
    private static int visit(Point p) {
        switch (maze.get(p.c, p.l, p.x, p.y)) {
                case 0:
                    maze.set(Maze.FLOOR, p.c, p.l, p.x, p.y);
                case Maze.FLOOR:
                    return 4;
                case -Maze.UP:
                    maze.set(Maze.UP, p.c, p.l, p.x, p.y);
                case Maze.UP:
                    return 5;
                case -Maze.DOWN:
                    maze.set(Maze.DOWN, p.c, p.l, p.x, p.y);
                case Maze.DOWN:
                    return 5;
                case -Maze.DOORUP:
                    maze.set(Maze.DOORUP, p.c, p.l, p.x, p.y);
                case Maze.DOORUP:
                    return 4;
                case -Maze.DOORDOWN:
                    maze.set(Maze.DOORDOWN, p.c, p.l, p.x, p.y);
                case Maze.DOORDOWN:
                    return 4;
                default:
                    return 4;
            }
    }
    
    public static int getCubeDen() {
        return cubeDen;
    }
    
    public static int getCubeDenDelta() {
        return cubeDenDelta;
    }
    
    public static int getTessDen() {
        return tessDen;
    }
    public static int getTessDenDelta() {
        return tessDenDelta;
    }
}
