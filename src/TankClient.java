import java.awt.*;

public class TankClient extends Frame {

    public void lanchFrame(){
        this.setLocation(400,300);
        this.setSize(800,600);
        this.setVisible(true);
    }

    public static void main(String[] args){
        TankClient tc = new TankClient();
        tc.lanchFrame();
    }
}

