
import java.awt.*;
import java.awt.image.BufferedImage;
import static java.lang.Math.max;

import javax.swing.JPanel;
import static jdk.nashorn.internal.objects.NativeMath.min;

class View3D extends JPanel {

    private BufferedImage full__buffered3Dview;
    private Graphics2D  off3Dgc;

    private Point.Double cameraPosition ;
    private double fieldOfViewInRadians = Math.toRadians(60);

    private int projectionPlaneWidth = 300;
    private int projectionPlaneHeight = 300;
    private double distToProjectionPlane; // distance between camera and projection plane

    private Player player;
    private TileMap tileMap;

    public View3D(int width,int height ) {
        super();
        setPreferredSize(new Dimension(width, height));
        this.setSize(width,height);
        projectionPlaneWidth=width;
        projectionPlaneHeight=height;
//
//        buffered3Dview = new BufferedImage(
//                WIDTH, HEIGHT,
//                BufferedImage.TYPE_3BYTE_BGR);
//        BUFF3Dg = buffered3Dview.createGraphics();

        full__buffered3Dview = new BufferedImage(
                this.getWidth(), this.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);
        off3Dgc = full__buffered3Dview.createGraphics();

        distToProjectionPlane = (projectionPlaneWidth / 2) / Math.tan(fieldOfViewInRadians / 2);
        
        cameraPosition = new Point.Double(0,0);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
        cameraPosition = new Point.Double(player.getx(),player.gety());
    }

    public void drawWall3D(Graphics2D g, double left_z,Color left_color, double right_z,Color right_color, int x1, int x2) {
        //Graphics g = getGraphics();
        final int scaleOfWallHeight = 20;

        double p = left_z / this.getHeight();
        p = p > 1 ? 1 : p;
        p = p < 0 ? 0 : p;
        int left_ic = (int) (p * 255);
        if(left_color.getRGB()==Color.BLUE.getRGB())
            g.setColor(new Color(0, 0, (int)min((p * 182),255),(int)min((p * 155),255)));
        else if(left_color.getRGB()==Color.RED.getRGB())
            g.setColor(new Color((int)max(min(left_z+p*left_z,255),0), 100, 100));
        else if(left_color.getRGB()==Color.YELLOW.getRGB())
            g.setColor(new Color(255, 255, (int)min((left_z),255),(int)min((p * 155),255)));
            
        else//if(left_color.getRGB()==Color.BLACK.getRGB())
            g.setColor(new Color(left_ic, left_ic, left_ic));
        int LeftwallHeight = (int) (distToProjectionPlane * scaleOfWallHeight / left_z);
        LeftwallHeight = LeftwallHeight > projectionPlaneHeight / 2 ? projectionPlaneHeight / 2 : LeftwallHeight;
        
        g.drawLine(x1, projectionPlaneHeight / 2 - LeftwallHeight, x1, projectionPlaneHeight / 2 + LeftwallHeight);

        p = right_z / this.getHeight();
        p = p > 1 ? 1 : p;
        p = p < 0 ? 0 : p;
        int right_ic = (int) (p * 255);
        if(right_color.getRGB()==Color.BLUE.getRGB())
            g.setColor(new Color(0, 0, (int)min((p * 182),255),(int)min((p * 155),255)));
        else if(right_color.getRGB()==Color.RED.getRGB())
            g.setColor(new Color((int)max(min(right_z-p*right_z,255),0), 100, 100));
        else if(right_color.getRGB()==Color.YELLOW.getRGB())
            g.setColor(new Color(255, 255, (int)min((right_z),255),(int)min((p * 155),255)));
        else//if(right_color.getRGB()==Color.BLACK.getRGB())
            g.setColor(new Color(right_ic, right_ic, right_ic));
        int RightwallHeight = (int) (distToProjectionPlane * scaleOfWallHeight / right_z);
        RightwallHeight = RightwallHeight > projectionPlaneHeight / 2 ? projectionPlaneHeight / 2 : RightwallHeight;
        
        g.drawLine(x2, projectionPlaneHeight / 2 - RightwallHeight, x2, projectionPlaneHeight / 2 + RightwallHeight);
    }

    public void drawFloor3D(Graphics2D g) {
        for (int i = 0; i <= this.getHeight() / 2; i++) {
            double p = i;
            p = p > 255 ? 255 : p;

            int ic = (int) (p);
            g.setColor(new Color(100, 40, ic));
            g.drawLine(0, i, this.getWidth(), i);
        }
        for (int i = this.getHeight(); i > this.getHeight() / 2; i--) {
            double p = i;
            p = p > 255 ? 255 : p;

            int ic = (int) (p);
            g.setColor(new Color(100, 40, ic));
            g.drawLine(0, i, this.getWidth(), i);
        }
    }

    ;

    public void render(BufferedImage off3Dscreen) {
        this.off3Dgc.drawImage(off3Dscreen, 0, 0, null);
        paintComponent(getGraphics());
    }
    public void drawFloorPortal(int d, int foundedX,int foundedY,int cameraPositionX, int cameraPositionY,double cameraAngle, Graphics g){
        
        Point lTop=new Point(),rTop=new Point(),lBottom=new Point(),rBottom=new Point();
        lTop.x=(int)(Math.floor((foundedX-tileMap.getx())/tileMap.getTileSize()))*tileMap.getTileSize()+tileMap.getx();
        rTop.x=lTop.x+tileMap.getTileSize();
        lTop.y=(int)(Math.floor((foundedY-tileMap.gety())/tileMap.getTileSize()))*tileMap.getTileSize()+tileMap.gety();
        rTop.y=lTop.y;
        if(foundedY!=lTop.y) return; //drawing only top line of portal
        //drawing only top line of portal
        double tx1=(lTop.x-cameraPositionX), ty1=(lTop.y-cameraPositionY);
        double tx2=(rTop.x-cameraPositionX), ty2=(rTop.y-cameraPositionY);
        double ix1=0,iz1=0,ix2=0,iz2=0;
       
        /*
        double a = Math.atan(tx1 / distToProjectionPlane);
        double LeftSideCameraWallDistance = d;
        double left_z = LeftSideCameraWallDistance * Math.cos(a);
        */
        double tz1=((tx1*Math.cos(cameraAngle) + ty1*Math.sin(cameraAngle)));
        double tz2=((tx2*Math.cos(cameraAngle) + ty2*Math.sin(cameraAngle)));
        tx1=(tx1*Math.sin(cameraAngle) - ty1*Math.cos(cameraAngle));
        tx2=(tx2*Math.sin(cameraAngle) - ty2*Math.cos(cameraAngle));
        /* Is the wall at least partially in front of the player? */
        if(tz1 <= 0 && tz2 <= 0) return;
        /* If it's partially behind the player, clip it against player's view frustrum */
        /*if(tz1 <= 0 || tz2 <=0){
            Point.Double temp = Intersect(tx1,tz1,tx2,tz2,-0.0001,0.0001,-20,5);
            ix1=temp.x; iz1=temp.y;
            temp=Intersect(tx1,tz1,tx2,tz2,0.0001,0.0001,20,5);
            ix2=temp.x; iz2=temp.y;
            if(tz1<0.0001)if(iz1>0){
                tx1=(int)ix1; tz1=(int)iz1;}
            else{
                tx1=(int)ix2; tz1=(int)iz2;
            }
            if(tz2<0.0001)if(iz2>0){
                tx2=(int)ix1; tz2=(int)iz1;}
            else{
                tx2=(int)ix2; tz2=(int)iz2;
            }
        }   */
            
            int x1=0,y1l=0,y1r=0;
            int x2=0,y2l=0,y2r=0;
            double xscale1 =0.5f*this.getHeight(), yscale1=(.2f*this.getHeight()); // Affects the horizontal, vertical field of vision
            /* Project our ceiling & floor heights into screen coordinates (Y coordinate) */
            //int yceil=0; player.yaw=min(max(yaw- y*0.05f,-5),5)  
            //int y1a  = H/2 - (int)((yceil + tz1*player.yaw) * yscale1),  y1b = H/2 - (int)((yfloor + tz1*player.yaw) * yscale1);
            //int y2a  = H/2 - (int)((yceil + tz2*player.yaw) * yscale2),  y2b = H/2 - (int)((yfloor + tz2*player.yaw) * yscale2);
            if(tz1!=0){
                x1=(int)(-tx1/tz1); y1l=(int)(-this.getHeight()/2/tz1); y1r=(int)(this.getHeight()/2/tz1);
            }
            if(tz2!=0){
                x2=(int)(-tx2/tz2); y2l=(int)(-this.getHeight()/2/tz2); y2r=(int)(this.getHeight()/2/tz2);
            }
             g.setColor(Color.YELLOW);
        
            g.setFont(new Font("Serif", Font.BOLD, 14));
            g.drawString("tz1:"+tz1+" tz2:"+tz2 + " angle: " + cameraAngle, 10, 20);
            //if(lTop.y!=256) JOptionPane.showMessageDialog(null, "My Goodness,  "+Integer.toString(lTop.x)+ " -- " + Integer.toString(foundedX));
            if(tz1 <= 0 || tz2 <=0){
            g.drawLine(this.getWidth()/2+x1, this.getHeight()/2+y1l, this.getWidth()/2+x2, this.getHeight()/2+y2r);//top line
            g.drawLine(this.getWidth()/2+x1, this.getHeight()/2+y1r, this.getWidth()/2+x2, this.getHeight()/2+y2l);//bottom line
            //g.drawLine(this.getHeight()/2+x1, this.getWidth()/2+y1l, this.getHeight()/2+x1, this.getWidth()/2+y1r);
            g.drawLine(this.getHeight()/2+x2, this.getWidth()/2+y2l, this.getHeight()/2+x2, this.getWidth()/2+y2r);
            }
            else{
            g.drawLine(this.getWidth()/2+x1, this.getHeight()/2+y1l, this.getWidth()/2+x2, this.getHeight()/2+y2l);//top line
            g.drawLine(this.getWidth()/2+x1, this.getHeight()/2+y1r, this.getWidth()/2+x2, this.getHeight()/2+y2r);//bottom line
            //g.drawLine(this.getHeight()/2+x1, this.getWidth()/2+y1l, this.getHeight()/2+x1, this.getWidth()/2+y1r);
            g.drawLine(this.getHeight()/2+x2, this.getWidth()/2+y2l, this.getHeight()/2+x2, this.getWidth()/2+y2r);
            }
        
    }
    double FNcross(double x1, double y1,double x2,double y2){
        return x1*y2-x2*y1;
    }
    public Point.Double Intersect(double x1,double y1,double x2,double y2,double x3,double y3,double x4,double y4){
        Point.Double temp=new Point.Double(); double x=0,y=0;
        x = FNcross( x1,  y1, x2, y2);
        y = FNcross( x3,  y3, x4, y4);
        double det = FNcross( x1-x2,  y1-y2, x3-x4, y3-y4);
        x = FNcross( x,  x1-x2, y, x3-x4)/det;
        y = FNcross( x,  y1-y2, y, y3-y4)/det;
        temp.x=x;
        temp.y=y;
        return temp;
    }
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(full__buffered3Dview, 0, 0, null);
    }

    public void moveCamera(double d, double a) {
        double angle = player.cameraAngle + a;
        double s = Math.sin(angle);
        double c = Math.cos(angle);
        if (tileMap.getTile(
                (int) ((cameraPosition.y + d * c) / tileMap.getTileSize()),//x and y swiped?!
                (int) ((cameraPosition.x + d * s) / tileMap.getTileSize()))
                != 0) {
            cameraPosition.x += d * s;
            cameraPosition.y += d * c;
        }
        player.setx((int) cameraPosition.x);
        player.sety((int) cameraPosition.y);
    }

}
