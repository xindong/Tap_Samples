package com.taptap.demo.friend;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.taptap.ttos.TapFriends;
import com.taptap.ttos.utils.LogUtil;
import com.taptap.ttos.widget.ToastUtil;
import com.xd.sdkdemo.R;

import org.json.JSONException;
import org.json.JSONObject;

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
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

        initSDK();
    }

    private void initSDK() {
        TapFriends.init(this, "wxbdfbe5dbd3e3c64b", "1106148555");
        TapFriends.online("a086bc35c98fac3bc0cb94dddc0a9c72", "gameId11111", "修改hou73d"+ Math.random(), "https://img.tapimg.com/market/images/dcaacad6231ac7003cf1b62b6643c450.png");
        TapFriends.getTapAvatar();
        TapFriends.setCallback(new TapFriends.TapFriendsCallback() {
            @Override
            public void onCallback(int code, String msg) {
                LogUtil.logd(" get callback code = " + code + " msg = " + msg);
                if (code == 2000) {
                    TapFriends.inviteUserToGame(msg, "demoExtra");
                } else if (code == TapFriends.CALLBACK_CODE_INVITE_OTHER) {
                    if (!msg.equals("world")) {
                        TapFriends.inviteToGame(msg, "extra");
                    }
                } else if (code == TapFriends.CALLBACK_CODE_ONLINE) {
                    LogUtil.logd(" online: " + code + " msg = " + msg);
                    if (msg.equals("online")) {
                        TapFriends.setLobbyName("绝地城堡");
                    }
                }else if(code == TapFriends.CALLBACK_CODE_OFFLINE){
                } else if (code == TapFriends.CALLBACK_CODE_GET_INVITE) {
                    try {
                        JSONObject jsonObject = new JSONObject(msg);
                        String xdId = jsonObject.getString("xdId");
                        TapFriends.acceptInviteSucceed(xdId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else if(code == TapFriends.CALLBACK_CODE_SHOW_USERINFO){
                    LogUtil.logd(" show userInfo id = " + msg);
                    ToastUtil.showMessage(MainActivity.this,"查看好友信息 id = " + msg);
                }else if(code == TapFriends.CALLBACK_CODE_UPLOAD_FRIENDS_SUCCESS){
                    ToastUtil.showMessage(MainActivity.this,"上传组队成员信息成功");
                }else if(code == TapFriends.CALLBACK_CODE_UPLOAD_FRIENDS_FAIL){
                    ToastUtil.showMessage(MainActivity.this,"上传组队成员信息失败");
                }else if(code == TapFriends.CALLBACK_CODE_ADD_FRIEND_SUCCESS){
                    ToastUtil.showMessage(MainActivity.this,"添加组队成员关注成功");
                }else if(code == TapFriends.CALLBACK_CODE_UPDATE_USER_SUCCESS){
                    ToastUtil.showMessage(MainActivity.this,"更新角色信息成功");
                }else if(code == TapFriends.CALLBACK_CODE_CHAT){
                    ToastUtil.showMessage(MainActivity.this,"私聊:" + msg);
                }
            }
        });
        TapFriends.setDebug(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TapFriends.checkConnection();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.online:
                TapFriends.online("a086bc35c98fac3bc0cb94dddc0a9c72", "gameId11111", "修改hou73d"+ Math.random(), "https://img.tapimg.com/market/images/dcaacad6231ac7003cf1b62b6643c450.png");
                break;
            case R.id.activeGameOverlay:
                TapFriends.activeGameOverlay(1);
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