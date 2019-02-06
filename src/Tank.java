import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {
    private int x, y;

    private Missile m;
    private TankClient tc;

    public static final int X_SPEED = 5;
    public static final int Y_SPEED = 5;
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;


    //define 9 directions
    enum Directiton{L,LU,U,RU,R,RD,D,LD,STOP}

    //init tank condition
    Directiton dir = Directiton.STOP;
    //define barrel direction
    Directiton bDir = Directiton.D;

    //define 4 button values represented for left, up, right, down
    private boolean bL = false, bU = false, bR = false, bD = false;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tank(int x, int y, TankClient tc){
        this(x,y);
        this.tc = tc;
    }

    //define move function to change x, y position
    void move(){

        getDirection();

        switch (dir){
            case L:
                x -= X_SPEED;
                break;
            case LU:
                x -= X_SPEED;
                y -= Y_SPEED;
                break;
            case U:
                y -= Y_SPEED;
                break;
            case RU:
                x += X_SPEED;
                y -= Y_SPEED;
                break;
            case R:
                x += X_SPEED;
                break;
            case RD:
                x += X_SPEED;
                y += Y_SPEED;
                break;
            case D:
                y += Y_SPEED;
                break;
            case LD:
                x -= X_SPEED;
                y += Y_SPEED;
                break;
            case STOP:
                break;

        }
    }

    public void draw(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.red);
        g.fillOval(x, y,WIDTH,HEIGHT);

        //add a barrel for tank
        g.setColor(Color.BLACK);
        switch (bDir){
            case L:
                g.drawLine(x+WIDTH/2,y+HEIGHT/2,x,y+HEIGHT/2);
                break;
            case LU:
                g.drawLine(x+WIDTH/2,y+HEIGHT/2,x,y);
                break;
            case U:
                g.drawLine(x+WIDTH/2,y+HEIGHT/2,x+WIDTH/2,y);
                break;
            case RU:
                g.drawLine(x+WIDTH/2,y+HEIGHT/2,x+WIDTH,y);
                break;
            case R:
                g.drawLine(x+WIDTH/2,y+HEIGHT/2,x+WIDTH,y+HEIGHT/2);
                break;
            case RD:
                g.drawLine(x+WIDTH/2,y+HEIGHT/2,x+WIDTH,y+HEIGHT);
                break;
            case D:
                g.drawLine(x+WIDTH/2,y+HEIGHT/2,x+WIDTH/2,y+HEIGHT);
                break;
            case LD:
                g.drawLine(x+WIDTH/2,y+HEIGHT/2,x,y+HEIGHT);
                break;
        }

        g.setColor(c);

        if(dir != Directiton.STOP)
            bDir = dir;

        move();
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_CONTROL:
                fire();
                break;
        }
    }

    private void fire() {
        int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
        int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
        m = new Missile(x, y, bDir);
        tc.missile = m;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
        }
    }

    public void getDirection(){
        if(bL==true && !bU==true && !bR == true && !bD == true){
            dir = Directiton.L;
        }
        else if(bL==true && bU==true && !bR == true && !bD == true){
            dir = Directiton.LU;
        }
        else if(!bL==true && bU==true && !bR == true && !bD == true){
            dir = Directiton.U;
        }
        else if(!bL==true && bU==true && bR == true && !bD == true){
            dir = Directiton.RU;
        }
        else if(!bL==true && !bU==true && bR == true && !bD == true){
            dir = Directiton.R;
        }
        else if(!bL==true && !bU==true && bR == true && bD == true){
            dir = Directiton.RD;
        }
        else if(!bL==true && !bU==true && !bR == true && bD == true){
            dir = Directiton.D;
        }
        else if(bL==true && !bU==true && !bR == true && !bD == true){
            dir = Directiton.LD;
        }else
            dir = Directiton.STOP;
    }
}
