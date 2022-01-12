package com.server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.screen.*;
public class newClient {
    private Socket socket;
    private Screen mainScreen;
    public boolean connected;
    public static final int SERVER_PORT = 9999;

    public newClient(Screen mainScreen) {
        this.mainScreen = mainScreen;
        this.connected = false;
    }

    public void connect() throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), SERVER_PORT), 3000);
        System.out.println("client: " + socket.getLocalAddress() + " port: " + socket.getLocalPort());
        System.out.println("server: " + socket.getInetAddress() + " port: " + socket.getPort());
        DataInputStream stream = new DataInputStream(socket.getInputStream());
        new Thread(new ClientListen(mainScreen, socket, stream)).start();
    }

    public void send(String s) throws IOException {
        System.out.println("client send: " + s);
        DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
        stream.writeUTF(s);
        stream.flush();
    }

}
