package com.example.klaus.mylight;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {

//    private SharedPreferences pref;
    private D1Frag DialFrag = new D1Frag();

    private TabLayout tabLayout;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Addr.pref = this.getPreferences(Context.MODE_PRIVATE);
        Addr.IPtext = Addr.pref.getString("saved_IP", "127.0.0.1");
        Addr.PORT = Addr.pref.getInt("saved_PORT", 80);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        replaceFragment(new colorFragment());

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Color"));
        tabLayout.addTab(tabLayout.newTab().setText("Pattern"));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    replaceFragment(new colorFragment());
                } else {
                    replaceFragment(new patternFragment());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
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
                DialFrag.show(getSupportFragmentManager(), "connectTo");
//                Toast.makeText(this, "IP gedrückt!", Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = Addr.pref.edit();
                editor.putString("saved_IP", Addr.IPtext);
                editor.putInt("saved_PORT", Addr.PORT);
                editor.commit();
                Log.i("TCP:", "save Settings "+Addr.IPtext+":"+String.valueOf(Addr.PORT));
                return true;
//            case R.id.PORT:
//                Toast.makeText(this, "PORT gedrückt!", Toast.LENGTH_LONG).show();
//                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
