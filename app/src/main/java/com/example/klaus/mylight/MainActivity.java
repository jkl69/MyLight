package com.example.klaus.mylight;

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

public class MainActivity extends AppCompatActivity {

    private UDPsend udpSend;
    private GradientView mTop;
    private GradientView mBottom;
    private TextView mTextView;
    private Drawable mIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        setContentView(R.layout.activity_main);
        mIcon = getResources().getDrawable(R.mipmap.ic_launcher);
//        mIcon = context.getResources().getDrawable(R.mipmap.ic_launcher);
        mTextView = (TextView)findViewById(R.id.color);
        mTextView.setCompoundDrawablesWithIntrinsicBounds(mIcon, null, null, null);
        mTop = (GradientView)findViewById(R.id.top);
        mBottom = (GradientView)findViewById(R.id.bottom);
        mTop.setBrightnessGradientView(mBottom);
        mBottom.setOnColorChangedListener(new GradientView.OnColorChangedListener() {
            @Override
            public void onColorChanged(GradientView view, int color) {
                mTextView.setTextColor(color);
                mTextView.setText("#" + Integer.toHexString(color));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mIcon.setTint(color);
                }
                sendData("r:"+Integer.toString(Color.red(color))+
                        ",g:"+Integer.toString(Color.green(color))+
                        ",b:"+Integer.toString(Color.blue(color)));
            }
        });
        int color = 0xFF394572;
        mTop.setColor(color);
    }

    public void sendData(String text) {
        Thread th;
        Log.e("Udp:", "SEND "+text);
        udpSend = new UDPsend();
        th = new Thread(udpSend);
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
        Log.e("Udp:", "Settings ");
        switch (item.getItemId()) {
            case R.id.IP:
                Toast.makeText(this, "IP gedrückt!", Toast.LENGTH_LONG).show();
//                editNote(info.id);
                return true;
            case R.id.PORT:
                Toast.makeText(this, "PORT gedrückt!", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
