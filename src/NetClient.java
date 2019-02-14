import java.io.IOException;
import java.net.Socket;

public class NetClient {

    public void connect(String IP, int port){

        try {
            // Creates a stream socket and connects it to the specified port
            // number on the named host.
            Socket s = new Socket(IP, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
System.out.println("Connected to server!");
    }
}
