import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class TankServer {

    private static int ID = 100;
    public static final int TCP_PORT = 8888;
    public static final int UDP_PORT = 6666;

    private List<Client> clients = new ArrayList<Client>();

    public static void main(String[] args){

        new TankServer().start();

    }

    private void start(){

        new Thread(new UDPThread()).start();

        // Creates a server socket
        ServerSocket ss = null;

        try {
            ss = new ServerSocket(TCP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }


        while (true){ // establish tcp thread (by the main thread)

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
System.out.println("A client connected! Addr- " + s.getInetAddress() + "tcpPort:" + s.getPort() + "------udpPort:" + udpPort);
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

    private class UDPThread implements Runnable{

        byte[] buf = new byte[1024];
        @Override
        public void run() {

            DatagramSocket ds = null;
            try {
                ds = new DatagramSocket(UDP_PORT);
            } catch (SocketException e) {
                e.printStackTrace();
            }
System.out.println("server starts UDP at port:"+UDP_PORT);

            while(ds != null){

                DatagramPacket dp = new DatagramPacket(buf,buf.length);
                try {
                    ds.receive(dp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
System.out.println("server receives a UDP packet");

                // forward the UDP packet to each client
                for(Client client:clients){

                    dp.setSocketAddress(new InetSocketAddress(client.IP,client.udpPort));
                    try {
                        ds.send(dp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
