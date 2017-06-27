package source;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 *
 * @author Stefan
 */
public class Editor extends javax.swing.JFrame {
    
    private int actual_Type_of_Fild;
    private int level;
    private int cube;
    private javax.swing.JMenu File;
    private javax.swing.JMenu Exit;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenuItem Load;
    private javax.swing.JMenuItem Save;
    private javax.swing.JMenuItem Generate;
    private javax.swing.JMenuItem Exit_full;
    private javax.swing.JMenuItem Exit_To_Menu;
    private javax.swing.JPanel PanelLeft;
    private javax.swing.JPanel PanelRight;
    private javax.swing.JSplitPane Split;
    private javax.swing.JButton Wall_normal;
    private javax.swing.JButton Wall_undestructable;
    private javax.swing.JButton Empty_space;
    private javax.swing.JButton Door_UP;
    private javax.swing.JButton Door_DOWN;
    private javax.swing.JButton Door;
    private javax.swing.JButton UP;
    private javax.swing.JButton DOWN;
    private javax.swing.JButton Level_UP;
    private javax.swing.JButton Level_DOWN;
    private javax.swing.JButton Cube_UP;
    private javax.swing.JButton Cube_DOWN;
    
    
    private MiniMap Map;   
    
    public Editor(){
        initComponents();
        
    }
    
   
    
                   
    private void initComponents()  {
        
        final int height_of_map = 500;
        final int width_of_map = 600;
        level= 0;
        cube = 0;
        Split = new javax.swing.JSplitPane();
        PanelLeft = new javax.swing.JPanel();
        MenuBar = new javax.swing.JMenuBar();
        File = new javax.swing.JMenu();
        Load = new javax.swing.JMenuItem();
        Generate = new javax.swing.JMenuItem();
        Save = new javax.swing.JMenuItem();
        Exit = new javax.swing.JMenu();
        Wall_normal = new javax.swing.JButton();
        Door_UP = new javax.swing.JButton();
        Door_DOWN = new javax.swing.JButton();
        Door = new javax.swing.JButton();
        UP = new javax.swing.JButton();
        DOWN = new javax.swing.JButton();
        Wall_undestructable = new javax.swing.JButton();
        Level_UP = new javax.swing.JButton();
        Level_DOWN = new javax.swing.JButton();
        Cube_UP = new javax.swing.JButton();
        Cube_DOWN = new javax.swing.JButton();
        Empty_space = new javax.swing.JButton();
        Exit_full = new javax.swing.JMenuItem();
        Exit_To_Menu = new javax.swing.JMenuItem();
        PanelRight = new javax.swing.JPanel();
        Map = new MiniMap(width_of_map,height_of_map);
        
        
        
        
        PanelRight.add(Map);
        Wall_normal.setText("Ściana");
        Wall_normal.setBackground(new java.awt.Color(0, 0, 0));
        Wall_normal.setForeground(new java.awt.Color(255, 255, 255));
        Wall_undestructable.setText("Ściana niezniszczalna");
        Wall_undestructable.setBackground(new java.awt.Color(0, 0, 0));
        Wall_undestructable.setForeground(new java.awt.Color(255, 255, 255));
        Empty_space.setText("Pusty obszar");
        Empty_space.setBackground(new java.awt.Color(255, 255, 255));
        Empty_space.setForeground(new java.awt.Color(0, 0, 0));
        
        Level_UP.setText("Nastepny poziom");
        Level_UP.setBackground(new java.awt.Color(255,255,255));
        Level_UP.setForeground(new java.awt.Color(0,0,0));
        
        Level_DOWN.setText("Poprzedni poziom");
        Level_DOWN.setBackground(new java.awt.Color(255,255,255));
        Level_DOWN.setForeground(new java.awt.Color(0,0,0));
        
        Cube_UP.setText("Nastepny sześcian");
        Cube_UP.setBackground(new java.awt.Color(255,255,255));
        Cube_UP.setForeground(new java.awt.Color(0,0,0));
        
        Cube_DOWN.setText("Poprzedni sześcian");
        Cube_DOWN.setBackground(new java.awt.Color(255,255,255));
        Cube_DOWN.setForeground(new java.awt.Color(0,0,0));
        
        Door_UP.setText("Portal do wyższego sześcianu");
        Door_UP.setBackground(new java.awt.Color(255,40,20));
        Door_UP.setForeground(new java.awt.Color(255,255,255));
        
        Door_DOWN.setText("Portal do niższego sześcianu");
        Door_DOWN.setBackground(new java.awt.Color(0,0,255));
        Door_DOWN.setForeground(new java.awt.Color(255,255,255));
        
        Door.setText("Portal do szecianu obok");
        Door.setBackground(new java.awt.Color(0,255,0));
        Door.setForeground(new java.awt.Color(255,255,255));
        
        UP.setText("Portal do poziomu wyżej");
        UP.setBackground(new java.awt.Color(0,0,255));
        UP.setForeground(new java.awt.Color(255,255,255));
        
        DOWN.setText("Portal do poziomu niżej");
        DOWN.setBackground(new java.awt.Color(255,40,20));
        DOWN.setForeground(new java.awt.Color(255,255,255));
        
        
        
        javax.swing.GroupLayout Layout_Left = new javax.swing.GroupLayout(PanelLeft);
        PanelLeft.setLayout(Layout_Left);
        Layout_Left.setHorizontalGroup(
            Layout_Left.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Layout_Left.createSequentialGroup()
                .addContainerGap()
                .addGroup(Layout_Left.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Wall_normal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Wall_undestructable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Empty_space, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Door_UP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Door_DOWN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Door, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(UP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DOWN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Level_UP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Level_DOWN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Cube_UP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Cube_DOWN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        
                )
                .addContainerGap(17, Short.MAX_VALUE))
        );
        Layout_Left.setVerticalGroup(
            Layout_Left.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Layout_Left.createSequentialGroup()
                .addComponent(Wall_normal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Wall_undestructable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Empty_space, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Door_UP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Door_DOWN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Door, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DOWN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Level_UP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Level_DOWN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Cube_UP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Cube_DOWN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGap(0, 46, Short.MAX_VALUE))
        );
        PanelLeft.setMaximumSize(new java.awt.Dimension(150, 100));
        PanelLeft.setMinimumSize(new java.awt.Dimension(140, 100));
        Split.setRightComponent(PanelRight);
        Split.setLeftComponent(PanelLeft);
        getContentPane().add(Split, java.awt.BorderLayout.CENTER);
        File.setText("Plik");
        Load.setText("Wczytaj");
        Generate.setText("Generuj");
        File.add(Load);
        Save.setText("Zapisz");
        File.add(Save);
        File.add(Generate);
        MenuBar.add(File);
        Exit.setText("Wyjście");
        Exit_full.setText("Do systemu");
        Exit.add(Exit_full);
        Exit_To_Menu.setText("Do menu");
        Exit.add(Exit_To_Menu);
        MenuBar.add(Exit);
        actual_Type_of_Fild = Maze.FLOOR;
        
        
        Map.addMouseListener(new MouseAdapter() { 
          public void mousePressed(MouseEvent me) { 
            int scaleX = width_of_map / Map.getTileMap().getWidth();
            int scaleY = height_of_map / Map.getTileMap().getHeight();
            Point p = me.getPoint();
            Map.getTileMap().setTile(p.y/scaleY, p.x/scaleX, actual_Type_of_Fild);
            if(Map.tileMap.maze!=null)
                if(Map.tileMap.maze.getDimension()==2)
                    Map.tileMap.maze.set(actual_Type_of_Fild,p.y/scaleY, p.x/scaleX);
                else if(Map.tileMap.maze.getDimension()==3)
                    Map.tileMap.maze.set(actual_Type_of_Fild,p.y/scaleY, p.x/scaleX,level);
                else if(Map.tileMap.maze.getDimension()==4)
                    Map.tileMap.maze.set(actual_Type_of_Fild,p.y/scaleY, p.x/scaleX,level,cube);
          } 
        }); 
        
        
        Wall_normal.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                actual_Type_of_Fild = Maze.WALL;
            } 
        });
        Wall_undestructable.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                actual_Type_of_Fild = Maze.HARDWALL;
            } 
        });
        Empty_space.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                actual_Type_of_Fild = Maze.FLOOR;
            } 
        });
        UP.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                actual_Type_of_Fild = Maze.UP;
            } 
        });
        DOWN.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                actual_Type_of_Fild = Maze.DOWN;
            } 
        });
        Door_UP.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                actual_Type_of_Fild = Maze.DOORUP;
            } 
        });
        Door_DOWN.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                actual_Type_of_Fild = Maze.DOORDOWN;
            } 
        });
        Door.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                actual_Type_of_Fild = Maze.DOOR;
            } 
        });
        Level_UP.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                if(level<Map.tileMap.getHeight()-1&&Map.tileMap.maze.getDimension()>2){
                    System.out.println(level + " "+Map.tileMap.getHeight());
                    Map.tileMap.loadNextLevel();
                    level++;
                }else
                    JOptionPane.showMessageDialog(null, "To maksymalny poziom");
                
            } 
        });
        Level_DOWN.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                if(level>0&&Map.tileMap.maze.getDimension()>2){
                    Map.tileMap.loadPreviousLevel();
                    level--;
                }else
                    JOptionPane.showMessageDialog(null, "To zerowy poziom");
            } 
        });
        Cube_UP.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                JOptionPane.showMessageDialog(null, "not implemented yet");
            } 
        });
        Cube_DOWN.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                JOptionPane.showMessageDialog(null, "not implemented yet");
            } 
        });
        Exit_full.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                System.exit(0);
            } 
        });
        Exit_To_Menu.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                menu.setVisible(true);
                setVisible(false);
            } 
        });
        Generate.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                level = 0;
                String dim;
                dim = JOptionPane.showInputDialog("Ile wymiarów, wpisz 2 lub 3");
                int dimInt = Integer.parseInt(dim);
                /*String size;
                size = JOptionPane.showInputDialog("Ile wymiarów, wpisz od liczbę 5 do 32");
                int sizeInt = Integer.parseInt(dim);*/
                Map.tileMap.maze = MazeGenerator.generate(dimInt,31);
                Map.tileMap.reload();
                
            } 
        });
        Load.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                level = 0;
                MazeFile m = new MazeFile();
                String path;
                path = JOptionPane.showInputDialog("Podaj ścieżkę");
                Map.tileMap.maze = m.load(path);
                Map.tileMap.reload();
                
            } 
        });
        Save.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                MazeFile m = new MazeFile();
                String path;
                path = JOptionPane.showInputDialog("podaj nazwę");
                path += ".maze";
                m.save(Map.tileMap.maze,path);
                
            } 
        });
        setJMenuBar(MenuBar);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(new java.awt.Color(240, 240, 240));
        setPreferredSize(new java.awt.Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Edytor L.A.B.I.R.Y.N.T.");

        

        pack();
    }     
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                menu = new Menu();
                menu.setVisible(true);    
            }
        });
    }
        public static Menu menu;
        
         
    
}
