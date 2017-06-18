
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Player {

    private BufferedImage clear__offscreen2D;
    private Graphics2D off3Dgc, copyBUFF2Dg;
    private BufferedImage off3Dscreen;
    private Color playerPointColor = Color.RED;
    private int playerPointRadius = 3;

    public double cameraAngle = 0;
    private double fieldOfViewInRadians = Math.toRadians(120);

    private int projectionPlaneWidth = 300;
    private int projectionPlaneHeight = 300;
    private double distToProjectionPlane; // distance between camera and projection plane

    private double x, y, dx, dy;
    public boolean right, left, up, down, rotateLeft, rotateRight;
    public boolean activateSecond = true;
    public boolean draw3DWalls = true;
    private double moveSpeed, rotateSpeed;
    private TileMap tileMap;
    private View3D map3d;
    private MiniMap miniMap;

    private class WallType {

        public double dist;
        public Color color;

        public WallType() {
            dist = 0;
            color = Color.BLACK;
        }
    }

    public Player(TileMap tm, View3D map3d, MiniMap miniMap) {
        this.tileMap = tm;
        this.map3d = map3d;
        this.miniMap = miniMap;
        projectionPlaneWidth = map3d.getWidth();
        projectionPlaneHeight = map3d.getHeight();

        clear__offscreen2D = new BufferedImage(
                tileMap.getWidth() * tileMap.getTileSize(), tileMap.getHeight() * tileMap.getTileSize(),
                BufferedImage.TYPE_INT_ARGB);
        copyBUFF2Dg = (Graphics2D) clear__offscreen2D.getGraphics();
        off3Dscreen = new BufferedImage(map3d.getWidth(), map3d.getHeight(), BufferedImage.TYPE_INT_ARGB);
        off3Dgc = (Graphics2D) off3Dscreen.getGraphics();
        right = false;
        left = false;
        up = false;
        down = false;
        rotateRight = false;
        rotateLeft = false;
        moveSpeed = 3.0;
        rotateSpeed = 0.1;
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
        if (right && !left) {
            map3d.moveCamera(moveSpeed, Math.toRadians(-90));
            if (up) {
                map3d.moveCamera(moveSpeed, 0);
            } else if (down) {
                map3d.moveCamera(-moveSpeed, 0);
            }
            if (rotateRight) {
                cameraAngle -= rotateSpeed;
            } else if (rotateLeft) {
                cameraAngle += rotateSpeed;
            }
        } else if (left && !right) {
            map3d.moveCamera(moveSpeed, Math.toRadians(90));
            if (up) {
                map3d.moveCamera(moveSpeed, 0);
            } else if (down) {
                map3d.moveCamera(-moveSpeed, 0);
            }
            if (rotateRight) {
                cameraAngle -= rotateSpeed;
            } else if (rotateLeft) {
                cameraAngle += rotateSpeed;
            }
        } else if (up && !down) {
            map3d.moveCamera(moveSpeed, 0);
            if (right) {
                map3d.moveCamera(moveSpeed, Math.toRadians(-90));
            } else if (left) {
                map3d.moveCamera(moveSpeed, Math.toRadians(90));
            }
            if (rotateRight) {
                cameraAngle -= rotateSpeed;
            } else if (rotateLeft) {
                cameraAngle += rotateSpeed;
            }
        } else if (down && !up) {
            map3d.moveCamera(-moveSpeed, 0);
            if (right) {
                map3d.moveCamera(moveSpeed, Math.toRadians(-90));
            } else if (left) {
                map3d.moveCamera(moveSpeed, Math.toRadians(90));
            }
            if (rotateRight) {
                cameraAngle -= rotateSpeed;
            } else if (rotateLeft) {
                cameraAngle += rotateSpeed;
            }
        } else if (rotateRight && !rotateLeft) {
            cameraAngle -= rotateSpeed;
            if (up) {
                map3d.moveCamera(moveSpeed, 0);
            } else if (down) {
                map3d.moveCamera(-moveSpeed, 0);
            }
            if (right) {
                map3d.moveCamera(moveSpeed, Math.toRadians(-90));
            } else if (left) {
                map3d.moveCamera(moveSpeed, Math.toRadians(90));
            }
        } else if (rotateLeft && !rotateRight) {
            cameraAngle += rotateSpeed;
            if (up) {
                map3d.moveCamera(moveSpeed, 0);
            } else if (down) {
                map3d.moveCamera(-moveSpeed, 0);
            }
            if (right) {
                map3d.moveCamera(moveSpeed, Math.toRadians(-90));
            } else if (left) {
                map3d.moveCamera(moveSpeed, Math.toRadians(90));
            }
        }

        //move the map
        tileMap.setx((int) (miniMap.getWidth() / miniMap.scale / 2 - x));
        tileMap.sety((int) (miniMap.getHeight() / miniMap.scale / 2 - y));
    }

    public void teleport(int teleportType) {
        int col = tileMap.getColTile((int) x);
        int row = tileMap.getRowTile((int) y);
        if (tileMap.getTile(row, col) == tileMap.upTeleport && teleportType == tileMap.upTeleport) {
            if (tileMap.loadNextLevel() == false) {
                JOptionPane.showMessageDialog(null, "No  next level here!");
            }
        } else if (tileMap.getTile(row, col) == tileMap.downTeleport && teleportType == tileMap.upTeleport) {
            JOptionPane.showMessageDialog(null, "No up portal here!");
        } else if (tileMap.getTile(row, col) == tileMap.downTeleport && teleportType == tileMap.downTeleport) {
            if (tileMap.loadPreviousLevel() == false) {
                JOptionPane.showMessageDialog(null, "No  previous level here!");
            }
        } else if (tileMap.getTile(row, col) == tileMap.upTeleport && teleportType == tileMap.downTeleport) {
            JOptionPane.showMessageDialog(null, "No down portal here!");
        } else {
            JOptionPane.showMessageDialog(null, "No portal here!");
        }
    }

    public void draw(Graphics2D BUFF2Dg, BufferedImage buffered2Dmap) {
        int tx = tileMap.getx();
        int ty = tileMap.gety();

        copyBUFF2Dg.drawImage(buffered2Dmap, -(int) (miniMap.getWidth() / miniMap.scale / 2 - x), -(int) (miniMap.getHeight() / miniMap.scale / 2 - y), null);

        BUFF2Dg.setColor(playerPointColor);
        BUFF2Dg.fillOval(
                (int) (tx + x - playerPointRadius), (int) (ty + y - playerPointRadius),
                playerPointRadius * 2, playerPointRadius * 2
        );
        // draw walls
        if (activateSecond) {
            BufferedImage tmp = new BufferedImage(buffered2Dmap.getWidth(), buffered2Dmap.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics2D goi = (Graphics2D) tmp.getGraphics();
            goi.translate(tmp.getWidth() >> 1, 0);
            goi.rotate(cameraAngle + Math.toRadians(0)); //вместо вида вращается миникарта
            goi.translate(-x, -y);
            goi.drawImage(buffered2Dmap, -(int) (miniMap.getWidth() / miniMap.scale / 2 - x - 0), -(int) (miniMap.getHeight() / miniMap.scale / 2 - y - 0), null);

            for (int y = (int) 20; y < projectionPlaneHeight / 2; y++) {
                double z = distToProjectionPlane * 20 / y;
                double frustrumWidthAtZ = (projectionPlaneWidth / 2) * 20 / y;
                int x1 = (int) (tmp.getWidth() / 2 - frustrumWidthAtZ);
                int x2 = (int) (tmp.getWidth() / 2 + frustrumWidthAtZ);
                int dy = projectionPlaneHeight / 2 + 1 * y;
                off3Dgc.drawImage(tmp, 0, dy, projectionPlaneWidth, dy + 1, x2, (int) z, x1, (int) z + 1, null);
            }
            for (int y = (int) 20; y < projectionPlaneHeight / 2; y++) {
                double z = distToProjectionPlane * 20 / y;
                double frustrumWidthAtZ = (projectionPlaneWidth / 2) * 20 / y;
                int x1 = (int) (tmp.getWidth() / 2 - frustrumWidthAtZ);
                int x2 = (int) (tmp.getWidth() / 2 + frustrumWidthAtZ);
                int dy = projectionPlaneHeight / 2 - 1 * y;
                off3Dgc.drawImage(tmp, 0, dy, projectionPlaneWidth, dy + 1, x2, (int) z, x1, (int) z + 1, null);
            }
        } else {
            map3d.drawFloor3D(off3Dgc);
        }
        for (int x1 = -(projectionPlaneWidth / 2), x2 = (projectionPlaneWidth / 2); x1 <= 0 && x2 >= 0; x1++, x2--) {
            double left_a = Math.atan(x1 / distToProjectionPlane);
            WallType LeftSideCameraWallDistance = castRay(this.x, y, cameraAngle - left_a, BUFF2Dg, Color.BLUE, clear__offscreen2D);
            double left_z = LeftSideCameraWallDistance.dist * Math.cos(left_a);

            double right_a = Math.atan(x2 / distToProjectionPlane);
            WallType RightSideCameraWallDistance = castRay(this.x, y, cameraAngle - right_a, BUFF2Dg, Color.BLUE, clear__offscreen2D);
            double right_z = RightSideCameraWallDistance.dist * Math.cos(right_a);

            map3d.drawWall3D(off3Dgc, left_z, LeftSideCameraWallDistance.color, right_z, RightSideCameraWallDistance.color, x1 + map3d.getWidth() / 2, x2 + map3d.getWidth() / 2);

        }
        // draw camera direction
        castRay(x, y, cameraAngle, BUFF2Dg, Color.GREEN, clear__offscreen2D);
//        if(draw3DWalls){
//            off3Dgc.setColor(Color.WHITE);
//            off3Dgc.fillRect(0, 0, off3Dscreen.getWidth(), off3Dscreen.getHeight());
//        }
        
        
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
            currentColor = clear__buffered2Dmap.getRGB(
                    (int) (px),
                    (int) (py)
            );
        } while (currentColor != Color.BLACK.getRGB());
        g.setColor(new Color(rayColor.getRed(), rayColor.getGreen(), rayColor.getBlue(), 10));
        g.drawLine((int) cameraPositionX + tileMap.getx(), (int) cameraPositionY + tileMap.gety(), (int) (cameraPositionX + d * s) + tileMap.getx(), (int) (cameraPositionY + d * c) + tileMap.gety());
        WallType result = new WallType();
        result.dist = d;
        result.color = new Color(currentColor);
        return result;
    }

}
