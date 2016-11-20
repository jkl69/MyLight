package com.example.klaus.mylight;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by klaus on 19.11.16.
 */

 public class UDPsend implements Runnable {

    public String ip= "192.168.70.38";
    public int port=5005;
    private InetAddress serverAddr;
    public String mesg="Hallo Welt";

    public void run() {
        try {
            DatagramSocket udpSocket = new DatagramSocket(port);
            InetAddress serverAddr = InetAddress.getByName(ip);
            byte[] buf = mesg.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length,serverAddr, port);
            udpSocket.send(packet);
            udpSocket.close();
        } catch (SocketException e) {
                Log.e("Udp:", "Socket Error:", e);
        } catch (IOException e) {
                Log.e("Udp Send:", "IO Error:", e);
        }
    }
 }

