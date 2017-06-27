/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MazeGeneratorTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import source.Maze;
import source.MazeGenerator;

/**
 *
 * @author Piotr Bartman
 */
public class SizeTest {
    
    public SizeTest() {
        System.out.println("Checking size parameter:");
    }

    /**
     * Test sprawdzający przyjęcia rozmiaru size przez Maze
     * @param size
     */
    public void testGenerateSize(int size) {
        System.out.print("Checking size(" +size+") : ");
        try {
            Maze result = MazeGenerator.generate(0, size);
            if (size%2 == 0) size--;
            if ((size<4?5:size) == result.getSize()) {
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
    public void testGenerateSizeAll() {
        for (int i=-8; i<128; i++)
            testGenerateSize(i);
    }
    @Test
    public void testGenerateSizeNegativ() {
        testGenerateSize(-1);
    }
    @Test
    public void testGenerateSize0() {
        testGenerateSize(0);
    }
    @Test
    public void testGenerateSize1() {
        testGenerateSize(1);
    }
    @Test
    public void testGenerateSize2() {
        testGenerateSize(2);
    }
    @Test
    public void testGenerateSize4() {
        testGenerateSize(4);
    }
    @Test
    public void testGenerateSize5() {
        testGenerateSize(5);
    }
    @Test
    public void testGenerateSize6() {
        testGenerateSize(6);
    }
    @Test
    public void testGenerateSize7() {
        testGenerateSize(7);
    }
    @Test
    public void testGenerateSize16() {
        testGenerateSize(16);
    }
}
