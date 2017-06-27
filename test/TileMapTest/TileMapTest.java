package TileMapTest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import source.TileMap;
import source.MazeGenerator;
import source.Maze;
import java.awt.Graphics2D;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maksym Titov
 */
public class TileMapTest {
    TileMap tileMap;
    int tileTestSize = 32;
    int mazeTestSize = 5;
    int mazeTestDim = 3;
    
    public TileMapTest() {
        tileMap = new TileMap(MazeGenerator.generate(mazeTestDim,mazeTestSize),tileTestSize);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadNextLevel method, of class TileMap.
     */
    @Test
    public void testLoadNextLevel() {
        System.out.println("loadNextLevel");
        //TileMap instanc = null;
        boolean expResult = true;
        boolean result = tileMap.loadNextLevel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of loadPreviousLevel method, of class TileMap.
     */
    @Test
    public void testLoadPreviousLevel() {
        System.out.println("loadPreviousLevel");
        //TileMap instanc = null;
        boolean expResult = false;
        boolean result = tileMap.loadPreviousLevel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getx method, of class TileMap.
     */
    @Test
    public void testGetx() {
        System.out.println("getx");
        //TileMap instanc = null;
        int expResult = 0;
        int result = tileMap.getx();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of gety method, of class TileMap.
     */
    @Test
    public void testGety() {
        System.out.println("gety");
        //TileMap instanc = null;
        int expResult = 0;
        int result = tileMap.gety();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getColTile method, of class TileMap.
     */
    @Test
    public void testGetColTile() {
        System.out.println("getColTile");
        int x = 0;
        //TileMap instanc = null;
        int expResult = 0;
        int result = tileMap.getColTile(x);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getRowTile method, of class TileMap.
     */
    @Test
    public void testGetRowTile() {
        System.out.println("getRowTile");
        int y = 0;
        //TileMap instanc = null;
        int expResult = 0;
        int result = tileMap.getRowTile(y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getTile method, of class TileMap.
     */
    @Test
    public void testGetTile() {
        System.out.println("getTile");
        int row = 0;
        int col = 0;
        //TileMap instanc = null;
        int expResult = Maze.HARDWALL;
        int result = tileMap.getTile(row, col);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setTile method, of class TileMap.
     */
    @Test
    public void testSetTile() {
        System.out.println("setTile");
        int row = 0;
        int col = 0;
        int value = 0;
        //TileMap instanc = null;
        tileMap.setTile(row, col, value);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getTileSize method, of class TileMap.
     */
    @Test
    public void testGetTileSize() {
        System.out.println("getTileSize");
        //TileMap instanc = null;
        int expResult = tileTestSize;
        int result = tileMap.getTileSize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getHeight method, of class TileMap.
     */
    @Test
    public void testGetHeight() {
        System.out.println("getHeight");
        //TileMap instanc = null;
        int expResult = mazeTestSize;
        int result = tileMap.getHeight();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getWidth method, of class TileMap.
     */
    @Test
    public void testGetWidth() {
        System.out.println("getWidth");
        //TileMap instanc = null;
        int expResult = mazeTestSize;
        int result = tileMap.getWidth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setHeight method, of class TileMap.
     */
    @Test
    public void testSetHeight() {
        System.out.println("setHeight");
        int height = mazeTestSize;
        //TileMap instanc = null;
        tileMap.setHeight(height);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setWidth method, of class TileMap.
     */
    @Test
    public void testSetWidth() {
        System.out.println("setWidth");
        int width = mazeTestSize;
        //TileMap instanc = null;
        tileMap.setWidth(width);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setTileSize method, of class TileMap.
     */
    @Test
    public void testSetTileSize() {
        System.out.println("setTileSize");
        int size = tileTestSize;
        //TileMap instanc = null;
        tileMap.setTileSize(size);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setx method, of class TileMap.
     */
    @Test
    public void testSetx() {
        System.out.println("setx");
        int i = 0;
        //TileMap instanc = null;
        tileMap.setx(i);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of sety method, of class TileMap.
     */
    @Test
    public void testSety() {
        System.out.println("sety");
        int i = 0;
        //TileMap instanc = null;
        tileMap.sety(i);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
