/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MazeGeneratorTest;

import org.junit.Test;

/**
 *
 * @author Piotr Bartman
 */
public class CompleteTest {
    
    public CompleteTest() {
        System.out.println("Test of MazeGenerator.generate():");
    }
    
    @Test
    public void testGenerateSizeAll() {
        SizeTest mgst = new SizeTest();
        for (int i=-8; i<128; i++)
            mgst.testGenerateSize(i);
    }
    
    @Test
    public void testGenerateDimensionAll() {
        DimensionTest mgdt = new DimensionTest();
        for (int i=-8; i<128; i++)
            mgdt.testGenerateDimension(i);
    }
    
    @Test
    public void testGenerate2DAll() {
        GenerationTest gt = new GenerationTest();
        for (int i=-8; i<128; i++)
            gt.testGenerate(2, i);
    }
    
    @Test
    public void testGenerate3DAll() {
        GenerationTest gt = new GenerationTest();
        for (int i=-8; i<128; i++)
            gt.testGenerate(3, i);
    }
}
