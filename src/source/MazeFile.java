package source;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.PrintWriter;
/**
 * Klasa pośredniczy w zapisie/odczycie labiryntu do pliku *.maze
 * @author Piotr Bartman
 */
public class MazeFile implements FileAdapter{
    @Override
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
            Logger.getLogger(MazeFile.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public boolean save(Maze maze, String path) {
        try (PrintWriter file = new PrintWriter(path)) {
            file.println(maze.getDimension());
            file.println(maze.getSize());
            int c = maze.getDimension()==4 ? 8 : 1;
            int l = maze.getDimension()>2 ? maze.getSize() : 1;
            for (int i=0; i<c; i++) {
                for (int j=0; j<l; j++) {
                    for (int X[]: maze.getLevel(j)) {
                        for (int x : X) {
                            file.print(x+" ");
                        }
                        file.println();
                    }
                }
            }
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MazeFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}