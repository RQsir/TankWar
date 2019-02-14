import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TankServer {

    public static final int TCP_PORT = 8888;

    public static void main(String[] args){

        try {
            // Creates a server socket, bound to the specified port.
            ServerSocket ss = new ServerSocket(TCP_PORT);
            while (true){
                // Listens for a connection to be made to this socket and accepts
                // it. The method blocks until a connection is made.
                Socket s = ss.accept();
System.out.println("A client connected! Addr- " + s.getInetAddress() + ":" + s.getPort());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
