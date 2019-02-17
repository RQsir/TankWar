import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class TankNewMsg {

    Tank tank;

    public TankNewMsg(Tank tank) {
        this.tank = tank;
    }

    public TankNewMsg() {

    }

    public void send(String IP, int udpPort, DatagramSocket ds) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(tank.id);
            dos.writeInt(tank.getX());
            dos.writeInt(tank.getY());
            dos.writeInt(tank.dir.ordinal());
            dos.writeBoolean(tank.isGood());
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] buf = baos.toByteArray();

        // constructs udp packet
        DatagramPacket dp = new DatagramPacket(buf,buf.length,new InetSocketAddress(IP,udpPort));

        try {
            ds.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parse(DatagramPacket dp) {

        byte[] data = dp.getData();
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        try {
            int id = dis.readInt();
            int x = dis.readInt();
            int y = dis.readInt();
            Direction dir = Direction.values()[dis.readInt()];
            boolean good  = dis.readBoolean();
System.out.println("id:" + id + "--x:" + x + "--y:" + y + "--dir:" + dir + "--good:" + good);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
