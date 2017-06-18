
import java.awt.*;
import javax.swing.JOptionPane;

public class Player {
    public Color playerPointColor = Color.RED;
    public int playerPointRadius = 3;

    public double cameraAngle = 0;
    private double fieldOfViewInRadians = Math.toRadians(120);
    public double distToProjectionPlane; // distance between camera and projection plane

    private double x, y;
    public boolean right, left, up, down, rotateLeft, rotateRight;
    private double moveSpeed, rotateSpeed;
    
    private TileMap tileMap;
    private View3D map3d;
    private MiniMap miniMap;
    
    public Player(TileMap tm, View3D map3d, MiniMap miniMap) {
        this.tileMap = tm;
        this.map3d = map3d;
        this.miniMap = miniMap;
        right = false;
        left = false;
        up = false;
        down = false;
        rotateRight = false;
        rotateLeft = false;
        this.moveSpeed = 8.0;
        this.rotateSpeed = 0.2;
        distToProjectionPlane = (map3d.getWidth() / 2) / Math.tan(fieldOfViewInRadians / 2);
    }
    public Player(TileMap tm, View3D map3d, MiniMap miniMap, double moveSpeed, double rotateSpeed) {
        this.tileMap = tm;
        this.map3d = map3d;
        this.miniMap = miniMap;
        right = false;
        left = false;
        up = false;
        down = false;
        rotateRight = false;
        rotateLeft = false;
        this.moveSpeed = moveSpeed;
        this.rotateSpeed = rotateSpeed;
        distToProjectionPlane = (map3d.getWidth() / 2) / Math.tan(fieldOfViewInRadians / 2);
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
}
