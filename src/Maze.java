/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Klasa Maze przechowuje labirynt w postaci tablicy int[][][][]
 * @author Piotr Bartman
 */
public class Maze {
    private int maze[][][][];
    private int dimension = 0;
    /**wartość domyślna size = 5*/
    private int size = 5;
    
    /**ilość sześcianów w tesserakcie*/
    public static final int CUBENUMBER = 8;
    /**stałe (elementy) labiryntu*/
    public static final int FLOOR = 1;
    public static final int WALL = 2;
    public static final int UP = 3;
    public static final int DOWN = 5;
    public static final int HARDWALL = 4;
    public static final int DOOR = 7;
    public static final int DOORUP = 9;
    public static final int DOORDOWN = 11;
    public static final int END = 13;
    public static final int START = 15;
    
    
    public Maze(int dimension, int size) {
        if (dimension < 2) this.dimension = 2;
        else if (dimension > 4) this.dimension = 4;
        else this.dimension = dimension;
        
        if (size%2 == 0) size--;
        if (size > 4) this.size = size;
        else size = this.size;
        
        maze = new int[dimension==4 ? size : 1][dimension>=3 ? size : 1][size][size];
        
        generateFrames();
    }
    
    /**
     * Tworzy labirynt z podanej tablicy.
     * @param array
     * @param dimension int od 2 do 4
     * @param size int od 5
     */
    public Maze (int array[][][][], int dimension, int size) {
        if (dimension < 2) this.dimension = 2;
        else if (dimension > 4) this.dimension = 4;
        else this.dimension = dimension;
        
        if (size < 5) this.size = 5;
        else if (size%2 == 0) this.size = size-1;
        else this.size = size;
        
        int c = dimension==4 ? this.size : 1;
        int l = dimension>=3 ? this.size : 1;
        maze = new int[c][l][this.size][this.size];
        for (int i=0; i<c; i++) {
            for (int j=0; j<l; j++) {
                for (int k=0; k<size; k++) {
                    for (int h=0; h<size; h++) {
                        set(array[i][j][k][h]>0?array[i][j][k][h]:0, i, j, k, h);
                    }
                }
            }
        }
        generateFrames();
        if (dimension >= 3) check3DTransition();
        if (dimension == 4) check4DTransition();
    }
    
    private void generateFrames() {
        int cubeNum = (dimension==4) ? 8 : 1;
        int lvlNum = (dimension>=3) ? size : 1;
        for (int i0=0; i0<cubeNum; i0++) {//cubes
            for (int i1=0; i1<lvlNum; i1++) {//levels
                for (int i2=0; i2<size; i2++) {
                    maze[i0][i1][i2][0] = HARDWALL;
                    maze[i0][i1][i2][size-1] = HARDWALL;
                }
                for (int i3=0; i3<size; i3++) {
                    maze[i0][i1][0][i3] = HARDWALL;
                    maze[i0][i1][size-1][i3] = HARDWALL;
                }
            }
        }
    }

    
    /**
     * 
     * @param k współrzędne od 0 do 4, niepodane współrzędne domniemanie 0
     * @return
     */
    public int get(int... k) {
        switch(k.length) {
            case 0:
                return maze[0][0][0][0];
            case 1:
                return maze[0][0][0][k[0]];
            case 2:
                return maze[0][0][k[0]][k[1]];
            case 3:
                return maze[0][k[0]][k[1]][k[2]];
            default:
                return maze[k[0]][k[1]][k[2]][k[3]];
        }
    }
    
    /**
     * 
     * @param n wstawiana wartość
     * @param m współrzędne od 0 do 4, niepodane współrzędne domniemanie 0
     */
    public void set(int n, int... m) {
        int i,j,k,h;
        i=j=k=h=0;
        switch(m.length) {
            case 0:
                break;
            case 1:
                h=m[0];
                break;
            case 2:
                k=m[0]; h=m[1];
                break;
            case 3:
                j=m[0]; k=m[1]; h=m[2];
                break;
            default:
                i=m[0]; j=m[1]; k=m[2]; h=m[3];
        }
        
        switch (n) {
            case START:
                maze[i][j][k][h] = START;
                break;
            case END:
                maze[i][j][k][h] = END;
                break;
            case DOOR:
                maze[i][j][k][h] = DOOR;
                break;
            case WALL:
                maze[i][j][k][h] = WALL;
                break;
            case HARDWALL:
                maze[i][j][k][h] = HARDWALL;
                break;
            case UP:
                maze[i][j][k][h] = UP;
                break;
            case -UP:
                maze[i][j][k][h] = -UP;
                break;
            case DOWN:
                maze[i][j][k][h] = DOWN;
                break;
            case -DOWN:
                maze[i][j][k][h] = -DOWN;
                break;
            case DOORUP:
                maze[i][j][k][h] = DOORUP;
                break;
            case DOORDOWN:
                maze[i][j][k][h] = DOORDOWN;
                break;
            default:
                maze[i][j][k][h] = FLOOR;
                break;
        }
    }
    
    /**
     *
     * @param k współrzędne od 1 do 2, domniemany sześcian to sześcian 0
     * @return
     */
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
    
    public int getDimension() {
        return dimension;
    }
    
    private void check3DTransition() {
        int cubeNum = (dimension==4) ? 8 : 1;
        for (int i=0; i<cubeNum; i++) {//cubes
            for (int j=0; j<size-1; j++) {
                for (int k=0; k<size-1; k++) {
                    if (maze[i][0][j][k] == DOWN)
                        maze[i][0][j][k] = FLOOR;
                    if (maze[i][size-1][j][k] == UP)
                        maze[i][size-1][j][k] = FLOOR;
                }
            }
        }
    }
    
    private void check4DTransition() {}
    
    public static void main(String[] args) {
        final int X = 17, Y=17, Z=17;
        Maze maze = new Maze(2, 61);
        for (int i=0; i < X; i++) {
            for (int j=0; j<Y; j++) {
                //System.out.print(maze.get(i,j));
                if (maze.get(j,i) == Maze.WALL)
                    System.out.print("  ");
                else
                    System.out.print("##");
            }
            System.out.println();
        }
    }
}
