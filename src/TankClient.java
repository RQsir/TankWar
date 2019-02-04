import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame {

    int x = 100, y = 100;
    @Override
    public void paint(Graphics g) {  // draw a circle represented for tank
        Color c = g.getColor();
        g.setColor(Color.red);
        g.fillOval(x, y,50,50);
        g.setColor(c);
        x += 5;
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
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}

