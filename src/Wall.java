import java.awt.*;

public class Wall {

    private int x, y, width, height;

    public Wall(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public void draw(Graphics g){

        Color c = g.getColor();
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
        g.setColor(c);
    }

    //get Rectangle that surrounds the wall
    public Rectangle getRect(){
        return new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
