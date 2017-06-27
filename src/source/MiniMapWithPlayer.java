package source;

import java.awt.event.KeyListener;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.JOptionPane;


public class MiniMapWithPlayer extends MiniMap implements KeyListener{
   //obraz poziomu labiryntu bez gracza
    private BufferedImage buffered2DmapWithoutPlayer;
    private Graphics2D BUFF2DgWithoutPlayer;
    //activateSecond - zmiana trybu rysowania podłogi i sufitu
    public boolean activateSecond = true,
            transparentWalls = false;
    private Player player;
    private View3D view3d;
    public final double scale;

    /**
     * Klasa WallType stworzona po to, żeby przechowywać odległość do ściany i
     * kolor ściany
     */
    private class WallType {

        public double dist;
        public Color color;

        public WallType() {
            dist = 0;
            color = Color.BLACK;
        }
    }

    /**
     *
     * @param view3d - widok 3D, na którym wyświetlamy obszar widoku grawca na
     * obecnej mapie
     * @param width - szerokość minimapy na ekranie w pikselach
     * @param height - wysokość minimapy na ekranie w pikselach
     * @param scale - skalowanie minimapy dla bardziej wygodnego wyświetlania
     */
    public MiniMapWithPlayer(View3D view3d, int width, int height, double scale, GameMaster gameMaster) {
        addKeyListener(this);
        setPreferredSize(new Dimension(width, height));
        setSize(width, height);
        setFocusable(true);

        requestFocus();
        this.view3d = view3d;
        this.scale = scale;
        init(gameMaster);
        buffered2DmapWithoutPlayer = new BufferedImage(
                tileMap.getWidth() * tileMap.getTileSize(), tileMap.getHeight() * tileMap.getTileSize(),
                BufferedImage.TYPE_INT_ARGB);
        BUFF2DgWithoutPlayer = (Graphics2D) buffered2DmapWithoutPlayer.getGraphics();

    }


    /**
     * inicjalizacja
     *
     * @param gameMaster - odpowiedni master gry
     */
    public void init(GameMaster gameMaster) {
        //zezwolenie na odpolanie wątka
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
        //tworzenie obeccnej mapy w postaci tablicy
        tileMap = gameMaster.tileMap;
        //tileMap = new TileMap("testmap.txt", 32);
        //ustalenie koordynat minimapy 
        tileMap.setx((int) (this.getWidth() / this.scale / 2 - (int) (tileMap.getTileSize() * 1.5)));
        tileMap.sety((int) (this.getHeight() / this.scale / 2 - (int) (tileMap.getTileSize() * 1.5)));
        buffered2Dmap = new BufferedImage(
                tileMap.getWidth() * tileMap.getTileSize() + 10 * tileMap.getTileSize(), tileMap.getHeight() * tileMap.getTileSize() + 10 * tileMap.getTileSize(),
                BufferedImage.TYPE_3BYTE_BGR);
        BUFF2Dg = buffered2Dmap.createGraphics();
        scaledBuffered2Dmap = new BufferedImage(
                (int) (scale * buffered2Dmap.getWidth(null)),
                (int) (scale * buffered2Dmap.getHeight(null)),
                BufferedImage.TYPE_INT_ARGB);
        scaledBUFF2Dg = (Graphics2D) scaledBuffered2Dmap.getGraphics();
        scaledBUFF2Dg.scale(scale, scale);
        player = gameMaster.player;
        player.setx((int) (tileMap.getTileSize() * 1.5));
        player.sety((int) (tileMap.getTileSize() * 1.5));
        view3d.setPlayer(player);
        view3d.setTileMap(tileMap);

    }
    @Override
     public void run() {

        long startTime;
        long urdTime;
        long waitTime; // in milliseconds
        player.startTime = System.nanoTime();
        while (running) {
            startTime = System.nanoTime();
            this.paintComponent(this.getGraphics());
            update();

            urdTime = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - urdTime;
            //JOptionPane.showMessageDialog(null, 1000/waitTime); // real FPS
//            
//            BUFF2Dg.setColor(Color.GREEN);
//            BUFF2Dg.setFont(new Font("Serif", Font.BOLD, 24));
//            BUFF2Dg.drawString("FPS: " + 1000/waitTime, 20, 20);
            try {
                Thread.sleep(waitTime);
            } catch (Exception e) {
            }
        }
    }
    /**
     *onowienie stanu 
     */
    private void update() {
        player.update();
        //move the map
        tileMap.setx((int) (this.getWidth() / this.scale / 2 - player.getx()));
        tileMap.sety((int) (this.getHeight() / this.scale / 2 - player.gety()));
    }
    @Override
        public synchronized void paintComponent(Graphics g) {

        tileMap.draw(BUFF2Dg);
//        try {
//            ImageIO.write(image, "PNG", new File("filename1.png"));
//        } catch (IOException ex) {
//            Logger.getLogger(MiniMap.class.getName()).log(Level.SEVERE, null, ex);
//        }
        draw(BUFF2Dg, buffered2Dmap);

        scaledBUFF2Dg.drawImage(buffered2Dmap, 0, 0, null);
        g.drawImage(scaledBuffered2Dmap, 0, 0, null);
    }
    public void draw(Graphics2D BUFF2Dg, BufferedImage buffered2Dmap) {
        int tx = tileMap.getx();
        int ty = tileMap.gety();

        BUFF2DgWithoutPlayer.drawImage(buffered2Dmap, -(int) (this.getWidth() / this.scale / 2 - player.getx()), -(int) (this.getHeight() / this.scale / 2 - player.gety()), null);

        BUFF2Dg.setColor(player.playerPointColor);
        BUFF2Dg.fillOval(
                (int) (tx + player.getx() - player.playerPointRadius), (int) (ty + player.gety() - player.playerPointRadius),
                player.playerPointRadius * 2, player.playerPointRadius * 2
        );
        // draw walls
        if (activateSecond) {
            BufferedImage tmp = new BufferedImage(buffered2Dmap.getWidth(), buffered2Dmap.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics2D goi = (Graphics2D) tmp.getGraphics();
            goi.translate(tmp.getWidth() >> 1, 0);
            goi.rotate(player.cameraAngle + Math.toRadians(0)); //вместо вида вращается миникарта
            goi.translate(-player.getx(), -player.gety());
            goi.drawImage(buffered2Dmap, -(int) (this.getWidth() / this.scale / 2 - player.getx() - 0), -(int) (this.getHeight() / this.scale / 2 - player.gety() - 0), null);

            for (int y = (int) 20; y < view3d.getHeight() / 2; y++) {
                double z = player.distToProjectionPlane * 20 / y;
                double frustrumWidthAtZ = (view3d.getWidth() / 2) * 20 / y;
                int x1 = (int) (tmp.getWidth() / 2 - frustrumWidthAtZ);
                int x2 = (int) (tmp.getWidth() / 2 + frustrumWidthAtZ);
                int dy = view3d.getHeight() / 2 + 1 * y;
                view3d.gcOfBuffered3dView.drawImage(tmp, 0, dy, view3d.getWidth(), dy + 1, x2, (int) z, x1, (int) z + 1, null);
            }
            for (int y = (int) 20; y < view3d.getHeight() / 2; y++) {
                double z = player.distToProjectionPlane * 20 / y;
                double frustrumWidthAtZ = (view3d.getWidth() / 2) * 20 / y;
                int x1 = (int) (tmp.getWidth() / 2 - frustrumWidthAtZ);
                int x2 = (int) (tmp.getWidth() / 2 + frustrumWidthAtZ);
                int dy = view3d.getHeight() / 2 - 1 * y;
                view3d.gcOfBuffered3dView.drawImage(tmp, 0, dy, view3d.getWidth(), dy + 1, x2, (int) z, x1, (int) z + 1, null);
            }
        } else {
            view3d.drawSimpleFloor3D();
        }

        view3d.gcOfBuffered3dView.drawImage(view3d.shadeCeilFloor, 0, 0, view3d.getWidth(), view3d.getHeight(), 0, 0, view3d.shadeCeilFloor.getWidth(), view3d.shadeCeilFloor.getHeight(), null);

        for (int x1 = -(view3d.getWidth() / 2), x2 = (view3d.getWidth() / 2); x1 <= 0 && x2 >= 0; x1++, x2--) {
            double left_a = Math.atan(x1 / player.distToProjectionPlane);
            WallType LeftSideCameraWallDistance = castRay(player.getx(), player.gety(), player.cameraAngle - left_a, BUFF2Dg, Color.BLUE,10);
            double left_z = LeftSideCameraWallDistance.dist * Math.cos(left_a);

            double right_a = Math.atan(x2 / player.distToProjectionPlane);
            WallType RightSideCameraWallDistance = castRay(player.getx(), player.gety(), player.cameraAngle - right_a, BUFF2Dg, Color.BLUE,10);
            double right_z = RightSideCameraWallDistance.dist * Math.cos(right_a);
            if (transparentWalls == false) {
                view3d.drawWall3D(left_z, LeftSideCameraWallDistance.color, right_z, RightSideCameraWallDistance.color, x1 + view3d.getWidth() / 2, x2 + view3d.getWidth() / 2);
            }

        }
        // draw camera direction
        castRay(player.getx(), player.gety(), player.cameraAngle, BUFF2Dg, Color.ORANGE,255);
        view3d.render();
    }
/**
 * 
 * @param cameraPositionX
 * @param cameraPositionY
 * @param cameraDirectionAngle
 * @param g - grafika, w której rysujemy promieni
 * @param rayColor - kolor promieniu, który rysujemy od grawca do ściany
 * @param transparencyLevel - pozion przezroczystości, 0 (całkowicie przezroczysty)... 255 (nieprzezroczysty)
 * @return WallType, czyli odległość do ściany oraz jej kolor
 */
    private WallType castRay(
            double cameraPositionX, double cameraPositionY, double cameraDirectionAngle,
            Graphics g, Color rayColor, int transparencyLevel
    ) {
        double s = Math.sin(cameraDirectionAngle);
        double c = Math.cos(cameraDirectionAngle);
        double d = player.playerPointRadius + 1;//out of radius of player point
        int currentColor = 0;
        int px = 0, py = 0;
        do {
            d += 0.1;
            px = (int) (cameraPositionX + d * s);
            py = (int) (cameraPositionY + d * c);
            currentColor = buffered2DmapWithoutPlayer.getRGB(
                    (int) (px),
                    (int) (py)
            );
        } while (currentColor != Color.BLACK.getRGB());
        g.setColor(new Color(rayColor.getRed(), rayColor.getGreen(), rayColor.getBlue(), transparencyLevel));
        g.drawLine((int) cameraPositionX + tileMap.getx(), (int) cameraPositionY + tileMap.gety(), (int) (cameraPositionX + d * s) + tileMap.getx(), (int) (cameraPositionY + d * c) + tileMap.gety());
        WallType result = new WallType();
        result.dist = d;
        result.color = new Color(currentColor);
        return result;
    }



    @Override
    public void keyTyped(KeyEvent key) {
    }

    @Override
    public void keyPressed(KeyEvent key) {
        //activateSecond
        if (key.getKeyChar() == '\\') {
            activateSecond = !activateSecond;
        }
        //make 3D walls transparent
        if (key.getKeyChar() == '`') {
            transparentWalls = !transparentWalls;
        }
        //switch on rotation with mouse
        if (key.getKeyChar() == '\'') {
            player.rotateWithMouse = !player.rotateWithMouse;
        }
        //left
        if ((key.getKeyCode() == 37 || key.getKeyCode() == 65)) {
            player.left = true;
        }//right
        else if ((key.getKeyCode() == 39 || key.getKeyCode() == 68)) {
            player.right = true;
        }
        //straight
        if ((key.getKeyCode() == 38 || key.getKeyCode() == 87)) {
            player.up = true;

        }//down
        else if ((key.getKeyCode() == 40 || key.getKeyCode() == 83)) {
            player.down = true;
        }
        //z
        if ((key.getKeyCode() == 90 || key.getKeyCode() == 91)) {
            player.rotateLeft = true;
        }//x
        else if ((key.getKeyCode() == 88 || key.getKeyCode() == 93)) {
            player.rotateRight = true;
        }
        //teleport up
        if (key.getKeyChar() == '+') {
            player.teleport(TileMap.upTeleport);
        }
        //teleport down
        if (key.getKeyChar() == '-') {
            player.teleport(TileMap.downTeleport);

        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent key) {
        //left
        if ((key.getKeyCode() == 37 || key.getKeyCode() == 65)) {
            player.left = false;
        }//right
        else if ((key.getKeyCode() == 39 || key.getKeyCode() == 68)) {
            player.right = false;
        }
        //straight
        if ((key.getKeyCode() == 38 || key.getKeyCode() == 87)) {
            player.up = false;

        }//down
        else if ((key.getKeyCode() == 40 || key.getKeyCode() == 83)) {
            player.down = false;
        }
        //z
        if ((key.getKeyCode() == 90 || key.getKeyCode() == 91)) {
            player.rotateLeft = false;
        }//x
        else if ((key.getKeyCode() == 88 || key.getKeyCode() == 93)) {
            player.rotateRight = false;
        }
    }

}
