package com.taptap.demo.friend;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button online;
    private Button activeGameOverlay;
    private Button inviteUserToGame;
    private Button queryFriendList;
    private Button addFollow;
    private Button queryFriendListByType;
    private String TAG = "friend demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        online = findViewById(R.id.online);
        activeGameOverlay = findViewById(R.id.activeGameOverlay);
        inviteUserToGame = findViewById(R.id.inviteUserToGame);
        queryFriendList = findViewById(R.id.queryFriendList);
        addFollow = findViewById(R.id.addFollow);
        queryFriendListByType = findViewById(R.id.queryFriendListByType);

        online.setOnClickListener(this);
        activeGameOverlay.setOnClickListener(this);
        inviteUserToGame.setOnClickListener(this);
        queryFriendList.setOnClickListener(this);
        addFollow.setOnClickListener(this);
        queryFriendListByType.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.online:

                break;
            case R.id.activeGameOverlay:

                break;
            case R.id.inviteUserToGame:

                break;
            case R.id.queryFriendList:

                break;
            case R.id.addFollow:

                break;
            case R.id.queryFriendListByType:

                break;


        }
    }
    private void showToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
    }
}