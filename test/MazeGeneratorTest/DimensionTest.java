/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MazeGeneratorTest;

import source.Maze;
import source.MazeGenerator;
import testmaze.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Piotr Bartman
 */
public class DimensionTest {
    
    public DimensionTest() {
        System.out.println("Checking dimension parameter:");
    }
    
     /**
     * Test sprawdzający przyjęcia wymiaru dimension przez Maze
     * @param dimension
     */
    public void testGenerateDimension(int dimension) {
        System.out.print("Checking dimension(" +dimension+") : ");
        try {
            Maze result = MazeGenerator.generate(dimension, 0);
            if (dimension < 2) dimension = 2;
            if (dimension > 4) dimension = 4;
            assertEquals(dimension, result.getDimension());
            if (dimension == result.getDimension()) {
                System.out.print("ok\n");
                assertTrue(true);
            }
            else {
                System.out.print("fail\n");
                fail();
            }
        }
        catch (Exception ex) {
            System.out.print("fail\n");
            throw ex;
        }
    }
    @Test
    public void testGenerateDimensionAll() {
        for (int i=-8; i<128; i++)
            testGenerateDimension(i);
    }
    @Test
    public void testGenerateDimensionNegativ() {
        testGenerateDimension(-1);
    }
    @Test
    public void testGenerateDimension0() {
        testGenerateDimension(0);
    }
    @Test
    public void testGenerateDimension1() {
        testGenerateDimension(1);
    }
    @Test
    public void testGenerateDimension2() {
        testGenerateDimension(2);
    }
    @Test
    public void testGenerateDimension3() {
        testGenerateDimension(3);
    }
    @Test
    public void testGenerateDimension4() {
        testGenerateDimension(4);
    }
    @Test
    public void testGenerateDimension5() {
        testGenerateDimension(5);
    }
}
