import com.sun.xml.internal.bind.v2.model.core.BuiltinLeafInfo;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {
    private int x;
    private int y;

    //to verify whether a tank belongs to mine or enemies
    private boolean good;

    //define tank's lifecycle
    private boolean alive = true;

    private Missile m;
    private TankClient tc;

    public static final int X_SPEED = 10;
    public static final int Y_SPEED = 10;
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

    public Tank(int x, int y, boolean good) {
        this.x = x;
        this.y = y;
        this.good = good;
    }

    public Tank(int x, int y, boolean good ,TankClient tc){
        this(x,y,good);
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

        //ensure tank won't break the game's boundary
        if(x <= 0) x = 0;
        if(x >= TankClient.GAME_WIDTH-Tank.WIDTH) x = TankClient.GAME_WIDTH-Tank.WIDTH;
        if(y <= 0) y = 0;
        if(y >= TankClient.GAME_HEIGHT-Tank.HEIGHT) y = TankClient.GAME_HEIGHT-Tank.HEIGHT;
    }

    public void draw(Graphics g){

        if(!alive)
            return;

        Color c = g.getColor();

        //set tank's color
        if(good == true) g.setColor(Color.red);
        else g.setColor(Color.BLUE);
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

        //synchronize the direction for tank and barrel
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
        }
    }

    private void fire() {
        int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
        int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
        m = new Missile(x, y, bDir, this.tc);
        tc.missiles.add(m);
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
            case KeyEvent.VK_CONTROL:
                fire();
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

    //get Rectangle that surrounds the Tank
    public Rectangle getRect(){
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    public boolean isAlive() { return  this.alive; }
}
