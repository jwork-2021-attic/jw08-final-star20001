package com.server;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import com.screen.*;

public class ClientListen implements Runnable {
    public Screen mainScreen;
    public Socket socket;
    public DataInputStream stream;

    public ClientListen(Screen mainScreen, Socket socket, DataInputStream stream) {
        this.mainScreen = mainScreen;
        this.socket = socket;
        this.stream = stream;
    }
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (true) {
//            System.out.println("client listen");
            try {
                String s = stream.readUTF();
                System.out.println("client get: " + s);
                //mainScreen.handleClientListen(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
