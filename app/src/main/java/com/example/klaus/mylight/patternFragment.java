package com.example.klaus.mylight;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by jaen on 21.02.2017.
 */

public class patternFragment extends Fragment {

    private TCPsend tcpSend;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pattern_fragment, container, false);
        Button button = (Button) view.findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                sendData("Pattern=h");
            }
        });
        button = (Button) view.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                sendData("Pattern=f");
            }
        });
        button = (Button) view.findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                sendData("Pattern=r");
            }
        });
        return view;
    }

    public void sendData(String text) {
        Thread th;
//        Log.i("TCP:", "SEND '"+text+"' to "+TCPsend.ip+":"+String.valueOf(TCPsend.port));
        tcpSend = new TCPsend();
        th = new Thread(tcpSend);
        tcpSend.activity= getActivity();
        tcpSend.ip= Addr.IPtext;
        tcpSend.port= Addr.PORT;
        tcpSend.mesg= text;
        th.start();
    }
}
