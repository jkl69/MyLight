package com.example.klaus.mylight;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

//    private UDPsend udpSend;
    private TCPsend udpSend;
    private GradientView mTop;
    private GradientView mBottom;
    private TextView mTextView;
    private Drawable mIcon;
    private String colormsg="";

//    private SharedPreferences pref;
    private D1Frag DialFrag = new D1Frag();
//    private String IP;
//    private int PORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Addr.pref = this.getPreferences(Context.MODE_PRIVATE);
//        UDPsend.ip = pref.getString("saved_IP", "127.0.0.1");
//        UDPsend.port = pref.getInt("saved_PORT", 5005);
        Addr.IPtext = Addr.pref.getString("saved_IP", "127.0.0.1");
        Addr.PORT = Addr.pref.getInt("saved_PORT", 5005);
        setContentView(R.layout.activity_main);
        mIcon = getResources().getDrawable(R.mipmap.ic_launcher);
//        mIcon = this.getResources().getDrawable(R.mipmap.ic_launcher);
        mTextView = (TextView)findViewById(R.id.color);
        mTextView.setCompoundDrawablesWithIntrinsicBounds(mIcon, null, null, null);
        mTop = (GradientView)findViewById(R.id.top);
        mBottom = (GradientView)findViewById(R.id.bottom);
        mTop.setBrightnessGradientView(mBottom);
        int color = 0xFF394572;
        mTop.setColor(color);
        mBottom.setOnColorChangedListener(new GradientView.OnColorChangedListener() {
            @Override
            public void onColorChanged(GradientView view, int color) {
                mTextView.setTextColor(color);
                mTextView.setText("T#" + Integer.toHexString(color));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mIcon.setTint(color);
                }
                String cmsg= "Color="+Integer.toString(Color.red(color))+
                        ","+Integer.toString(Color.green(color))+
                        ","+Integer.toString(Color.blue(color));
                if (! cmsg.equals(colormsg)) {
                    Log.i("Udp:", "newColor");
                    colormsg= cmsg;
                    sendData(colormsg);
                }
            }
        });
    }

    public void sendData(String text) {
        Thread th;
        Log.i("Udp:", "SEND '"+text+"' to "+UDPsend.ip+":"+String.valueOf(UDPsend.port));
//        udpSend = new UDPsend();
        udpSend = new TCPsend();
        th = new Thread(udpSend);
        udpSend.activity=this;
        udpSend.ip= Addr.IPtext;
        udpSend.port= Addr.PORT;
        udpSend.mesg= text;
        th.start();
    }

    @Override
   public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lights, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.IP:
//                DialFrag.IPtext=pref.getString("saved_IP", "127.0.0.1");
//                DialFrag.PORT= pref.getInt("saved_PORT", 5005);
                DialFrag.show(getSupportFragmentManager(), "connectTo");
//                Toast.makeText(this, "IP gedrückt!", Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = Addr.pref.edit();
                editor.putString("saved_IP", Addr.IPtext);
                editor.putInt("saved_PORT", Addr.PORT);
                editor.commit();
                Log.i("Udp:", "save Settings "+Addr.IPtext+":"+String.valueOf(Addr.PORT));
                return true;
//            case R.id.PORT:
//                Toast.makeText(this, "PORT gedrückt!", Toast.LENGTH_LONG).show();
//                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
