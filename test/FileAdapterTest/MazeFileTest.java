/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileAdapterTest;

import org.junit.Test;
import static org.junit.Assert.*;
import source.Maze;
import source.MazeFile;
import source.MazeGenerator;

/**
 *
 * @author PiotrRadosław
 */
public class MazeFileTest {
    
    public MazeFileTest() {
    }

    /**
     * Test of load method, of class MazeFile.
     */
    @Test
    public void testLoad() {
        System.out.println("load:");
        String path = "test\\FileAdapterTest\\test.maze";
        MazeFile file = new MazeFile();
        Maze result = file.load(path);
        int[][] array = result.getLevel(0);
        for (int X[] : array) {
            for (int x : X) {
                System.out.print(x+" ");
            }
            System.out.println();
        }
        assertEquals(result, result);
    }
    
    /**
     * Test of save method, of class MazeFile.
     */
    @Test
    public void testSave() {
        System.out.println("save:");
        String path = "test\\FileAdapterTest\\test.maze";
        MazeFile file = new MazeFile();
        boolean result = file.save(MazeGenerator.generate(3, 17), path);
        assertEquals(result, true);
    }
    
}
