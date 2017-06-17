
import java.awt.*;
import javax.swing.*;

public class VIZUALIZATION {
    //Create and set up a colored label.
    private static int deffaultWidth=800;
    private static int deffaultHeight=600;
    
    public static void main(String args[]) {
        JFrame window = new JFrame("LABIRINT");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());
        JLayeredPane mainPanel = new JLayeredPane();
        window.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setBounds(0, 0, deffaultWidth, deffaultHeight);
        mainPanel.setPreferredSize(new Dimension(deffaultWidth, deffaultHeight));
        View3D map3d = new View3D(deffaultWidth, deffaultHeight);
        map3d.setBounds(0, 0, deffaultWidth, deffaultHeight);
        map3d.setOpaque(true);
        
        MiniMap miniMap = new MiniMap(map3d, 200, 200, 0.3);
        miniMap.setBounds(0, 0, 200, 200);
        miniMap.setOpaque(true);
        mainPanel.add(map3d, new Integer(0));
        mainPanel.add(miniMap, new Integer(1));

        window.setSize(deffaultWidth, deffaultHeight);
        window.pack();
        window.setVisible(true);
    }
}
