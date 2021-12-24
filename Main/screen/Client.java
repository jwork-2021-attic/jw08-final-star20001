package screen;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    private String result;

    public Client(long score) throws IOException {
        String s = String.valueOf(score);
        byte[] data = s.getBytes();
        InetAddress add = InetAddress.getByName("localhost");
        int port = 8800;
        DatagramPacket packet = new DatagramPacket(data, data.length, add, port);
        DatagramSocket socket = new DatagramSocket();
        socket.send(packet);
        byte[] data2 = new byte[100];
        DatagramPacket packet2 = new DatagramPacket(data2, data2.length);
        System.out.println("Waiting");
        socket.receive(packet2);
        result = new String(data2, 0, packet2.getLength());
        System.out.println("Receive");
        socket.close();
    }

    public String getResult() {
        return this.result;
    }
}
