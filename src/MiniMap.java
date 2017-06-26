
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

/**
 * Klasa MiniMap odpowiada za wyświetlenie mapy z widokiem od góry i
 * nasłuchiwanie klawiatury i myszki w celu przesuwania gracza (Player) w
 * labiryncie
 *
 * @author Maksym Titov
 */
public class MiniMap extends JPanel implements Runnable {
 //obrazek do zapisywania bieżącego poziomu labiryntu w postaci graficznej, skalowany obraz poziomu.
    protected BufferedImage buffered2Dmap, scaledBuffered2Dmap;
    //odpowiednie grafiki dla obrazów  buffered2Dmap i scaledBuffered2Dmap.
    protected Graphics2D BUFF2Dg, scaledBUFF2Dg;
    
    protected Thread renderer;
    protected boolean running;

    protected int FPS = 10;
    protected int targetTime = 1000 / FPS;

    protected TileMap tileMap;
    public MiniMap(){
        
    }
    public MiniMap(int width, int height) {
        super();
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);

        requestFocus();
        init();
    }

    /**
     * Dodajemy wątek renderowania dodajemy nasłuchiwanie klawiatury
     */
    public void addNotify() {
        super.addNotify();
        if (renderer == null) {
            renderer = new Thread(this);
            renderer.start();
        }
        
    }
    public TileMap getTileMap(){
        return tileMap;
    };
    /**
     * funkcja run() startuje wątek renderer
     */
    public void run() {

        long startTime;
        long urdTime;
        long waitTime; // in milliseconds

        while (running) {
           startTime = System.nanoTime();
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
        tileMap = new TileMap("testmap.txt", 32);
        buffered2Dmap = new BufferedImage(
                tileMap.getWidth() * tileMap.getTileSize(), tileMap.getHeight() * tileMap.getTileSize(),
                BufferedImage.TYPE_3BYTE_BGR);
        BUFF2Dg = buffered2Dmap.createGraphics();
    }
        @Override
    public synchronized void paintComponent(Graphics g) {

        tileMap.draw(BUFF2Dg);
        g.drawImage(buffered2Dmap, 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
