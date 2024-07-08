package com.example.microsoftteamclone;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity4 extends AppCompatActivity {

    EditText CodeBox;
    Button joinBtn,sharebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        CodeBox = findViewById(R.id.codeBox);
        joinBtn = findViewById(R.id.joinButton);
        sharebtn = findViewById(R.id.shareButton);

        final String code = getIntent().getStringExtra("code");

        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                String shareBody = CodeBox.getText().toString();
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(intent,"Share using"));
            }
        });

        URL serverURL;

        try {
            serverURL = new URL("https://meet.jit.si") ;
            JitsiMeetConferenceOptions defaultOptions =
                    new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverURL)
                    .setWelcomePageEnabled(false)
                    .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(CodeBox.getText().toString())
                        .setWelcomePageEnabled(false)
                        .build();
                JitsiMeetActivity.launch(MainActivity4.this,options);

            }
        });

        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.logoutid:
                        FirebaseAuth.getInstance().signOut();
                        Intent logout = new Intent(MainActivity4.this, MainActivity2.class);
                        startActivity(logout);
                        finish();
                        return true;
                    case R.id.homeid:
                        Intent intent = new Intent(MainActivity4.this,MainActivity2.class);
                        startActivity(intent);
                    case R.id.chatid:
                        Intent intent1 = new Intent(MainActivity4.this,ChatActivity.class);
                        startActivity(intent1);

                }
                return false;
            }
        });
    }
}