/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Random;
import java.util.Stack;
/**
 *
 * @author Piotr
 */
public class Maze {
    private int maze[][][][];
    private int dimension = 0;
    private int size = 17; //wartość domyślna
    private int cubeDen = 1; //ilość przejść między piętrami sześcianu
    private int cubeDenDelta = 1; //odchylenie w ilości przejść między piętrami
    private int tessDen = 1; //ilość przejść między sześcianami
    private int tessDenDelta = 1; //odchylenie w ilości przejść między sześcianami
    
    public static final int CUBENUMBER = 8;//ilość sześcianów w tesserakcie
    public static final int FLOOR = 1;
    public static final int WALL = 2;
    public static final int UP = 3;
    public static final int DOWN = 5;
    public static final int HARDWALL = 4;
    public static final int DOOR = 7;
    public static final int DOORUP = 9;
    public static final int DOORDOWN = 11;
    
    public Maze(int dimension, int size, int... transistionDensity) {
        if (dimension < 2) this.dimension = 2;
        else if (dimension > 4) this.dimension = 4;
        else this.dimension = dimension;
        
        if (size < 5) this.size = 5;
        else if (size%2 == 0) this.size = size-1;
        else this.size = size;
        maze = new int[dimension==4 ? this.size : 1][dimension>=3 ? this.size : 1][this.size][this.size];
        
        if (transistionDensity.length > 0) cubeDen = transistionDensity[0]<this.size ? transistionDensity[0] : this.size-1;
        else cubeDen = ((cubeDen=(int)(this.size*this.size/128))>0) ? cubeDen : 1;
        if (transistionDensity.length > 1) cubeDenDelta = transistionDensity[1]<cubeDen ? transistionDensity[1] : cubeDen-1;
        else cubeDenDelta = (int)(cubeDen/2);
        if (transistionDensity.length > 2) tessDen = transistionDensity[2]<this.size ? transistionDensity[2] : this.size-1;
        else tessDen = (tessDen=(int)(this.size*this.size/128))>0 ? tessDen : 1;
        if (transistionDensity.length > 3) tessDenDelta = transistionDensity[3]<cubeDen ? transistionDensity[3] : cubeDen-1;
        else tessDenDelta = (int)(tessDen/2);
        generateColumns();
        if (dimension >= 3) generate3DTransition();
        if (dimension == 4) generate4DTransition();
        generateCorridors();
    }
    /*public Maze (int array[][][][]) {
        if (array[].)
    }*/
    
    private void generateColumns() {
        int cubeNum = (dimension==4) ? 8 : 1;
        int lvlNum = (dimension>=3) ? size : 1;
        for (int i0=0; i0<cubeNum; i0++) {//cubes
            for (int i1=0; i1<lvlNum; i1++) {//levels
                for (int i2=0; i2<size; i2++) {
                    maze[i0][i1][i2][0] = HARDWALL;
                    maze[i0][i1][i2][size-1] = HARDWALL;
                    if (i2%2==0)
                        for (int i3=0; i3<size; i3++) {
                            if (i3%2==0)
                                maze[i0][i1][i2][i3] = HARDWALL;
                        }
                }
                for (int i3=0; i3<size; i3++) {
                    maze[i0][i1][0][i3] = HARDWALL;
                    maze[i0][i1][size-1][i3] = HARDWALL;
                }
            }
        }
    }
    
    private void generate3DTransition() {
        Random rnd = new Random();
        int cd;
        int cubeNum = (dimension==4) ? 8 : 1;
        for (int i0=0; i0<cubeNum; i0++) {//cubes
            for (int i1=0; i1<size-1; i1++) {//levels
                if (cubeDenDelta > 0)
                    cd = cubeDen - cubeDenDelta + rnd.nextInt(2*cubeDenDelta);
                else
                    cd = cubeDen;
                for (int i=0; i<cd; i++) {
                    int x = rnd.nextInt(size);
                    int y = rnd.nextInt(size);
                    if (x%2 == 0) x++;
                    if (y%2 == 0) y++;
                    if (x == size) x = 3;
                    if (y == size) y = 1;
                    if (x==1 && y==1) x+=2;
                    if (maze[i0][i1][x][y]==-DOWN || maze[i0][i1][x][y]==-UP) {
                        i--; continue;
                    }
                    maze[i0][i1][x][y] = -UP;
                    maze[i0][i1+1][x][y] = -DOWN;
                }
            }
        }
    }
    private void generate4DTransition() {
        Random rnd = new Random();
        int td;
            if (tessDenDelta > 0) 
                td = tessDen - tessDenDelta + rnd.nextInt(2*tessDenDelta);
            else 
                td = tessDen;
        for (int a=0; a<CUBENUMBER-1; a++)//łączymy sześcian a
            for (int b=a+1; b<CUBENUMBER; b++) {//z sześcianem b
                if (b == inverse(a)) continue;
                int x = rnd.nextInt(size);
                int y = rnd.nextInt(size);
                if (x%2 == 0) x++;
                if (y%2 == 0) y++;
                if (x == size) x = 1;
                if (y == size) y = 1;
                int A = b==7 ? a : b;//ściana A sześcianu a
                int B = a==0 ? inverse(b) : a;//ściana B sześcianu b
                if ((getWallPoint(a, A, x, y)!=HARDWALL && getWallPoint(a, A, x, y)!=0)
                        || (getWallPoint(b, B, x, y)!=HARDWALL && getWallPoint(b, B, x, y)!=0)) {
                    b--; continue;
                }
                /**
                 * @todo trzeba jeszcze skorygować x i y dwóch ścian
                 */
                setWallPoint(a, A, x, y, (A<5 ? DOOR : (A==5)?-DOORDOWN:-DOORUP));
                setWallPoint(b, B, x, y, (B<5 ? DOOR : (A==5)?-DOORDOWN:-DOORUP));
            }
}
    private int inverse(int i) {
        i = i%2==0 ? (i-1)%8 : (i+1)%8;
        return i<0 ? 7 : i;
    }
    private int getWallPoint(int cubeNr, int wallNr, int x, int y) {
        switch(wallNr) {
            case 1: return maze[cubeNr][x][y][0];
            case 2: return maze[cubeNr][x][y][size-1];
            case 3: return maze[cubeNr][x][0][y];
            case 4: return maze[cubeNr][x][size-1][y];
            case 5: return maze[cubeNr][0][x][y];
            case 6: return maze[cubeNr][size-1][x][y];
            default: return 0;
        }
    }
    private void setWallPoint(int cubeNr, int wallNr, int x, int y, int value) {
        switch(wallNr) {
            case 1: maze[cubeNr][x][y][0] = value;
            case 2: maze[cubeNr][x][y][size-1] = value;
            case 3: maze[cubeNr][x][0][y] = value;
            case 4: maze[cubeNr][x][size-1][y] = value;
            case 5: maze[cubeNr][0][x][y] = value;
            case 6: maze[cubeNr][size-1][x][y] = value;
        }
    }
    
    private void generateCorridors() {
        Point p = new Point();
        int dir;
        int directions;
        Random rnd = new Random();
        Stack<Point> stack = new <Point>Stack();
        stack.push(new Point(p));
        while (!stack.empty()) {
            p = stack.peek();
            switch (maze[p.c][p.l][p.x][p.y]) {
                case 0:
                    maze[p.c][p.l][p.x][p.y] = FLOOR;
                case FLOOR:
                    directions = 4;
                    break;
                case -UP:
                    maze[p.c][p.l][p.x][p.y] = UP;
                case UP:
                    //System.out.print("UP:");
                    directions = 5;
                    break;
                case -DOWN:
                    maze[p.c][p.l][p.x][p.y] = DOWN;
                case DOWN:
                    //System.out.print("DOWN:");
                    directions = 5;
                    break;
                case -DOORUP:
                    maze[p.c][p.l][p.x][p.y] = DOORUP;
                case DOORUP:
                    directions = 4;
                    break;
                case -DOORDOWN:
                    maze[p.c][p.l][p.x][p.y] = DOORDOWN;
                case DOORDOWN:
                    directions = 4;
                    break;
                default:
                    directions = 4;
                    break;
            }
            switch (directions) {
                case 5:
                    int lvl = maze[p.c][p.l][p.x][p.y]==UP ? 1 : -1;
                    /*dir = checkDirection(rnd.nextInt(5),
                            maze[p.c][p.l][p.x][p.y-1], maze[p.c][p.l][p.x+1][p.y],
                            maze[p.c][p.l][p.x][p.y+1], maze[p.c][p.l][p.x-1][p.y],
                            maze[p.c][p.l+lvl][p.x][p.y]);
                    System.out.print(dir+" ");*/
                    if (maze[p.c][p.l+lvl][p.x][p.y] < 1) {
                        dir = 5;
                        break;
                    }
                    //break;
                case 4:
                    dir = checkDirection(rnd.nextInt(4),
                            maze[p.c][p.l][p.x][p.y-1], maze[p.c][p.l][p.x+1][p.y],
                            maze[p.c][p.l][p.x][p.y+1], maze[p.c][p.l][p.x-1][p.y]);
                    break;
                default:
                    dir = 0;
            }
            switch(dir) {
                case 1:
                    if (maze[p.c][p.l][p.x][p.y-2]<=0) {
                        maze[p.c][p.l][p.x][p.y-1] = FLOOR;
                        p.y -= 2;
                        stack.push(new Point(p));
                    }
                    else {
                        maze[p.c][p.l][p.x][p.y-1] = WALL;
                    }
                    break;
                case 2:
                    if (maze[p.c][p.l][p.x+2][p.y]<=0) {
                        maze[p.c][p.l][p.x+1][p.y] = FLOOR;
                        p.x += 2;
                        stack.push(new Point(p));
                    }
                    else {
                        maze[p.c][p.l][p.x+1][p.y] = WALL;
                    }
                    break;
                case 3:
                    if (maze[p.c][p.l][p.x][p.y+2]<=0) {
                        maze[p.c][p.l][p.x][p.y+1] = FLOOR;
                        p.y += 2;
                        stack.push(new Point(p));
                    }
                    else {
                        maze[p.c][p.l][p.x][p.y+1] = WALL;
                    }
                    break;
                case 4:
                    if (maze[p.c][p.l][p.x-2][p.y]<=0) {
                        maze[p.c][p.l][p.x-1][p.y] = FLOOR;
                        p.x -= 2;
                        stack.push(new Point(p));
                    }
                    else {
                        maze[p.c][p.l][p.x-1][p.y] = WALL;
                    }
                    break;
                case 5:
                    //przejście na inne piętro
                    switch (maze[p.c][p.l][p.x][p.y]) {
                        case UP:
                            if (maze[p.c][p.l+1][p.x][p.y]<=0) {
                                p.l += 1;
                                stack.push(new Point(p));
                            }
                            break;
                        case DOWN:
                            if (maze[p.c][p.l-1][p.x][p.y]<=0) {
                                p.l -= 1;
                                stack.push(new Point(p));
                            }
                            break;
                        default:
                            System.out.print(":( ");
                    }
                    break;
                default:
                    //System.out.println("X="+p.x+" Y="+p.y);
                    stack.pop();
                    break;
            }
        }
    }
    private class Point {
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
    private int checkDirection(int r, int... fields) {
        int count = fields.length;
        while ((fields[r]==HARDWALL || fields[r]==WALL 
                || fields[r]==DOOR || fields[r]==-DOOR || fields[r]==FLOOR
                || fields[r]==UP || fields[r]==DOWN) && count>0) {
            r = (r+1)%fields.length;
            count--;
        }
        if (count == 0)
            r=-1;
        return r+1;
    }
    
    public int get(int... k) {
        switch(k.length) {
            case 1:
                return maze[0][0][0][k[0]];
            case 2:
                return maze[0][0][k[0]][k[1]];
            case 3:
                return maze[0][k[0]][k[1]][k[2]];
            case 4:
                return maze[k[0]][k[1]][k[2]][k[3]];
            default:
                return maze[k[0]][k[1]][k[2]][k[3]];
        }
    }
    
    public int[][] getLevel(int... k) {
        switch(k.length) {
            case 1:
                return maze[0][k[0]];
            case 2:
                return maze[k[0]][k[1]];
            default:
                return null;
        }
    }
    
    public int getSize() {
        return size;
    }
    
    public static void main(String[] args) {
        final int X = 17, Y=17, Z=17;
        Maze maze = new Maze(2, 61);
        for (int i=0; i < X; i++) {
            for (int j=0; j<Y; j++) {
                //System.out.print(maze.get(i,j));
                if (maze.get(j,i) == maze.WALL)
                    System.out.print("  ");
                else
                    System.out.print("##");
            }
            System.out.println();
        }
    }
}
