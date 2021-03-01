package com.taptap.demo.anti_addiction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.taptap.antisdk.AntiAddictionCore;
import com.taptap.antisdk.AntiAddictionKit;


public class MainActivity extends Activity implements View.OnClickListener {
    private Button checkAnti;
    private Button remainTime;
    /**
     * 0-未实名游客 1：8岁以下 ，2：8-15岁，  3：16-17岁， 4：不需要防沉迷 5：未实名正式用户
     */
    private Button userType;
    private String TAG = "anti demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAnti = findViewById(R.id.checkanti);
        remainTime = findViewById(R.id.remain);
        userType = findViewById(R.id.userType);
        userType.setOnClickListener(this);
        checkAnti.setOnClickListener(this);
        remainTime.setOnClickListener(this);
        AntiAddictionKit.init(this, protectCallBack);
        AntiAddictionKit.checkState();

    }

    @Override
    protected void onResume() {
        super.onResume();
        AntiAddictionKit.setDebug(true);
        AntiAddictionKit.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AntiAddictionKit.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkanti:
                AntiAddictionKit.init(this, protectCallBack);
                AntiAddictionKit.checkState();
                break;
            case R.id.remain:
                int time = AntiAddictionCore.getCurrentUser().getRemainTime();
                showToast("剩余游戏时常："+time+" 秒");
                break;
            case R.id.userType:
                int type = AntiAddictionCore.getCurrentUser().getAccountType();
                showToast("玩家类型："+type);
        }
    }

    private AntiAddictionKit.AntiAddictionCallback protectCallBack = new AntiAddictionKit.AntiAddictionCallback() {
        @Override
        public void onAntiAddictionResult(int i, String s) {
            Log.e(TAG, "code:" + i + ". msg:" + s);
            switch (i) {
                case 1030:
                    //TODO 时常耗尽 用户在线时长到达防沉迷限制
                    break;
                case 1040:
                    //checkState通过，可以开始游戏
                    break;
                case 1050:
                    //TODO 触发宵禁 用户游戏时间进入防沉迷宵禁时间段
                    break;
            }
        }
    };

    private void showToast(String msg){
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
    }
}