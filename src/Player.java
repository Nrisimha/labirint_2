
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;


public class Player {

    private BufferedImage clear__offscreen2D;
    private Graphics2D off3Dgc, copyBUFF2Dg;
    private BufferedImage off3Dscreen;
    private Color playerPointColor = Color.RED;
    private int playerPointRadius = 3;

    public double cameraAngle = 0;
    private double fieldOfViewInRadians = Math.toRadians(60);

    private int projectionPlaneWidth = 300;
    private int projectionPlaneHeight = 300;
    private double distToProjectionPlane; // distance between camera and projection plane

    private double x, y, dx, dy;
    public boolean right, left, up, down, rotateLeft, rotateRight;
    private double moveSpeed, rotateSpeed;
    private TileMap tileMap;
    private View3D map3d;
    private class WallType{
        public double dist;
        public Color color;
        public WallType(){
            dist=0;
            color=Color.BLACK;
        }
    }
    public Player(TileMap tm, View3D map3d) {
        this.tileMap = tm;
        this.map3d = map3d;
        projectionPlaneWidth=map3d.getWidth();
        projectionPlaneHeight=map3d.getHeight();

        clear__offscreen2D = new BufferedImage(
                tileMap.getWidth() * tileMap.getTileSize(), tileMap.getHeight() * tileMap.getTileSize(),
                BufferedImage.TYPE_INT_ARGB);
        copyBUFF2Dg = (Graphics2D) clear__offscreen2D.getGraphics();
        off3Dscreen = new BufferedImage(map3d.getWidth(), map3d.getHeight(), BufferedImage.TYPE_INT_ARGB);
        off3Dgc = (Graphics2D) off3Dscreen.getGraphics();
        right=false;
        left=false;
        up=false;
        down=false;
        rotateRight=false;
        rotateLeft=false;
        moveSpeed=3.0;
        rotateSpeed=0.1;
        distToProjectionPlane = (projectionPlaneWidth / 2) / Math.tan(fieldOfViewInRadians / 2);
    }

    public void setx(int i) {
        x = i;
    }

    public void sety(int i) {
        y = i;
    }

    public double getx() {
        return x;
    }

    public double gety() {
        return y;
    }

///////////////////////////////////////////////////////////////
    public void update() {
        //determine next position
        if(right && !left){
            map3d.moveCamera(moveSpeed, Math.toRadians(-90));
            if(up){
                map3d.moveCamera(moveSpeed, 0);
            }
            else if(down){
                map3d.moveCamera(-moveSpeed, 0);
            }
            if(rotateRight){
                cameraAngle -= rotateSpeed;
            }
            else if(rotateLeft){
                cameraAngle += rotateSpeed;
            }
        }
        else if(left && !right){
            map3d.moveCamera(moveSpeed, Math.toRadians(90));
            if(up){
                map3d.moveCamera(moveSpeed, 0);
            }
            else if(down){
                map3d.moveCamera(-moveSpeed, 0);
            }
            if(rotateRight){
                cameraAngle -= rotateSpeed;
            }
            else if(rotateLeft){
                cameraAngle += rotateSpeed;
            }
        }  
        else if(up && !down){
            map3d.moveCamera(moveSpeed, 0);
            if(right){
                map3d.moveCamera(moveSpeed, Math.toRadians(-90));
            }
            else if(left){
                map3d.moveCamera(moveSpeed, Math.toRadians(90));
            }
            if(rotateRight){
                cameraAngle -= rotateSpeed;
            }
            else if(rotateLeft){
                cameraAngle += rotateSpeed;
            }
        }
         else if(down && !up){
            map3d.moveCamera(-moveSpeed, 0);
            if(right){
                map3d.moveCamera(moveSpeed, Math.toRadians(-90));
            }
            else if(left){
                map3d.moveCamera(moveSpeed, Math.toRadians(90));
            }
            if(rotateRight){
                cameraAngle -= rotateSpeed;
            }
            else if(rotateLeft){
                cameraAngle += rotateSpeed;
            }
        }   
         else if(rotateRight && !rotateLeft){
            cameraAngle -= rotateSpeed;
            if(up){
                map3d.moveCamera(moveSpeed, 0);
            }
            else if(down){
                map3d.moveCamera(-moveSpeed, 0);
            }
            if(right){
                map3d.moveCamera(moveSpeed, Math.toRadians(-90));
            }
            else if(left){
                map3d.moveCamera(moveSpeed, Math.toRadians(90));
            }
        }  
        else if(rotateLeft && !rotateRight){
            cameraAngle += rotateSpeed;
            if(up){
                map3d.moveCamera(moveSpeed, 0);
            }
            else if(down){
                map3d.moveCamera(-moveSpeed, 0);
            }
            if(right){
                map3d.moveCamera(moveSpeed, Math.toRadians(-90));
            }
            else if(left){
                map3d.moveCamera(moveSpeed, Math.toRadians(90));
            }
        } 
        // check collisions
        int currCol = tileMap.getColTile((int) x);
        int currRow = tileMap.getRowTile((int) y);

        double tox = x + dx;
        double toy = y + dy;

        double tempx = x;
        double tempy = y;

        //move the map
        tileMap.setx((int) ((tileMap.getWidth() * tileMap.getTileSize()) / 2 - x));
        tileMap.sety((int) ((tileMap.getHeight() * tileMap.getTileSize()) / 2 - y));
    }
    public void teleport(int teleportType){
        int col=tileMap.getColTile((int)x);
        int row=tileMap.getRowTile((int)y);
        if(tileMap.getTile(row,col)==tileMap.upTeleport && teleportType==tileMap.upTeleport){
            if(tileMap.loadNextLevel()==false){
                JOptionPane.showMessageDialog(null, "No  next level here!");
            }
        }
        else if(tileMap.getTile(row,col)==tileMap.downTeleport && teleportType==tileMap.upTeleport){
            JOptionPane.showMessageDialog(null, "No up portal here!");
        }
        else if(tileMap.getTile(row,col)==tileMap.downTeleport && teleportType==tileMap.downTeleport){
            if(tileMap.loadPreviousLevel()==false){
                JOptionPane.showMessageDialog(null, "No  previous level here!");
            }
        }
        else if(tileMap.getTile(row,col)==tileMap.upTeleport && teleportType==tileMap.downTeleport){
            JOptionPane.showMessageDialog(null, "No down portal here!");
        }   
        else{
            JOptionPane.showMessageDialog(null, "No portal here!");
        }
    }
    public void draw(Graphics2D BUFF2Dg, BufferedImage buffered2Dmap) {
        int tx = tileMap.getx();
        int ty = tileMap.gety();

        copyBUFF2Dg.drawImage(buffered2Dmap, 0, 0, null);
        BUFF2Dg.setColor(playerPointColor);
        BUFF2Dg.fillOval(
                (int) (tx + x - playerPointRadius),
                (int) (ty + y - playerPointRadius),
                playerPointRadius * 2,
                playerPointRadius * 2
        );
        // draw walls
        map3d.drawFloor3D(off3Dgc);
        for (int x1 = -(projectionPlaneWidth / 2), x2 = (projectionPlaneWidth / 2); x1 <= 0 && x2 >= 0; x1++, x2--) {
            double left_a = Math.atan(x1 / distToProjectionPlane);
            WallType LeftSideCameraWallDistance = castRay(tx + this.x, ty + y, cameraAngle - left_a, BUFF2Dg, Color.BLUE, clear__offscreen2D);
            double left_z = LeftSideCameraWallDistance.dist * Math.cos(left_a);

            double right_a = Math.atan(x2 / distToProjectionPlane);
            WallType RightSideCameraWallDistance = castRay(tx + this.x, ty + y, cameraAngle - right_a, BUFF2Dg, Color.BLUE, clear__offscreen2D);
            double right_z = RightSideCameraWallDistance.dist * Math.cos(right_a);

            map3d.drawWall3D(off3Dgc, left_z,LeftSideCameraWallDistance.color, right_z,RightSideCameraWallDistance.color, x1+map3d.getWidth()/2, x2+map3d.getWidth()/2);

        }
        // draw camera direction
        castRay(tx + x, ty + y, cameraAngle, BUFF2Dg, Color.GREEN, clear__offscreen2D);
        //BUFF2Dg.drawImage(off3Dscreen, 0, 0, null);

        //BUFF2Dg.setColor(Color.RED);
        //BUFF2Dg.setFont(new Font("Serif", Font.BOLD, 24));
        //BUFF2Dg.drawString("lTop.x: "+(int)(tx+x)+" lTop.y: "+(int)(ty+y), 20, 20);
        map3d.render(off3Dscreen);
    }
    
    private WallType castRay(
            double cameraPositionX,
            double cameraPositionY,
            double cameraDirectionAngle,
            Graphics g,
            Color rayColor,
            BufferedImage clear__buffered2Dmap
    ) {
        double s = Math.sin(cameraDirectionAngle);
        double c = Math.cos(cameraDirectionAngle);
        double d = playerPointRadius + 1;//out of radius of player point
        int currentColor = 0;
        int px = 0, py = 0;
        do {
            d += 0.1;
            px = (int) (cameraPositionX + d * s);
            py = (int) (cameraPositionY + d * c);
            if ((px) >= clear__buffered2Dmap.getWidth() || (py) >= clear__buffered2Dmap.getHeight() || px < 0 || py < 0) {
                break;
            }
            currentColor = clear__buffered2Dmap.getRGB(
                    (int) (px),
                    (int) (py)
            );
            /*
            if(currentColor!=Color.BLACK.getRGB() && currentColor!=Color.WHITE.getRGB()){
                try {
                    ImageIO.write(clear__buffered2Dmap, "PNG", new File("filename1.png"));
                } catch (IOException ex) {
                    Logger.getLogger(MiniMap.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
            }
             */
            if (currentColor == Color.BLUE.getRGB() && rayColor == Color.GREEN) {
                //down portal founded
               // map3d.drawFloorPortal((int) d, (int) px, (int) py, (int) cameraPositionX, (int) cameraPositionY, cameraAngle, off3Dgc);
            }

        } while (currentColor == Color.WHITE.getRGB());//(currentColor == Color.WHITE.getRGB() || currentColor == Color.BLUE.getRGB() || currentColor == Color.GREEN.getRGB());//calcuate distance to wall on the picture
        g.setColor(new Color(rayColor.getRed(),rayColor.getGreen(),rayColor.getBlue(),10));
        g.drawLine((int) cameraPositionX, (int) cameraPositionY, (int) (cameraPositionX + d * s), (int) (cameraPositionY + d * c));
        WallType result=new WallType();
        result.dist=d;
        result.color=new Color(currentColor);
        return result;
    }

}
