package com.example.klaus.mylight;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by klaus on 19.11.16.
 */

 public class TCPsend implements Runnable {

    static public String ip= "192.168.70.21";
    static public int port=5005;
    private InetAddress serverAddr;
    public String mesg="Hallo Welt";

    public void run() {
        try{
            Log.e("TCP Send:", "Try send");
// Creating new socket connection to the IP (first parameter) and its opened port (second parameter)
            Socket s = new Socket(ip, port);
//            DataOutputStream DOS = new DataOutputStream(s.getOutputStream());
//            DOS.writeUTF(mesg);
//            s.close();
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            out.write(mesg);
            out.flush();
            out.close();
            s.close();
        }
        catch(Exception ex){
            Log.e("TCP Send:", "IO Error:"+ ex.getMessage());
        }
    }
 }

