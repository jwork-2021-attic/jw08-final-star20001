package com;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class Server {
    private final static int Port = 8800;
    private int[] a = new int[10];

    public Server() {

        File file = new File("list.txt");
        try {
            FileInputStream fileinput = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fileinput);
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            a[0] = Integer.valueOf(line).intValue();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        
        DatagramSocket socket = new DatagramSocket(Port);
        byte[] newscore = new byte[100];
        DatagramPacket packet = new DatagramPacket(newscore, newscore.length);
        System.out.println("Waiting");
        socket.receive(packet);
        String info = new String(newscore, 0, packet.getLength());
        long score = Long.parseLong(info);
        System.out.println(score);
        byte[] data;
        if (score < 100) {
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