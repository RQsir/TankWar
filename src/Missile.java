import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Missile {

    private static HashMap<Direction, BufferedImage> myMissiles = new HashMap<Direction,BufferedImage>();
    private static HashMap<Direction,BufferedImage> enemyMissiles = new HashMap<Direction,BufferedImage>();
    public static final int X_SPEED = 30;
    public static final int Y_SPEED = 30;
    public static int WIDTH;
    public static int HEIGHT;

    static {
        try {
            myMissiles.put(Direction.L, ImageIO.read(Tank.class.getClassLoader().getResource("images/myMissile_L.png")));
            myMissiles.put(Direction.LU, ImageIO.read(Tank.class.getClassLoader().getResource("images/myMissile_LU.png")));
            myMissiles.put(Direction.U, ImageIO.read(Tank.class.getClassLoader().getResource("images/myMissile_U.png")));
            myMissiles.put(Direction.RU, ImageIO.read(Tank.class.getClassLoader().getResource("images/myMissile_RU.png")));
            myMissiles.put(Direction.R, ImageIO.read(Tank.class.getClassLoader().getResource("images/myMissile_R.png")));
            myMissiles.put(Direction.RD, ImageIO.read(Tank.class.getClassLoader().getResource("images/myMissile_RD.png")));
            myMissiles.put(Direction.D, ImageIO.read(Tank.class.getClassLoader().getResource("images/myMissile_D.png")));
            myMissiles.put(Direction.LD, ImageIO.read(Tank.class.getClassLoader().getResource("images/myMissile_LD.png")));

            enemyMissiles.put(Direction.L, ImageIO.read(Tank.class.getClassLoader().getResource("images/enemyMissile_L.png")));
            enemyMissiles.put(Direction.LU, ImageIO.read(Tank.class.getClassLoader().getResource("images/enemyMissile_LU.png")));
            enemyMissiles.put(Direction.U, ImageIO.read(Tank.class.getClassLoader().getResource("images/enemyMissile_U.png")));
            enemyMissiles.put(Direction.RU, ImageIO.read(Tank.class.getClassLoader().getResource("images/enemyMissile_RU.png")));
            enemyMissiles.put(Direction.R, ImageIO.read(Tank.class.getClassLoader().getResource("images/enemyMissile_R.png")));
            enemyMissiles.put(Direction.RD, ImageIO.read(Tank.class.getClassLoader().getResource("images/enemyMissile_RD.png")));
            enemyMissiles.put(Direction.D, ImageIO.read(Tank.class.getClassLoader().getResource("images/enemyMissile_D.png")));
            enemyMissiles.put(Direction.LD, ImageIO.read(Tank.class.getClassLoader().getResource("images/enemyMissile_LD.png")));

            WIDTH = ImageIO.read(Tank.class.getClassLoader().getResource("images/myMissile_L.png")).getWidth();
            HEIGHT = ImageIO.read(Tank.class.getClassLoader().getResource("images/myMissile_L.png")).getHeight();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  TankClient tc;

    private boolean alive = true;

    private boolean good;

    private int x, y;
    private Direction dir;

    public Missile(int x, int y, Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public Missile(int x, int y, Direction dir, TankClient tc, boolean good){
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


        if(good){
            g.drawImage(myMissiles.get(dir), x, y, null);
        }else {
            g.drawImage(enemyMissiles.get(dir), x, y, null);
        }

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
