import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetClient {

    public static int UDP_PORT_START = 2333;
    int udpPort;
    private TankClient tc;

    public NetClient(TankClient tc) {
        this.udpPort = UDP_PORT_START++;
        this.tc = tc;
    }

    public void connect(String IP, int port){

        Socket s = null;

        try {
            // Creates a stream socket and connects it to the specified port
            // number on the named host.
            s = new Socket(IP, port);
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeInt(udpPort);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            int id;
            id = dis.readInt();
            tc.myTank.id = id;
            System.out.println("Connected to server!"+"Addr-" + s.getInetAddress() + ":" + s.getPort() + ", and server gives me a id:" + id);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(s != null){

                    s.close();
                    s = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
