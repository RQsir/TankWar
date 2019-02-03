import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame {

    public void lanchFrame(){
        this.setLocation(400,300);
        this.setSize(800,600);
        this.setVisible(true);

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

