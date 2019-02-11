import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Explosion {

    private int x, y;

    private boolean alive = true;

    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static BufferedImage[] imgs;

    static {
        try {
            imgs = new BufferedImage[]{
                    ImageIO.read(Explosion.class.getClassLoader().getResource("images/explosion0.png")),
                    ImageIO.read(Explosion.class.getClassLoader().getResource("images/explosion1.png")),
                    ImageIO.read(Explosion.class.getClassLoader().getResource("images/explosion2.png")),
                    ImageIO.read(Explosion.class.getClassLoader().getResource("images/explosion3.png")),
                    ImageIO.read(Explosion.class.getClassLoader().getResource("images/explosion4.png")),
                    ImageIO.read(Explosion.class.getClassLoader().getResource("images/explosion5.png")),
                    ImageIO.read(Explosion.class.getClassLoader().getResource("images/explosion6.png")),
                    ImageIO.read(Explosion.class.getClassLoader().getResource("images/explosion7.png")),
                    ImageIO.read(Explosion.class.getClassLoader().getResource("images/explosion8.png")),
                    ImageIO.read(Explosion.class.getClassLoader().getResource("images/explosion9.png")),
                };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int step = 0;

    private TankClient tc;

    public Explosion(int x, int y, TankClient tc){
        this.x = x;
        this.y = y;
        this.tc = tc;
    }

    public void draw(Graphics g){
        if(!alive){
            tc.explosions.remove(this);
            return;
        }

        if(step == imgs.length){
            alive = false;
            return;
        }

       g.drawImage(imgs[step], x, y, null);

        step++;
    }
}
