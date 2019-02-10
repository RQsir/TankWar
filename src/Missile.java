import java.awt.*;
import java.util.List;

public class Missile {

    public static final int X_SPEED = 20;
    public static final int Y_SPEED = 20;
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;

    private  TankClient tc;

    private boolean alive = true;

    private boolean good;

    private int x, y;
    private Tank.Directiton dir;

    public Missile(int x, int y, Tank.Directiton dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public Missile(int x, int y, Tank.Directiton dir, TankClient tc, boolean good){
        this(x, y, dir);
        this.tc = tc;
        this.good = good;
    }

    public void draw(Graphics g){
        //judge whether the missile is alive
        if(!alive){
            tc.missiles.remove(this);
            return;
        }

        Color c = g.getColor();

        if(good){
            g.setColor(Color.RED);
            g.fillOval(x, y, WIDTH,HEIGHT);
        }else {
            g.setColor(Color.BLUE);
            g.fillOval(x, y, WIDTH,HEIGHT);
        }

        g.setColor(c);

        move();
    }

    private void move() {

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
        }

        //if missile go out of the game window, remove it from missiles list
        if(x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT){
            alive = false;
        }
    }

    //get Rectangle that surrounds the Missile
    public Rectangle getRect(){
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public boolean hitWall(Wall w){

        if(this.getRect().intersects(w.getRect()) && !good){
            this.alive = false;


            return true;
        }

        return false;
    }

    public boolean hitTank(Tank t){
        //use 2 rectangles to surround enemyTank and missile, and use "intersect" function to judge if a
        // missile has hit a tank
        if(this.getRect().intersects(t.getRect()) && t.isAlive() && this.good != t.isGood() && this.alive){

            if(t.isGood()){
                t.setLife(t.getLife() - 20);
                if(t.getLife() <= 0){
                    t.setAlive(false);
                }
            }else {

                t.setAlive(false);
            }

            this.alive = false;

            //add explosion
            Explosion e = new Explosion(x, y, tc);
            tc.explosions.add(e);

            return true;
        }
        return false;
    }

    public boolean hitTanks(List<Tank> enemyTanks) {

        if(enemyTanks.size() > 0){

            for(int i=0; i<enemyTanks.size(); i++){
                if(hitTank(enemyTanks.get(i)))
                    return true;
            }
        }

        return false;
    }
}
