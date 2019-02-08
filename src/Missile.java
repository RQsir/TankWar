import java.awt.*;

public class Missile {

    public static final int X_SPEED = 20;
    public static final int Y_SPEED = 20;
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;

    private  TankClient tc;

    private boolean alive = true;

    private int x, y;
    private Tank.Directiton dir;

    public Missile(int x, int y, Tank.Directiton dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public Missile(int x, int y, Tank.Directiton dir, TankClient tc){
        this(x, y, dir);
        this.tc = tc;
    }

    public void draw(Graphics g){
        //judge whether the missile is alive
        if(!alive)
            return;

        Color c = g.getColor();
        g.setColor(Color.BLACK);
        g.fillOval(x, y, WIDTH,HEIGHT);
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
            tc.missiles.remove(this);
        }
    }

    //get Rectangle that surrounds the Missile
    public Rectangle getRect(){
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public boolean isHit(Tank t){
        //use 2 rectangles to surround enemyTank and missile, and use "intersect" function to judge if a
        // missile has hit a tank
        if(this.getRect().intersects(t.getRect()) && t.isAlive()){
            this.alive = false;
            t.setAlive(false);

            //add explosion
            Explosion e = new Explosion(x, y, tc);
            tc.explosions.add(e);

            return true;
        }
        return false;
    }
}
