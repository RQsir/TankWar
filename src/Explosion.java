import java.awt.*;

public class Explosion {

    private int x, y;

    private boolean alive = true;

    private int[] diameter = {4, 7, 10, 13, 17, 22, 27, 31, 37, 44, 50, 55, 60, 67, 78, 85, 92, 100, 50, 20, 11, 3};
    private int step = 0;

    private TankClient tc;

    public Explosion(int x, int y, TankClient tc){
        this.x = x;
        this.y = y;
        this.tc = tc;
    }

    public void draw(Graphics g){
        if(!alive)
            return;

        if(step == diameter.length){
            alive = false;
            return;
        }

        Color c = g.getColor();

        g.setColor(Color.ORANGE);
        g.fillOval(x, y, diameter[step], diameter[step]);
        g.setColor(c);

        step++;
    }
}
