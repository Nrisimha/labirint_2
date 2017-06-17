
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MiniMap extends JPanel implements Runnable, KeyListener {

    private Thread thread;
    private boolean running;

    private BufferedImage buffered2Dmap;
    private Graphics2D BUFF2Dg;

    private int FPS = 30;
    private int targetTime = 1000 / FPS;

    private TileMap tileMap;
    private Player player;
    private View3D map3d;
    public final double scale;
    public MiniMap(View3D map3d, int width, int height, double scale) {
        super();
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        //WIDTH=width;

        requestFocus();
        this.map3d = map3d;
        this.scale=scale;
    }

    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }

    public void run() {
        init();
        long startTime;
        long urdTime;
        long waitTime;
        while (running) {
            startTime = System.nanoTime();

            update();
            this.paintComponent(this.getGraphics());

            urdTime = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - urdTime;

            try {
                Thread.sleep(waitTime);
            } catch (Exception e) {
            }
        }
    }

    public void init() {
        running = true;
        // load map image
        /*
        try {
            image = ImageIO.read(getClass().getResourceAsStream("map.png"));
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
        //backBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        tileMap = new TileMap(new Maze(3,15), 32);
        //tileMap = new TileMap("testmap.txt", 32);
        buffered2Dmap = new BufferedImage(
                tileMap.getWidth() * tileMap.getTileSize()+10*tileMap.getTileSize(), tileMap.getHeight() * tileMap.getTileSize()+10*tileMap.getTileSize(),
                BufferedImage.TYPE_3BYTE_BGR);
        BUFF2Dg = buffered2Dmap.createGraphics();

        player = new Player(tileMap, map3d,this);
        player.setx((int)(tileMap.getTileSize()*1.5));
        player.sety((int)(tileMap.getTileSize()*1.5));
        map3d.setPlayer(player);
        map3d.setTileMap(tileMap);

    }
    ///////////////////////////////////////////////////////////////

    private void update() {
        player.update();
    }

    public synchronized void paintComponent(Graphics g) {
        BUFF2Dg.clearRect(0, 0, buffered2Dmap.getWidth(), buffered2Dmap.getHeight());
        tileMap.draw(BUFF2Dg);

//        try {
//            ImageIO.write(image, "PNG", new File("filename1.png"));
//        } catch (IOException ex) {
//            Logger.getLogger(MiniMap.class.getName()).log(Level.SEVERE, null, ex);
//        }
        player.draw(BUFF2Dg, buffered2Dmap);
        
        BufferedImage bi = new BufferedImage((int)(scale * buffered2Dmap.getWidth(null)),
                                             (int)(scale * buffered2Dmap.getHeight(null)),
                                             BufferedImage.TYPE_INT_ARGB);
;

        Graphics2D grph = (Graphics2D) bi.getGraphics();
        grph.scale(scale, scale);
         grph.drawImage(buffered2Dmap, 0, 0, null);
        g.drawImage(bi, 0, 0, null);
    }

    public void keyTyped(KeyEvent key) {
    }

    public void keyPressed(KeyEvent key) {
        //activateSecond
        if(key.getKeyChar() == '\\'){
            if(player.activateSecond)
                player.activateSecond=false;
            else
                player.activateSecond=true;
        }
        //draw3DWalls
        if(key.getKeyChar() == '`'){
            if(player.draw3DWalls)
                player.draw3DWalls=false;
            else
                player.draw3DWalls=true;
        }
        //left
        if ((key.getKeyCode() == 37 || key.getKeyCode()==65)) {
            player.left=true;
        }//right
        else if ((key.getKeyCode() == 39 || key.getKeyCode()==68)) {
            player.right=true;
        }
        //straight
        if ((key.getKeyCode() == 38 || key.getKeyCode()==87)) {
            player.up=true;
            
        }//down
        else if ((key.getKeyCode() == 40 || key.getKeyCode()==83)) {
            player.down=true;
        }
        //z
        if ((key.getKeyCode() == 90 || key.getKeyCode()==91)) {
            player.rotateLeft=true;
        }//x
        else if ((key.getKeyCode() == 88 || key.getKeyCode()==93)) {
            player.rotateRight=true;
        }
        //teleport up
        if(key.getKeyChar() == '+'){
            player.teleport(tileMap.upTeleport);
        }
        //teleport down
        if(key.getKeyChar() == '-'){
            player.teleport(tileMap.downTeleport);
            
        }
        repaint();
    }

    public void keyReleased(KeyEvent key) {
         //left
        if ((key.getKeyCode() == 37 || key.getKeyCode()==65)) {
            player.left=false;
        }//right
        else if ((key.getKeyCode() == 39 || key.getKeyCode()==68)) {
            player.right=false;
        }
        //straight
        if ((key.getKeyCode() == 38 || key.getKeyCode()==87)) {
            player.up=false;
            
        }//down
        else if ((key.getKeyCode() == 40 || key.getKeyCode()==83)) {
            player.down=false;
        }
        //z
        if ((key.getKeyCode() == 90 || key.getKeyCode()==91)) {
            player.rotateLeft=false;
        }//x
        else if ((key.getKeyCode() == 88 || key.getKeyCode()==93)) {
            player.rotateRight=false;
        }
    }

}
