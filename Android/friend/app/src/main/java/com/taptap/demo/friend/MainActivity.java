package com.taptap.demo.friend;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.taptap.ttos.TapFriends;
import com.taptap.ttos.utils.LogUtil;
import com.taptap.ttos.widget.ToastUtil;
import com.xd.sdkdemo.R;
import com.xuexiang.xui.adapter.simple.XUISimpleAdapter;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.popupwindow.popup.XUIListPopup;
import com.xuexiang.xui.widget.popupwindow.popup.XUIPopup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button online;
    private Button activeGameOverlay;
    private Button inviteUserToGame;
    private Button queryFriendList;
    private Button addFollow;
    private Button sdkInit;
    private Button setRichText;
    private Button clearRichText;
    private String TapTag = "LeeJiEun";
    private boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        setCallback();

        getFriendList();

    }

    private void setCallback() {
        TapFriends.setCallback(new TapFriends.TapFriendsCallback() {
            @Override
            public void onCallback(int code, String msg) {
                LogUtil.logd(" get callback code = " + code + " msg = " + msg);
                if (code == TapFriends.CALLBACK_CODE_INVITE_FRIEND) {
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
                } else if (code == TapFriends.CALLBACK_CODE_OFFLINE) {
                    ToastUtil.showMessage(MainActivity.this, "TapFriends.CALLBACK_CODE_OFFLINE");
                } else if (code == TapFriends.CALLBACK_CODE_GET_INVITE) {
                    try {
                        JSONObject jsonObject = new JSONObject(msg);
                        String xdId = jsonObject.getString("xdId");
                        TapFriends.acceptInviteSucceed(xdId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (code == TapFriends.CALLBACK_CODE_SHOW_USERINFO) {
                    LogUtil.logd(" show userInfo id = " + msg);
                    ToastUtil.showMessage(MainActivity.this, "查看好友信息 id = " + msg);
                } else if (code == TapFriends.CALLBACK_CODE_UPLOAD_FRIENDS_SUCCESS) {
                    ToastUtil.showMessage(MainActivity.this, "上传组队成员信息成功");
                } else if (code == TapFriends.CALLBACK_CODE_UPLOAD_FRIENDS_FAIL) {
                    ToastUtil.showMessage(MainActivity.this, "上传组队成员信息失败");
                } else if (code == TapFriends.CALLBACK_CODE_ADD_FRIEND_SUCCESS) {
                    ToastUtil.showMessage(MainActivity.this, "添加组队成员关注成功： " + msg);
                } else if (code == TapFriends.CALLBACK_CODE_ADD_FRIEND_FAIL) {
                    ToastUtil.showMessage(MainActivity.this, "添加组队成员关注失败： " + msg);
                } else if (code == TapFriends.CALLBACK_CODE_UPDATE_USER_SUCCESS) {
                    ToastUtil.showMessage(MainActivity.this, "更新角色信息成功");
                } else if (code == TapFriends.CALLBACK_CODE_CHAT) {
                    ToastUtil.showMessage(MainActivity.this, "私聊: " + msg);
                } else if (code == TapFriends.CALLBACK_CODE_INIT_SUCCESS) {
                    ToastUtil.showMessage(MainActivity.this, "初始化成功");
                    status = true;
                } else if (code == TapFriends.CALLBACK_CODE_INIT_FAIL) {
                    ToastUtil.showMessage(MainActivity.this, "初始化失败: " + msg);
                } else if (code == TapFriends.CALLBACK_CODE_SET_RICH_PRESENCE_SUCCESS) {
                    ToastUtil.showMessage(MainActivity.this, "富信息内容更新成功");
                } else if (code == TapFriends.CALLBACK_CODE_SET_RICH_PRESENCE_FAIL) {
                    ToastUtil.showMessage(MainActivity.this, "富信息内容更新失败: " + msg);
                } else if (code == TapFriends.CALLBACK_CODE_DELETE_FRIEND_SUCCESS) {
                    ToastUtil.showMessage(MainActivity.this, "取消关注好友成功");
                } else if (code == TapFriends.CALLBACK_CODE_DELETE_FRIEND_FAIL) {
                    ToastUtil.showMessage(MainActivity.this, "取消关注好友失败： " + msg);
                }
            }
        });
        TapFriends.setDebug(true);
    }

    private void initView() {
        //        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        online = findViewById(R.id.online);
        activeGameOverlay = findViewById(R.id.activeGameOverlay);
        inviteUserToGame = findViewById(R.id.inviteUserToGame);
        queryFriendList = findViewById(R.id.queryFriendList);
        addFollow = findViewById(R.id.addFollow);
        sdkInit = findViewById(R.id.initSDK);
        setRichText = findViewById(R.id.set_richtext);
        clearRichText = findViewById(R.id.clear_richtext);

        online.setOnClickListener(this);
        activeGameOverlay.setOnClickListener(this);
        inviteUserToGame.setOnClickListener(this);
        queryFriendList.setOnClickListener(this);
        addFollow.setOnClickListener(this);
        sdkInit.setOnClickListener(this);
        setRichText.setOnClickListener(this);
        clearRichText.setOnClickListener(this);
    }

    private void getFriendList() {
        TapFriends.queryFriendList(0, new TapFriends.QueryFriendsCallback() {
            @Override
            public void onGetFriendList(String data, int total) {
                Log.d(TapTag, "total: => " + total + "    msg : => " + data);
            }

            @Override
            public void onGetFail(int code, String msg) {
                Log.d(TapTag, "code: => " + code + "    msg : => " + msg);
            }
        });
    }

    private void initSDK() {
        if (!status) {
            TapFriends.init(this, "wxbdfbe5dbd3e3c64b", "1106148555");
            TapFriends.online("a086bc35c98fac3bc0cb94dddc0a9c72", "gameId11111", "修改hou73d" + Math.random(), "https://img.tapimg.com/market/images/dcaacad6231ac7003cf1b62b6643c450.png");
            TapFriends.getTapAvatar();
        } else {
            ToastUtil.showMessage(MainActivity.this, "初始化成功");
        }

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
            case R.id.initSDK:
                initSDK();
                break;
            case R.id.online:
                if (false == status) {
                    ToastUtil.showMessage(MainActivity.this, "请先初始化SDK哦！");
                    return;
                }
                TapFriends.online("a086bc35c98fac3bc0cb94dddc0a9c72", "gameId11111", "修改hou73d" + Math.random(), "https://img.tapimg.com/market/images/dcaacad6231ac7003cf1b62b6643c450.png");
                break;
            case R.id.activeGameOverlay:
                if (false == status) {
                    ToastUtil.showMessage(MainActivity.this, "请先初始化SDK哦！");
                    return;
                }

                String[] friendDialogList = new String[]{
                        "好友关系对话框",
                        "组队对话框",
                };

                XUISimpleAdapter friendDialogAdapter = XUISimpleAdapter.create(getBaseContext(), friendDialogList);
                XUIListPopup friendDialogListPopup = new XUIListPopup(getBaseContext(), friendDialogAdapter);
                friendDialogListPopup.create(DensityUtils.dp2px(200), DensityUtils.dp2px(150), new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (0 == i) {
                            TapFriends.activeGameOverlay(0);
                        } else {
                            TapFriends.activeGameOverlay(1);
                        }
                        friendDialogListPopup.dismiss();
                    }
                });
                friendDialogListPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
//                        inviteUserToGame.setText("显示列表浮层");
                    }
                });
                friendDialogListPopup.setAnimStyle(XUIPopup.ANIM_GROW_FROM_CENTER);
                friendDialogListPopup.setPreferredDirection(XUIPopup.DIRECTION_TOP);
                friendDialogListPopup.show(v);


                break;
            case R.id.set_richtext:
                if (!status) {
                    ToastUtil.showMessage(MainActivity.this, "请先初始化SDK哦！");
                    return;
                }
                TapFriends.setRichPresence("display", "战斗中");
                break;
            case R.id.clear_richtext:
                if (!status) {
                    ToastUtil.showMessage(MainActivity.this, "请先初始化SDK哦！");
                    return;
                }
                TapFriends.clearRichPresence("战斗中");
                break;
            case R.id.inviteUserToGame:
                if (!status) {
                    ToastUtil.showMessage(MainActivity.this, "请先初始化SDK哦！");
                    return;
                }


                String[] listItems = new String[]{
                        "好友列表邀请",
                        "Local",
                        "Remote",
                };

                XUISimpleAdapter adapter = XUISimpleAdapter.create(getBaseContext(), listItems);
                XUIListPopup mListPopup = new XUIListPopup(getBaseContext(), adapter);
                mListPopup.create(DensityUtils.dp2px(200), DensityUtils.dp2px(150), new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            TapFriends.inviteToGame("199949426", "Friendly UserId ");
                        } else if (i == 1) {
                            TapFriends.inviteToGame("local", "Local ");
                        } else {
                            TapFriends.inviteToGame("remote", "Remote ");
                        }
                        mListPopup.dismiss();
                    }
                });
                mListPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
//                        inviteUserToGame.setText("显示列表浮层");
                    }
                });
                mListPopup.setAnimStyle(XUIPopup.ANIM_GROW_FROM_CENTER);
                mListPopup.setPreferredDirection(XUIPopup.DIRECTION_TOP);
//                Drawable drawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.popupwindow_bg);
//                mListPopup.setBackgroundDrawable(drawable);
                mListPopup.show(v);


                break;
            case R.id.queryFriendList:
                if (!status) {
                    ToastUtil.showMessage(MainActivity.this, "请先初始化SDK哦！");
                    return;
                }
                String[] FriendsListItems = new String[]{
                        "查询粉丝",
                        "查询关注好友",
                        "黑名单",
                        "互相关注",
                        "查询部分好友关系",
                };

                XUISimpleAdapter FriendsListAdapter = XUISimpleAdapter.create(getBaseContext(), FriendsListItems);
                XUIListPopup friendListPopup = new XUIListPopup(getBaseContext(), FriendsListAdapter);
                friendListPopup.create(DensityUtils.dp2px(200), DensityUtils.dp2px(150), new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        0 表示粉丝，1 表示关注 2 表示黑名单 10 表示互相关注
                        if (i == 0) {
                            TapFriends.queryFriendListByType(0, 0, new TapFriends.QueryFriendsCallback() {
                                @Override
                                public void onGetFriendList(String data, int total) {
                                    Log.d(TapTag, "total: => " + total + "    msg : => " + data);
                                }

                                @Override
                                public void onGetFail(int code, String msg) {
                                    Log.d(TapTag, "code: => " + code + "    msg : => " + msg);
                                }
                            });

                        } else if (i == 1) {
                            TapFriends.queryFriendListByType(1, 0, new TapFriends.QueryFriendsCallback() {
                                @Override
                                public void onGetFriendList(String data, int total) {
                                    Log.d(TapTag, "total: => " + total + "    msg : => " + data);
                                }

                                @Override
                                public void onGetFail(int code, String msg) {
                                    Log.d(TapTag, "code: => " + code + "    msg : => " + msg);
                                }
                            });
                        } else if (i == 2) {
                            TapFriends.queryFriendListByType(2, 0, new TapFriends.QueryFriendsCallback() {
                                @Override
                                public void onGetFriendList(String data, int total) {
                                    Log.d(TapTag, "total: => " + total + "    msg : => " + data);
                                }

                                @Override
                                public void onGetFail(int code, String msg) {
                                    Log.d(TapTag, "code: => " + code + "    msg : => " + msg);
                                }
                            });
                        } else if (i == 3) {
                            TapFriends.queryFriendListByType(10, 0, new TapFriends.QueryFriendsCallback() {
                                @Override
                                public void onGetFriendList(String data, int total) {
                                    Log.d(TapTag, "total: => " + total + "    msg : => " + data);
                                }

                                @Override
                                public void onGetFail(int code, String msg) {
                                    Log.d(TapTag, "code: => " + code + "    msg : => " + msg);
                                }
                            });
                        } else if (i == 4) {
                            List<String> queryFriendRelation = new ArrayList<String>();
                            queryFriendRelation.add("199949426");
                            queryFriendRelation.add("67726813");
                            TapFriends.queryUserRelations(queryFriendRelation, new TapFriends.QueryFriendsCallback() {
                                @Override
                                public void onGetFriendList(String data, int total) {
                                    Log.d(TapTag, "total: => " + total + "    data : => " + data);
                                    XUIPopup mNormalPopup = new XUIPopup(getBaseContext());
                                    TextView textView = new TextView(getBaseContext());
                                    textView.setLayoutParams(mNormalPopup.generateLayoutParam(
                                            DensityUtils.dp2px(getBaseContext(), 250),
                                            WRAP_CONTENT
                                    ));
                                    textView.setLineSpacing(DensityUtils.dp2px(4), 1.0f);
                                    int padding = DensityUtils.dp2px(20);
                                    textView.setPadding(padding, padding, padding, padding);
                                    textView.setText(data);
                                    textView.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.xui_btn_gray_normal_color));
                                    mNormalPopup.setContentView(textView);
                                    mNormalPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                        @Override
                                        public void onDismiss() {

                                        }
                                    });
                                    mNormalPopup.setAnimStyle(XUIPopup.ANIM_GROW_FROM_CENTER);
                                    mNormalPopup.setPreferredDirection(XUIPopup.DIRECTION_TOP);
                                    mNormalPopup.show(v);
                                }

                                @Override
                                public void onGetFail(int code, String msg) {
                                    ToastUtil.showMessage(MainActivity.this, "查询失败： " + msg);
                                }
                            });

                        }
                        friendListPopup.dismiss();
                    }
                });
                friendListPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
//                        inviteUserToGame.setText("显示列表浮层");
                    }
                });
                friendListPopup.setAnimStyle(XUIPopup.ANIM_GROW_FROM_CENTER);
                friendListPopup.setPreferredDirection(XUIPopup.DIRECTION_TOP);
                friendListPopup.show(v);
                break;
            case R.id.addFollow:
                if (!status) {
                    ToastUtil.showMessage(MainActivity.this, "请先初始化SDK哦！");
                    return;
                }

                String[] addFriendsListItems = new String[]{
                        "取消关注",
                        "关注好友",
                };
                XUISimpleAdapter addFrienAdapter = XUISimpleAdapter.create(getBaseContext(), addFriendsListItems);
                XUIListPopup addFriendListPopup = new XUIListPopup(getBaseContext(), addFrienAdapter);
                addFriendListPopup.create(DensityUtils.dp2px(200), DensityUtils.dp2px(150), new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            // 先取消该好友的关注，然后才可以测试关注好友的功能
                            TapFriends.deleteFollow("199949426");
                        } else if (i == 1) {
                            TapFriends.addFollow("199949426");
                        }
                        addFriendListPopup.dismiss();
                    }
                });
                addFriendListPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
//                        inviteUserToGame.setText("显示列表浮层");
                    }
                });
                addFriendListPopup.setAnimStyle(XUIPopup.ANIM_GROW_FROM_CENTER);
                addFriendListPopup.setPreferredDirection(XUIPopup.DIRECTION_TOP);
                addFriendListPopup.show(v);

                break;
        }
    }

    private void showToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
    }
}