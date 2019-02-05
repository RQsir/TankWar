import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {
    private int x, y;

    private static final int X_SPEED = 5;
    private static final int Y_SPEED = 5;

    //define 9 directions
    enum Directiton{L,LU,U,RU,R,RD,D,LD,STOP}

    //init tank condition
    Directiton d = Directiton.STOP;

    //define 4 button values represented for left, up, right, down
    private boolean bL = false, bU = false, bR = false, bD = false;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //define move function to change x, y position
    void move(){

        getDirection();

        switch (d){
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
        g.fillOval(x, y,50,50);
        g.setColor(c);

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

    public void getDirection(){
        if(bL==true && !bU==true && !bR == true && !bD == true){
            d = Directiton.L;
        }
        else if(bL==true && bU==true && !bR == true && !bD == true){
            d = Directiton.LU;
        }
        else if(!bL==true && bU==true && !bR == true && !bD == true){
            d = Directiton.U;
        }
        else if(!bL==true && bU==true && bR == true && !bD == true){
            d = Directiton.RU;
        }
        else if(!bL==true && !bU==true && bR == true && !bD == true){
            d = Directiton.R;
        }
        else if(!bL==true && !bU==true && bR == true && bD == true){
            d = Directiton.RD;
        }
        else if(!bL==true && !bU==true && !bR == true && bD == true){
            d = Directiton.D;
        }
        else if(bL==true && !bU==true && !bR == true && !bD == true){
            d = Directiton.LD;
        }
    }
}
