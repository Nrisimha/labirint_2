
import java.awt.*;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

public class Player {

    public Color playerPointColor = Color.RED;
    public int playerPointRadius = 3;

    public double cameraAngle = 0;
    private double fieldOfViewInRadians = Math.toRadians(120);
    public double distToProjectionPlane; // distance between camera and projection plane
    public long startTime = 0;
    private double x, y;
    public boolean right, left, up, down, rotateLeft, rotateRight;
    public boolean rotateWithMouse = false;
    private double moveSpeed, rotateSpeed;

    private TileMap tileMap;
    private View3D view3d;
    private MiniMap miniMap;

    private int mouseXLastPosition;

    public Player(TileMap tm, View3D view3d, MiniMap miniMap) {
        this.tileMap = tm;
        this.view3d = view3d;
        this.miniMap = miniMap;
        right = false;
        left = false;
        up = false;
        down = false;
        rotateRight = false;
        rotateLeft = false;
        this.moveSpeed = 8.0;
        this.rotateSpeed = 0.2;
        distToProjectionPlane = (view3d.getWidth() / 2) / Math.tan(fieldOfViewInRadians / 2);
        mouseXLastPosition = 0;// (view3d.getLocationOnScreen().x+view3d.getWidth()/2 - MouseInfo.getPointerInfo().getLocation().x);
    }

    public Player(TileMap tm, View3D view3d, MiniMap miniMap, double moveSpeed, double rotateSpeed) {
        this.tileMap = tm;
        this.view3d = view3d;
        this.miniMap = miniMap;
        right = false;
        left = false;
        up = false;
        down = false;
        rotateRight = false;
        rotateLeft = false;
        this.moveSpeed = moveSpeed;
        this.rotateSpeed = rotateSpeed;
        distToProjectionPlane = (view3d.getWidth() / 2) / Math.tan(fieldOfViewInRadians / 2);
        mouseXLastPosition = 0;// (view3d.getLocationOnScreen().x+view3d.getWidth()/2 - MouseInfo.getPointerInfo().getLocation().x);
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
        int mouseXPlace = (view3d.getLocationOnScreen().x + view3d.getWidth() / 2 - MouseInfo.getPointerInfo().getLocation().x);
        if (right && !left) {
            view3d.moveCamera(moveSpeed, Math.toRadians(-90));
            if (up) {
                view3d.moveCamera(moveSpeed, 0);
            } else if (down) {
                view3d.moveCamera(-moveSpeed, 0);
            }
            if (rotateRight || ((mouseXLastPosition - mouseXPlace) > 0 && mouseXPlace < 0 && rotateWithMouse)) {
                cameraAngle -= rotateSpeed;
            } else if (rotateLeft || ((mouseXLastPosition - mouseXPlace) < 0 && mouseXPlace > 0 && rotateWithMouse)) {
                cameraAngle += rotateSpeed;
            }
        } else if (left && !right) {
            view3d.moveCamera(moveSpeed, Math.toRadians(90));
            if (up) {
                view3d.moveCamera(moveSpeed, 0);
            } else if (down) {
                view3d.moveCamera(-moveSpeed, 0);
            }
            if (rotateRight || ((mouseXLastPosition - mouseXPlace) > 0 && mouseXPlace < 0 && rotateWithMouse)) {
                cameraAngle -= rotateSpeed;
            } else if (rotateLeft || ((mouseXLastPosition - mouseXPlace) < 0 && mouseXPlace > 0 && rotateWithMouse)) {
                cameraAngle += rotateSpeed;
            }
        } else if (up && !down) {
            view3d.moveCamera(moveSpeed, 0);
            if (right) {
                view3d.moveCamera(moveSpeed, Math.toRadians(-90));
            } else if (left) {
                view3d.moveCamera(moveSpeed, Math.toRadians(90));
            }
            if (rotateRight || ((mouseXLastPosition - mouseXPlace) > 0 && mouseXPlace < 0 && rotateWithMouse)) {
                cameraAngle -= rotateSpeed;
            } else if (rotateLeft || ((mouseXLastPosition - mouseXPlace) < 0 && mouseXPlace > 0 && rotateWithMouse)) {
                cameraAngle += rotateSpeed;
            }
        } else if (down && !up) {
            view3d.moveCamera(-moveSpeed, 0);
            if (right) {
                view3d.moveCamera(moveSpeed, Math.toRadians(-90));
            } else if (left) {
                view3d.moveCamera(moveSpeed, Math.toRadians(90));
            }
            if (rotateRight || ((mouseXLastPosition - mouseXPlace) > 0 && mouseXPlace < 0 && rotateWithMouse)) {
                cameraAngle -= rotateSpeed;
            } else if (rotateLeft || ((mouseXLastPosition - mouseXPlace) < 0 && mouseXPlace > 0 && rotateWithMouse)) {
                cameraAngle += rotateSpeed;
            }
        } else if ((rotateRight || ((mouseXLastPosition - mouseXPlace) > 0 && mouseXPlace < 0 && rotateWithMouse)) && !(rotateLeft || ((mouseXLastPosition - mouseXPlace) < 0 && mouseXPlace > 0 && rotateWithMouse))) {
            cameraAngle -= rotateSpeed;
            if (up) {
                view3d.moveCamera(moveSpeed, 0);
            } else if (down) {
                view3d.moveCamera(-moveSpeed, 0);
            }
            if (right) {
                view3d.moveCamera(moveSpeed, Math.toRadians(-90));
            } else if (left) {
                view3d.moveCamera(moveSpeed, Math.toRadians(90));
            }
        } else if ((rotateLeft || ((mouseXLastPosition - mouseXPlace) < 0 && mouseXPlace > 0 && rotateWithMouse)) && !(rotateRight || ((mouseXLastPosition - mouseXPlace) > 0 && mouseXPlace < 0 && rotateWithMouse))) {
            cameraAngle += rotateSpeed;
            if (up) {
                view3d.moveCamera(moveSpeed, 0);
            } else if (down) {
                view3d.moveCamera(-moveSpeed, 0);
            }
            if (right) {
                view3d.moveCamera(moveSpeed, Math.toRadians(-90));
            } else if (left) {
                view3d.moveCamera(moveSpeed, Math.toRadians(90));
            }
        }
        mouseXLastPosition = mouseXPlace;
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
        } else if (tileMap.getTile(row, col) == tileMap.exitTeleport && (teleportType == tileMap.exitTeleport || teleportType == tileMap.upTeleport)) {
            //finish game
            Object[] options = {"Yes, I do",
                "No, I don't have a life",
            "Just leave me alone!"};
            int n = JOptionPane.showOptionDialog(null,//parent container of JOptionPane
                    "Did you lose "+getElapsedTime(startTime, System.nanoTime()) + " of your life to walking in empty maze?",
                    "A Silly Question",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,//do not use a custom Icon
                    options,//the titles of buttons
                    options[0]);//default button title
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(null, "No portal here!");
        }
    }

    private static String getElapsedTime(final long start, final long end) {
        String str = "";
        DecimalFormat df = new DecimalFormat("#.##");
        double difference = (end - start) / 1000000000;
        int minHourDay = 0;
        if (difference > 60) {
            minHourDay++;
            difference /= 60;
            if (difference > 60){
                minHourDay++;
                difference /= 60;
                if (difference > 24){
                    minHourDay++;
                    difference /= 24;
                }
            }
        }
        switch(minHourDay){
            case 3:
                str += (int)(difference) + "d ";
                difference *= 24;
                difference %= 24;
            case 2:
                str += (int)(difference) + "h ";
                difference *= 60;
                difference %= 60;
            case 1:
                str += (int)(difference) + "m ";
                difference *= 60;
                difference %= 60;
            case 0:
                str += df.format(difference) + "s";
        }
        return str.equals("0s")?"<1s":str;
    }
}
