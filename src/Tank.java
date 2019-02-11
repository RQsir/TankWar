import com.sun.xml.internal.bind.v2.model.core.BuiltinLeafInfo;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

public class Tank {
    private int x;
    private int y;
    private int preX;
    private int preY;

    //to verify whether a tank belongs to mine or enemies
    private boolean good;

    //define tank's lifecycle
    private boolean alive = true;
    //define tank's life_value
    private int life = 100;

    private Missile m;
    private TankClient tc;

    private BloodBar bb = new BloodBar();

    public static final int X_SPEED = 10;
    public static final int Y_SPEED = 10;
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;

    //define a static random object to control enemyTank's direction
    private static Random r = new Random();



    //generate dirs arrays
    Direction[] dirs = Direction.values();

    //define tank walk steps
    private int step = r.nextInt(13) + 3;


    //init tank condition
    Direction dir = Direction.STOP;
    //define barrel direction
    Direction bDir = Direction.D;

    //define 4 boolean values represented for left, up, right, down
    private boolean bL = false, bU = false, bR = false, bD = false;

    public Tank(int x, int y, boolean good) {
        this.x = x;
        this.y = y;
        this.good = good;
    }

    public Tank(int x, int y, boolean good ,TankClient tc, Direction dir){
        this(x,y,good);
        this.tc = tc;
        this.dir = dir;
    }

    //define move function to change x, y position
    void move(){

        preX = x;
        preY = y;

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

        //control enemyTank's direction
        if(!good){

            step--;

            if(step == 0){
                step = r.nextInt(13) + 3;
                int index = r.nextInt(dirs.length);
                dir = dirs[index];
            }

            //let enemyTank shoot
//            if(r.nextInt(40) > 35)
//                fire();
        }
    }

    public void draw(Graphics g){

        if(!alive){
            if(good == false)
                tc.enemyTanks.remove(this);
            return;
        }

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
        if(dir != Direction.STOP)
            bDir = dir;

        if(good)
            bb.draw(g);

        //invoked by draw(), that's to be invoked by PaintThread
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
            case KeyEvent.VK_F2:
                if(!alive && good){
                    alive = true;
                    life = 100;
                }
                break;
        }

        //myTank's dir changes only if key pressed, so bind getDirection() with keyPressed()
        getDirection();
    }

    public void fire() {

        //if a tank is dead, it cannot fire
        if(!alive)
            return;
        int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
        int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
        m = new Missile(x, y, bDir, this.tc, this.good);
        tc.missiles.add(m);
    }

    public void fire(Direction dir){
        //if a tank is dead, it cannot fire
        if(!alive)
            return;
        int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
        int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
        m = new Missile(x, y, dir, this.tc, this.good);
        tc.missiles.add(m);
    }

    public void superFire(){

        for(Direction dir : dirs){
            if(dir != Direction.STOP)
                fire(dir);
        }
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
            case KeyEvent.VK_A:
                superFire();
                break;
        }
    }

    public void getDirection(){
        if(bL==true && !bU==true && !bR == true && !bD == true){
            dir = Direction.L;
        }
        else if(bL==true && bU==true && !bR == true && !bD == true){
            dir = Direction.LU;
        }
        else if(!bL==true && bU==true && !bR == true && !bD == true){
            dir = Direction.U;
        }
        else if(!bL==true && bU==true && bR == true && !bD == true){
            dir = Direction.RU;
        }
        else if(!bL==true && !bU==true && bR == true && !bD == true){
            dir = Direction.R;
        }
        else if(!bL==true && !bU==true && bR == true && bD == true){
            dir = Direction.RD;
        }
        else if(!bL==true && !bU==true && !bR == true && bD == true){
            dir = Direction.D;
        }
        else if(bL==true && !bU==true && !bR == true && bD == true){
            dir = Direction.LD;
        }else
            dir = Direction.STOP;
    }

    //get Rectangle that surrounds the Tank
    public Rectangle getRect(){
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    public boolean isAlive() { return  this.alive; }

    public boolean isGood() {
        return good;
    }

    public void backward(){
        x = preX;
        y = preY;
    }

    public boolean hitWall(Wall w){


        if(this.getRect().intersects(w.getRect())){

            backward();
            return true;
        }

        return false;
    }

    public boolean collideTank(Tank t){

        if(this.getRect().intersects(t.getRect())){

            backward();
            t.backward();
            return true;
        }

        return false;
    }

    public boolean collideTanks(List<Tank> tanks){

        boolean flag = false;

        for (Tank tank:tanks) {

            //avoid collide with itself
            if(this != tank){

                flag = collideTank(tank);
            }
        }

        return flag;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    private class BloodBar {

        public void draw(Graphics g){

            Color c = g.getColor();
            g.setColor(Color.RED);
            g.drawRect(x, y-20, WIDTH,10);
            int w = WIDTH*life/100;
            g.fillRect(x,y-20, w,10);
        }
    }

    public boolean eat(Blood b){

        if(getRect().intersects(b.getRect()) && alive && good && b.isAlive()){

            life = 100;
            b.setAlive(false);
            return true;
        }

        return false;
    }
}
