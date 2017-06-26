

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends javax.swing.JFrame {
    private boolean openEditor, openGame;
    private javax.swing.JButton New_Game;
    private javax.swing.JButton Editor;
    private javax.swing.JButton Exit;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;  
    public static  Editor editor;
    public static  Vizualization game;

    public Menu() {
        initComponents();
    }

                      
    private void initComponents() {
        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jFrame3 = new javax.swing.JFrame();
        New_Game = new javax.swing.JButton();
        Editor = new javax.swing.JButton();
        Exit = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(new java.awt.Color(240, 240, 240));
        setPreferredSize(new java.awt.Dimension(800, 600));

        New_Game.setBackground(new java.awt.Color(51, 51, 51));
        New_Game.setFont(new java.awt.Font("Ravie", 0, 14));
        New_Game.setForeground(new java.awt.Color(240, 240, 240));
        New_Game.setText("Nowa Gra");
        openGame = false;
        New_Game.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                if(!openGame)
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        game= new Vizualization(MazeGenerator.generate(3, 15));
                        //game.setVisible(true);  
                        openGame = true;
                 }
            });
                else{
                     //editor.setVisible(true);
                setVisible(false);
                }
            } 
        });

        Editor.setBackground(new java.awt.Color(51, 51, 51));
        Editor.setFont(new java.awt.Font("Ravie", 0, 14));
        Editor.setForeground(new java.awt.Color(240, 240, 240));
        Editor.setText("Edytor");
        openEditor = false;
        Editor.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                if(!openEditor)
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        editor = new Editor();
                        editor.setVisible(true);  
                        openEditor = true;
                 }
            });
                else
                     editor.setVisible(true);
                editor.menu.setVisible(false);
            } 
        });

        Exit.setBackground(new java.awt.Color(51, 51, 51));
        Exit.setFont(new java.awt.Font("Ravie", 0, 14));
        Exit.setForeground(new java.awt.Color(240, 240, 240));
        Exit.setText("Wyjscie");
        Exit.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                System.exit(0);
            } 
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(300, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Editor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(New_Game, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
                .addContainerGap(291, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addComponent(New_Game, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(Editor, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(Exit, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap(100, Short.MAX_VALUE))
        );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getAccessibleContext().setAccessibleDescription("Menu");
        setTitle("Menu L.A.B.I.R.Y.N.T.");
        pack();
    }                   
}
