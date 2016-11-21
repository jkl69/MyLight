package com.example.klaus.mylight;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.InetAddress;


/**
 * Created by Klaus on 21.11.2016.
 */

public class D1Frag extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.connection, null);
        AlertDialog.Builder P1 = new AlertDialog.Builder(getActivity());
//      p1.setView(inflater.inflate(R.layout.connection, null)).
        P1.setView(view);
        final EditText IPinput = (EditText) view.findViewById(R.id.IP);
        final EditText PORTinput = (EditText) view.findViewById(R.id.PORT);
        IPinput.setText(UDPsend.ip);
        PORTinput.setText(String.valueOf(UDPsend.port));
        P1.setTitle("Light Address").
            setPositiveButton(R.string.B1, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    String IPtext= IPinput.getText().toString();
                    int PORT = Integer.valueOf(PORTinput.getText().toString());
                    try {
                        InetAddress serverAddr = InetAddress.getByName(IPtext);
                        Log.i("Udp:", "VALID HOST "+IPtext+":"+PORTinput.getText().toString());
                        UDPsend.ip = IPtext;
                        UDPsend.port = PORT;
//                        if (! serverAddr.isReachable(1000)) {Log.e("Udp:", "INVALID HOST 2"+IPtext); }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "INVALID HOST "+IPtext, Toast.LENGTH_LONG).show();
                        Log.e("Udp:", "INVALID HOST "+IPtext);
                    }
                }
        })
                .setNegativeButton(R.string.B2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        return P1.create();
    }
}
