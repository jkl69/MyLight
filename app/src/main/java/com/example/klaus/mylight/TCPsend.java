package com.example.klaus.mylight;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;


/**
 * Created by klaus on 19.11.16.
 */

 public class TCPsend implements Runnable {

    static public String ip= "192.168.70.21";
    static public int port=5005;
    private InetAddress serverAddr;
    public String mesg="Hallo Welt";
    public Activity activity = null;// reference to an Activity

    public void showToast(final String toast) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, toast, Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    public void run() {
        String result="OK";
        try{
            Log.e("TCP Send:", "Try send to "+ip+":"+String.valueOf(port));
// Creating new socket connection to the IP (first parameter) and its opened port (second parameter)
            Socket s = new Socket(ip, port);
            DataOutputStream DOS = new DataOutputStream(s.getOutputStream());
            DOS.writeBytes(mesg );
//            DOS.writeUTF(mesg);
            s.close();
        }
        catch(Exception ex){
            Log.e("TCP Send:", "IO Error:"+ ex.getMessage());
            result="Cant reach Light !";
            }
        showToast(result );
   }

 }

