import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame {

    //constant
    public static final int GAME_WIDTH = 900;
    public static final int GAME_HEIGHT = 800;

    //init instance
    List<Missile> missiles = new ArrayList<Missile>();
    Tank myTank = new Tank(50, 50, true, this);
    Tank enemyTank = new Tank(100, 100, false, this);
    Explosion e = new Explosion(200,200,this);

    //create a virtual screen image for double-buffer
    Image offScreenImage = null;

    @Override
    public void paint(Graphics g) {  // draw a circle represented for tank
       myTank.draw(g);
       for(int i=0; i<missiles.size(); i++){
           missiles.get(i).isHit(enemyTank);
           missiles.get(i).draw(g);
       }

       enemyTank.draw(g);
       g.drawString("missiles count:"+missiles.size(),10,30);

       e.draw(g);
    }

    @Override
    public void update(Graphics g) {

        if(offScreenImage == null)
            offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
        Graphics offScreenPaintor = offScreenImage.getGraphics();

        //refresh bg
        Color c = offScreenPaintor.getColor();
        offScreenPaintor.setColor(Color.green);
        offScreenPaintor.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        offScreenPaintor.setColor(c);

        //draw goods on virtual screen
        paint(offScreenPaintor);

        //mapping virtual screen to physical screen
        g.drawImage(offScreenImage,0,0,null);

    }

    public void lanchFrame(){
        this.setLocation(400,300);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.setVisible(true);
        this.setBackground(Color.green);
        this.setTitle("TankWar");

        //add close window function
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //add window size unchangeable
        this.setResizable(false);

        //invoke PaintThread
        new Thread(new PaintThread()).start();

        //invoke Key_Listener
        this.addKeyListener(new KeyMonitor());
    }

    public static void main(String[] args){
        TankClient tc = new TankClient();
        tc.lanchFrame();
    }

    //add a Thead for tank movement
    private class PaintThread implements Runnable{

        @Override
        public void run() {

            while (true) {
                repaint();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    //add key-listener to control the tank
    private class KeyMonitor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
           myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }
}

