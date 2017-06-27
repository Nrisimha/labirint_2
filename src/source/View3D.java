package source;


import java.awt.*;
import java.awt.image.BufferedImage;
import static java.lang.Math.max;
import javax.swing.JPanel;
import static java.lang.Math.min;
import javax.swing.JOptionPane;

/**
 * Klasa View3D odpowiada za rysowanie widoku 3D na elemencie JPanel
 *
 * @author Maksym Titov
 */
class View3D extends JPanel {

    //obraz buffered3Dview przechowuje wszystko, co powinno być narysowane na View3D
    //obraz shadeCeilFloor przechowuje cień na podłodze i suficie, która rozrasta się przy oddaleniu od grawca
    public BufferedImage buffered3Dview, shadeCeilFloor;
    //grafika, którą rysuje na buffered3Dview
    public Graphics2D gcOfBuffered3dView;
    private Point.Double cameraPosition;
    private Player player;
    private TileMap tileMap;
    //"wysokość" ścian 
    private final int scaleOfWallHeight = 20;

    public View3D(int width, int height) {
        super();
        setPreferredSize(new Dimension(width, height));
        this.setSize(width, height);
        buffered3Dview = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        gcOfBuffered3dView = buffered3Dview.createGraphics();

        cameraPosition = new Point.Double(0, 0);

        //tworzenie cieni
        shadeCeilFloor = new BufferedImage(1, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics sg = shadeCeilFloor.getGraphics();
        sg.setColor(Color.BLACK);
        //nieprzepuszczalna cień, prostokąt wyśrodkowany po osi y, wysokość 10
        sg.fillRect(0, shadeCeilFloor.getHeight() / 2 - 5, 1, 5);
        //zaczynając od krańca nieprzepuszczalnej cieni rysujemy 90 linij coraz jaśnieszej cieni
        for (int y = 5; y <= 90; y++) {
            sg.setColor(new Color(0, 0, 0, max(255 - (y - 5) * 3, 0)));
            sg.drawLine(0, shadeCeilFloor.getHeight() / 2 - y, 0, shadeCeilFloor.getHeight() / 2 - y);
            sg.drawLine(0, shadeCeilFloor.getHeight() / 2 + y, 0, shadeCeilFloor.getHeight() / 2 + y);
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
        cameraPosition = new Point.Double(player.getx(), player.gety());
    }

    /**
     * funkcja drawWall3D rysuje ściany, zaczynając od lewego i prawego krańców
     * widoku i kończąc w środku. Rysuje pionowymi liniami zmieniając kolor
     * linii na bardziej ciemny w zależności od odległości do ściany. Jedno
     * Wywołanie drawWall3D rysuje 2 linii: 1 po lewej stronie i 1 po prawej
     *
     * @param leftZ - odległość do ściany z lewej strony
     * @param left_color - kolor obiektu (ściany) po lewej stronie widoku
     * @param rightZ - odległość do ściany z prawej strony
     * @param right_color - kolor obiektu (ściany) po prawej stronie widoku
     * @param leftX
     * @param rightX
     */
    public void drawWall3D(double leftZ, Color left_color, double rightZ, Color right_color, int leftX, int rightX) {
        double p = leftZ / this.getHeight();
        p = p > 1 ? 1 : p;
        p = p < 0 ? 0 : p;
        int left_ic = (int) (p * 255);
        if (left_color.getRGB() == Color.BLACK.getRGB()) {
            gcOfBuffered3dView.setColor(new Color(max(130 - left_ic, 0), max(130 - left_ic, 0), max(130 - left_ic, 0)));
        } else if (left_color.getRGB() == Color.BLUE.getRGB()) {
            gcOfBuffered3dView.setColor(new Color(0, 0, (int) min((p * 182), 255), (int) min((p * 155), 255)));
        } else if (left_color.getRGB() == Color.RED.getRGB()) {
            gcOfBuffered3dView.setColor(new Color((int) max(min(leftZ + p * leftZ, 255), 0), 100, 100));
        } else if (left_color.getRGB() == Color.YELLOW.getRGB()) {
            gcOfBuffered3dView.setColor(new Color(255, 255, (int) min((leftZ), 255), (int) min((p * 155), 255)));
        }
        int LeftwallHeight = (int) (player.distToProjectionPlane * scaleOfWallHeight / leftZ);
        LeftwallHeight = LeftwallHeight > this.getHeight() / 2 ? this.getHeight() / 2 : LeftwallHeight;
        gcOfBuffered3dView.drawLine(leftX, this.getHeight() / 2 - LeftwallHeight, leftX, this.getHeight() / 2 + LeftwallHeight);

        p = rightZ / this.getHeight();
        p = p > 1 ? 1 : p;
        p = p < 0 ? 0 : p;
        int right_ic = (int) (p * 255);
        if (right_color.getRGB() == Color.BLACK.getRGB()) {
            gcOfBuffered3dView.setColor(new Color(max(130 - right_ic, 0), max(130 - right_ic, 0), max(130 - right_ic, 0)));
        } else if (right_color.getRGB() == Color.BLUE.getRGB()) {
            gcOfBuffered3dView.setColor(new Color(0, 0, (int) min((p * 182), 255), (int) min((p * 155), 255)));
        } else if (right_color.getRGB() == Color.RED.getRGB()) {
            gcOfBuffered3dView.setColor(new Color((int) max(min(rightZ - p * rightZ, 255), 0), 100, 100));
        } else if (right_color.getRGB() == Color.YELLOW.getRGB()) {
            gcOfBuffered3dView.setColor(new Color(255, 255, (int) min((rightZ), 255), (int) min((p * 155), 255)));
        }
        int RightwallHeight = (int) (player.distToProjectionPlane * scaleOfWallHeight / rightZ);
        RightwallHeight = RightwallHeight > this.getHeight() / 2 ? this.getHeight() / 2 : RightwallHeight;

        gcOfBuffered3dView.drawLine(rightX, this.getHeight() / 2 - RightwallHeight, rightX, this.getHeight() / 2 + RightwallHeight);
    }

    /**
     * Funkcja drawSimpleFloor3D() rysuje zamiast podłogi i sufitu zwykły
     * gradient niebieskiego i czerwonego koloru.
     */
    public void drawSimpleFloor3D() {
        for (int i = 0; i <= this.getHeight() / 2; i++) {
            double p = i;
            p = p > 255 ? 255 : p;
            int ic = (int) (p);
            gcOfBuffered3dView.setColor(new Color(100, 40, ic));
            gcOfBuffered3dView.drawLine(0, i, this.getWidth(), i);
        }
        for (int i = this.getHeight(); i > this.getHeight() / 2; i--) {
            double p = i;
            p = p > 255 ? 255 : p;
            int ic = (int) (p);
            gcOfBuffered3dView.setColor(new Color(100, 40, ic));
            gcOfBuffered3dView.drawLine(0, i, this.getWidth(), i);
        }
    }

    /**
     * Funkcja render() wymuszuje rysowanie obiektu View3D.
     *
     */
    public void render() {
        paintComponent(getGraphics());
    }

    /**
     * Przy rysowaniu View3D narzucam rysowanie zbuforawanego obrazu
     * buffered3Dview
     *
     * @param g - nie używany
     */
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(buffered3Dview, 0, 0, null);
    }

    /**
     * Funkcja moveCamera odpowiada za ustalenie nowej lokalizacji grawca Player
     * na minimapie miniMap
     *
     * @param d - odległość (krok), na którą przesunąc grawca
     * @param a - kąt, o który obrócić grawca
     */
    public void moveCamera(double d, double a) {
        double angle = player.cameraAngle + a;
        double s = Math.sin(angle);
        double c = Math.cos(angle);
        //jeśli pozycja, na którą chce wstać grawiec, nie jest twardą ścianą labiryntu (hardWall)
        //i nie jest zwykłą ścią=aną labiryntu (wall), to
        //przesuń grawca na nowe miejsce
        if (tileMap.getTile(
                (int) ((cameraPosition.y + d * c) / tileMap.getTileSize()),//x and y swiped?!
                (int) ((cameraPosition.x + d * s) / tileMap.getTileSize()))
                != TileMap.hardWall
                && tileMap.getTile(
                        (int) ((cameraPosition.y + d * c) / tileMap.getTileSize()),//x and y swiped?!
                        (int) ((cameraPosition.x + d * s) / tileMap.getTileSize()))
                != TileMap.wall) {
            cameraPosition.x += d * s;
            cameraPosition.y += d * c;
        }
        player.setx((int) cameraPosition.x);
        player.sety((int) cameraPosition.y);
    }
}
