import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame {

    //constant
    public static final int GAME_WIDTH = 900;
    public static final int GAME_HEIGHT = 800;


    Tank myTank = new Tank(50,50);

    //create a virtual screen image for double-buffer
    Image offScreenImage = null;

    @Override
    public void paint(Graphics g) {  // draw a circle represented for tank
       myTank.draw(g);
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

        paint(offScreenPaintor);
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
    }
}

