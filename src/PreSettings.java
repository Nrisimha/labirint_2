
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class PreSettings extends JFrame {

    private boolean openMenu, openGame;
    private javax.swing.JButton LoadMaze;
    private javax.swing.JButton NewMaze;
    private javax.swing.JButton ShowMenu;
    private javax.swing.JComboBox mazeSizeList;
    private javax.swing.JComboBox mazeDimentionList;
    public Menu menu;
    public static Vizualization game;

    public PreSettings() {
        initComponents();
    }

    public PreSettings(Menu menu) {
        this.menu = menu;
        initComponents();
    }

    private void initComponents() {
        Vector mazeSizeArray = new Vector();
        for (int i = 5; i < 33; ++i) {
            mazeSizeArray.add(i);
        }
        mazeSizeList = new JComboBox(mazeSizeArray);
        mazeSizeList.setSelectedIndex(7);

        Vector mazeDimentionArray = new Vector();
        for (int i = 2; i < 5; ++i) {
            mazeDimentionArray.add(i);
        }
        mazeDimentionList = new JComboBox(mazeDimentionArray);
        mazeDimentionList.setSelectedIndex(1);

        LoadMaze = new javax.swing.JButton();
        NewMaze = new javax.swing.JButton();
        ShowMenu = new javax.swing.JButton();
        PreSettings tmp = this;
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(new java.awt.Color(240, 240, 240));
        setPreferredSize(new java.awt.Dimension(800, 600));

        LoadMaze.setBackground(new java.awt.Color(51, 51, 51));
        LoadMaze.setFont(new java.awt.Font("Ravie", 0, 14));
        LoadMaze.setForeground(new java.awt.Color(240, 240, 240));
        LoadMaze.setText("Wgraj labirynt z pliku");
        openGame = false;

        LoadMaze.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!openGame) {
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            JOptionPane.showMessageDialog(null, "Tego jeszcze nie napisali!");
                            /*game = new Vizualization(MazeGenerator.generate((int)mazeDimentionList.getSelectedItem(),(int)mazeSizeList.getSelectedItem()));
                            openGame = true;*/
                        }
                    });
                } else {
                    /*
                    setVisible(false);
                     */
                }
            }
        }
        );
        NewMaze.setBackground(new java.awt.Color(51, 51, 51));
        NewMaze.setFont(new java.awt.Font("Ravie", 0, 14));
        NewMaze.setForeground(new java.awt.Color(240, 240, 240));
        NewMaze.setText("Stwórż nowy");
        openGame = false;

        NewMaze.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (!openGame) {
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {

                            tmp.setVisible(false);
                            game = new Vizualization(MazeGenerator.generate((int) mazeDimentionList.getSelectedItem(), (int) mazeSizeList.getSelectedItem()));
                            //game.setVisible(true);  
                            openGame = true;
                        }
                    });
                } else {
                    //editor.setVisible(true);
                    setVisible(false);
                }
            }
        }
        );

        ShowMenu.setBackground(new java.awt.Color(51, 51, 51));
        ShowMenu.setFont(new java.awt.Font("Ravie", 0, 14));
        ShowMenu.setForeground(new java.awt.Color(240, 240, 240));
        ShowMenu.setText("Powrót do menu");
        ShowMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(true);
                setVisible(false);
            }
        });
        Box box = Box.createHorizontalBox();
        //box.add(Box.createHorizontalGlue());
        box.add(NewMaze);
        box.add(mazeDimentionList);
        box.add(mazeSizeList);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(300, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(LoadMaze, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(box, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ShowMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(300, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap(100, Short.MAX_VALUE)
                        .addComponent(LoadMaze, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(box, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(ShowMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                        .addContainerGap(100, Short.MAX_VALUE))
        );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getAccessibleContext().setAccessibleDescription("Menu");
        setTitle("Menu L.A.B.I.R.Y.N.T.");
        pack();
    }
}
