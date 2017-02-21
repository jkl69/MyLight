package com.example.klaus.mylight;

import android.app.Activity;
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
import android.widget.TextView;

/**
 * Created by jaen on 21.02.2017.
 */

public class colorFragment extends Fragment {

    private TCPsend tcpSend;
    private GradientView gTop;
    private GradientView gBottom;
    private TextView cText;
    private Drawable gIcon;
    private String colormsg="";

    int color = 0xFF394572;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gIcon = getResources().getDrawable(R.mipmap.ic_launcher);
//      gIcon = this.getResources().getDrawable(R.mipmap.ic_launcher);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.color_fragment, container, false);
        cText = (TextView) view.findViewById(R.id.color);
 //       cText.setCompoundDrawablesWithIntrinsicBounds(gIcon, null, null, null);
        cText.setText("#" + Integer.toHexString(color));
        gTop = (GradientView) view.findViewById(R.id.top);
        gBottom = (GradientView) view.findViewById(R.id.bottom);
        gTop.setBrightnessGradientView(gBottom);
        gTop.setColor(color);

        gBottom.setOnColorChangedListener(new GradientView.OnColorChangedListener() {
            @Override
            public void onColorChanged(GradientView view, int color) {
                cText.setTextColor(color);
                cText.setText("#" + Integer.toHexString(color));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    gIcon.setTint(color);
                }
                String cmsg = "Color=" + Integer.toString(Color.red(color)) + "," + Integer.toString(Color.green(color)) + "," + Integer.toString(Color.blue(color));
                if (!cmsg.equals(colormsg)) {
                    Log.i("TCP:", "newColor");
                    colormsg = cmsg;
                    sendData(colormsg);
                }
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
