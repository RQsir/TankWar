import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame {

    @Override
    public void paint(Graphics g) {  // draw a circle represented for tank
        Color c = g.getColor();
        g.setColor(Color.red);
        g.fillOval(100,100,50,50);
        g.setColor(c);
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
    }

    public static void main(String[] args){
        TankClient tc = new TankClient();
        tc.lanchFrame();
    }
}

