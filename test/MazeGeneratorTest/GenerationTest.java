/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MazeGeneratorTest;

import source.Maze;
import source.MazeGenerator;
import testmaze.*;
import static org.junit.Assert.*;

/**
 *
 * @author Piotr Bartman
 */
public class GenerationTest {
    
    public GenerationTest() {
        System.out.println("Checking generation:");
    }

    
    /**
     * Test sprawdzający poprawność wygenerowania labiryntu o zadanych parametrach
     * @param size
     */
    public void testGenerate(int dimension, int size, int... transDen) {
        System.out.print("Checking generation"+dimension+"D("+size+") : ");
        try {
            Maze result = MazeGenerator.generate(dimension, size, transDen);
            int notOk = 0;
            int cubeNum = (result.getDimension()==4) ? 8 : 1;
            int lvlNum = (result.getDimension()>=3) ? result.getSize() : 1;
            for (int i0=0; i0<cubeNum; i0++) {//cubes
                for (int i1=0; i1<lvlNum; i1++) {//levels
                    int transNum = 0;
                    for (int X[] : result.getLevel(0)) {
                        for (int x : X) {
                            if (!Maze.check(x)) {
                                System.out.print(x+",");
                                notOk++;
                            }
                            if(x == Maze.UP) {
                                transNum++;
                            }
                        }
                    }
                    if (result.getDimension()>2 &&
                            (transNum<MazeGenerator.getCubeDen()-MazeGenerator.getCubeDenDelta()
                            || transNum>MazeGenerator.getCubeDen()+MazeGenerator.getCubeDenDelta())) {
                        System.out.print("fail("+transNum+": wrong number of transistions)\n");
                        fail();
                    }
                        
                }
            }
            if (notOk == 0) {
                System.out.print("ok\n");
                assertTrue(true);
            }
            else {
                System.out.print("fail("+notOk+" tile is corrupt)\n");
                fail();
            }
        }
        catch (Exception ex) {
            System.out.print("fail\n");
            throw ex;
        }
    }
}
