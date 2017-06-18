
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class VIZUALIZATION {

    private static int deffaultWidth = 800;
    private static int deffaultHeight = 600;
    public static JFrame window;

    public static void main(String args[]) {
        window = new JFrame("LABIRINT");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.setSize(deffaultWidth, deffaultHeight);

        JLayeredPane mainPanel = new JLayeredPane();
        mainPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
        mainPanel.setPreferredSize(new Dimension(window.getWidth(), window.getHeight()));

        window.add(mainPanel, BorderLayout.CENTER);

        View3D map3d = new View3D(window.getWidth(), window.getHeight());
        map3d.setBounds(0, 0, window.getWidth(), window.getHeight());
        //set map3d as not transparent for drawing
        map3d.setOpaque(true);

        MiniMap miniMap = new MiniMap(map3d, 200, 200, 0.3);
        miniMap.setBounds(0, 0, 200, 200);
        //set miniMap as not transparent for drawing
        miniMap.setOpaque(true);

        mainPanel.add(map3d, new Integer(0));
        mainPanel.add(miniMap, new Integer(1));

        window.pack();
        window.setVisible(true);
        window.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
               
                map3d.setSize(window.getWidth(), window.getHeight());
                map3d.setBounds(0, 0, window.getWidth(), window.getHeight());
                map3d.buffered3Dview = new BufferedImage(map3d.getWidth(), map3d.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
                map3d.gcOfBuffered3dView = map3d.buffered3Dview.createGraphics();
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
