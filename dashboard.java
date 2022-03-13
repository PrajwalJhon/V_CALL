package com.example.exmp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class dashboard extends AppCompatActivity {

    EditText scodebox;
    Button jb,sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        scodebox=findViewById(R.id.codeBox);
        jb=findViewById(R.id.joinBtn);
        sb=findViewById(R.id.shareBtn);
        URL u;
        try {
            u=new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaul =new JitsiMeetConferenceOptions.Builder().setServerURL(u).setWelcomePageEnabled(false).build();
            JitsiMeet.setDefaultConferenceOptions(defaul);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        jb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JitsiMeetConferenceOptions op=new JitsiMeetConferenceOptions.Builder().setRoom(scodebox.getText().toString()).setWelcomePageEnabled(false).build();

                JitsiMeetActivity.launch(dashboard.this,op);
            }
        });
        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = scodebox.getText().toString();
                Intent intent =new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");

                intent.putExtra(Intent.EXTRA_TEXT,value);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Use This Code To Join The Meeting");
                startActivity(Intent.createChooser(intent,"Share Via"));
            }
        });


        //Intialize of bottom navigation
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch(item.getItemId())
               {
                   case R.id.home:
                       startActivity(new Intent(getApplicationContext(),dashboard.class));
                       overridePendingTransition(0,0);
                       return true;
                   case R.id.his:
                       startActivity(new Intent(getApplicationContext(),signup.class));
                       overridePendingTransition(0,0);
                       return true;
                   case R.id.sett:
                       startActivity(new Intent(getApplicationContext(),user.class));
                       overridePendingTransition(0,0);
                       return true;

                   case R.id.logout:
                       startActivity(new Intent(getApplicationContext(),MainActivity.class));
                       overridePendingTransition(0,0);
                       return true;



               }



                return false;
            }
        });



    }
   /*  @Override
    public boolean onCreateOptionsMenu(Menu m)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,m);
        return true;
    }
    @Override
   public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.home:
                Intent intent =new Intent(dashboard.this,dashboard.class);
                startActivity(intent);
                return true;

            case R.id.his:
                Intent intent1 =new Intent(dashboard.this,user.class);
                startActivity(intent1);
                return true;


            case R.id.sett:
                Intent intent2 =new Intent(dashboard.this,signup.class);
                startActivity(intent2);
                return true;

            case R.id.logout:
                Intent intent3 =new Intent(dashboard.this,login.class);
                startActivity(intent3);
                return true;

            default:
                super.onOptionsItemSelected(item);

        }
        return true;
    }*/
}