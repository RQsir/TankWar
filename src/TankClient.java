import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame {

    int x = 100, y = 100;

    //create a virtual screen image for double-buffer
    Image offScreenImage = null;

    @Override
    public void paint(Graphics g) {  // draw a circle represented for tank
        Color c = g.getColor();
        g.setColor(Color.red);
        g.fillOval(x, y,50,50);
        g.setColor(c);
        x += 5;
    }

    @Override
    public void update(Graphics g) {
        if(offScreenImage == null)
            offScreenImage = this.createImage(800,600);
        Graphics offScreenPaintor = offScreenImage.getGraphics();

        //refresh bg
        Color c = offScreenPaintor.getColor();
        offScreenPaintor.setColor(Color.green);
        offScreenPaintor.fillRect(0,0,800,600);
        offScreenPaintor.setColor(c);

        paint(offScreenPaintor);
        g.drawImage(offScreenImage,0,0,null);

    }

    public void lanchFrame(){
        this.setLocation(400,300);
        this.setSize(800,600);
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
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}

