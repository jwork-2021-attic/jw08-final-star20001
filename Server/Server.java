import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    private final static int Port = 8800;


    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(Port);
        byte[] newscore = new byte[100];
        DatagramPacket packet = new DatagramPacket(newscore, newscore.length);
        System.out.println("Waiting");
        socket.receive(packet);
        String info = new String(newscore, 0, packet.getLength());
        long score = Long.parseLong(info);
        System.out.println(score);
        int i = 100;
        byte[] data;
        if (score < i) {
            data = "You are NO.1".getBytes();
        } else {
            data = "You are not NO.1".getBytes();
        }
        InetAddress add = packet.getAddress();
        int cport = packet.getPort();
        
        DatagramPacket packet2 = new DatagramPacket(data, data.length, add, cport);
        socket.send(packet2);
        System.out.println("Close");
        socket.close();
    }
}