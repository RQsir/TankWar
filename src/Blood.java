import java.awt.*;

public class Blood {

    int x, y, width, height;

    int step = 0;

    boolean alive = true;

    int[][] path = {
        {300,250},{350,200},{400,150},{450,200},{500,250},
        {550,300},{600,350},{650,300},{700,250},{650,200},
        {600,150},{550,200},{500,250},{450,300},{400,350},
        {350,300},{300,250}
    };

    public Blood() {
        this.x = path[0][0];
        this.y = path[0][1];
        this.width = 30;
        this.height = 10;
    }

    public void draw(Graphics g){

        if(!alive)
            return;

        Color c = g.getColor();
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, width, height);
        g.setColor(c);

        move();
    }

    private void move() {

        step++;

        if(step == path.length)
            step = 0;

        for(int i=0; i<path.length; i++){
            x = path[step][0];
            y = path[step][1];
        }
    }

    public Rectangle getRect(){
        return new Rectangle(x, y, width, height);
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
