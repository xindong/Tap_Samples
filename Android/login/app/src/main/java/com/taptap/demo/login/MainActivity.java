package com.taptap.demo.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.taptap.sdk.CallBackManager;
import com.taptap.sdk.LoginManager;
import com.taptap.sdk.LoginResponse;
import com.taptap.sdk.RegionType;
import com.taptap.sdk.TapTapLoginCallback;
import com.taptap.sdk.TapTapSdk;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private CallBackManager callBackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login.findViewById(R.id.login);

        TapTapSdk.LoginSdkConfig config = new TapTapSdk.LoginSdkConfig();
        config.roundCorner = false;
        config.regionType = RegionType.CN;//标识为国际版，从2.5版本才开始支持
        TapTapSdk.sdkInitialize(getApplicationContext(), "client id", config);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提供带config的初始化方法
                //提供不带config的初始化方法，此时会开启圆角
                //TapTapSdk.sdkInitialize(getApplicationContext(), "client id");
                callBackManager = CallBackManager.Factory.create();
                LoginManager.getInstance().registerCallback(callBackManager, new TapTapLoginCallback<LoginResponse>() {
                    @Override
                    public void onSuccess(LoginResponse loginResult) {
                        // TODO:登录成功
                    }

                    @Override
                    public void onCancel() {
                        // TODO:用户取消
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        // TODO:登录失败
                    }
                });
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, TapTapSdk.SCOPE_PUIBLIC_PROFILE);
            }
        });
    }



}