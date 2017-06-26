
import java.io.FileReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MazeFile implements FileAdapter{
    
    public boolean save(Maze maze, String path) {
        return false;
    }
    
    public Maze load(String path) {
        try (Scanner file = new Scanner(new FileReader(path))) {
            int dim, size;
            dim = file.nextInt();
            size = file.nextInt();
            if (dim < 2 || dim > 4)
                return null;
            if (size < 5)
                return null;
            int c = dim==4 ? 8 : 1;
            int l = dim>2 ? size : 1;
            int array[][][][] = new int[c][l][size][size];
            for (int i=0; i<c; i++) {
                for (int j=0; j<l; j++) {
                    for (int k=0; k<size; k++) {
                        for (int h=0; h<size; h++) {
                            array[i][j][k][h] = file.nextInt();
                        }
                    }
                }
            }
            Maze maze = new Maze(array, dim, size);
            return maze;
        } 
        catch (Exception ex) {
            //komunikat o niepowodzeniu
            Logger.getLogger(MazeFile.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
