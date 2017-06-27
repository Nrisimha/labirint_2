
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
/**
 * Klasa Vizualization odpowiada za wyświetlenie procesu gry w postaci JFrame
 * i pozwala skorzystać się z widoku 3D (klasa View3D) oraz zawiera minimapę (klasa Minimap)
 * @author Maksym Titov
 */
public class Vizualization {
    //domyślne szyrokość
    //i wysokość okna JFrame
    private static int deffaultWidth = 800;
    private static int deffaultHeight = 600;
    public static JFrame window;

    public Vizualization(Maze maze) {
        window = new JFrame("L.A.B.I.R.Y.N.T.");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.setSize(deffaultWidth, deffaultHeight);
        
        JLayeredPane mainPanel = new JLayeredPane();
        mainPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
        mainPanel.setPreferredSize(new Dimension(window.getWidth(), window.getHeight()));

        window.add(mainPanel, BorderLayout.CENTER);

        final View3D view3d = new View3D(window.getWidth(), window.getHeight());
        view3d.setBounds(0, 0, window.getWidth(), window.getHeight());
        //set view3d as not transparent for drawing
        view3d.setOpaque(true);
        TileMap tileMap;
        tileMap = new TileMap(maze,32);//MazeGenerator.generate(3, 15), 32);
        GameMaster gameMaster = new GameMaster(new Player(tileMap, view3d),tileMap);
        MiniMapWithPlayer miniMap = new MiniMapWithPlayer(view3d, 200, 200, 0.3, gameMaster);
        miniMap.setBounds(0, 0, 200, 200);
        //set miniMap as not transparent for drawing
        miniMap.setOpaque(true);

        mainPanel.add(view3d, new Integer(0));
        mainPanel.add(miniMap, new Integer(1));

        window.pack();
        window.setVisible(true);
        window.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
               
                view3d.setSize(window.getWidth(), window.getHeight());
                view3d.setBounds(0, 0, window.getWidth(), window.getHeight());
                view3d.buffered3Dview = new BufferedImage(view3d.getWidth(), view3d.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
                view3d.gcOfBuffered3dView = view3d.buffered3Dview.createGraphics();
            }

            @Override
            public void componentMoved(ComponentEvent ce) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void componentShown(ComponentEvent ce) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void componentHidden(ComponentEvent ce) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

}
