import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TankServer {

    private static int ID = 100;
    public static final int TCP_PORT = 8888;

    private List<Client> clients = new ArrayList<Client>();

    public static void main(String[] args){

        new TankServer().start();

    }

    private void start(){

        // Creates a server socket
        ServerSocket ss = null;

        try {
            ss = new ServerSocket(TCP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }


        while (true){

            Socket s = null;
            try {
                // Listens for a connection to be made to this socket and accepts
                // it. The method blocks until a connection is made.
                s = ss.accept();
                DataInputStream dis = new DataInputStream(s.getInputStream());
                int udpPort =  dis.readInt();
                String IP = s.getInetAddress().getHostAddress();
                Client client = new Client(IP,udpPort);
                clients.add(client);
System.out.println("A client connected! Addr- " + s.getInetAddress() + ":" + s.getPort() + "------udpPort:" + udpPort);
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(ID++);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(s != null){
                    try {
                        s.close();
                        s = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }


    }

    private class Client{

        String IP;
        int udpPort;

        public Client(String IP, int udpPort) {
            this.IP = IP;
            this.udpPort = udpPort;
        }
    }
}
