

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends javax.swing.JFrame {
    private boolean openEditor, openGame;
    private javax.swing.JButton New_Game;
    private javax.swing.JButton Editor;
    private javax.swing.JButton Exit;
    public static  Editor editor;
    public static  PreSettings preSettings;

    public Menu() {
        initComponents();
    }

                      
    private void initComponents() {
        New_Game = new javax.swing.JButton();
        Editor = new javax.swing.JButton();
        Exit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(new java.awt.Color(240, 240, 240));
        setPreferredSize(new java.awt.Dimension(800, 600));

        New_Game.setBackground(new java.awt.Color(51, 51, 51));
        New_Game.setFont(new java.awt.Font("Ravie", 0, 14));
        New_Game.setForeground(new java.awt.Color(240, 240, 240));
        New_Game.setText("Nowa Gra");
        openGame = false;
        Menu tmp=this;
        New_Game.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                if(!openGame)
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        preSettings= new PreSettings(tmp);
                        tmp.setVisible(false);
                        //game.setVisible(true);  
                        openGame = true;
                        preSettings.setVisible(openGame);
                        
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
