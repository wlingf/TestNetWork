package com.wlf.testnetwork;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wlf.testnetwork.bean.BaseBean;
import com.wlf.testnetwork.bean.User;
import com.wlf.testnetwork.network.NetWorkCallBack;
import com.wlf.testnetwork.network.NetWorkManager;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        request();
    }

    void request() {
        HashMap<String, String> map = new HashMap<>(16);
        map.put("userMobile", "111111");
        map.put("userPwd", "111111");
        NetWorkManager.post(this, "", map, new NetWorkCallBack<User>() {
            @Override
            public void onSuccess(User user, String body, SimpleResponse response) {
                user.setUserInstance(user);
                Log.e("TAG", "userId=" + User.getInstance().userId);
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String msg, String msgCode, SimpleResponse response) {
                Log.e("TAG", "msg:" + msg + "---" + "code" + msgCode);
            }
        });
    }

    public void getData(View v) {
        NetWorkManager.post(this, "", new HashMap<String, String>(16), new NetWorkCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean bean, String body, SimpleResponse response) {
                Toast.makeText(MainActivity.this, "请求成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String msg, String msgCode, SimpleResponse response) {
                Log.e("TAG", "msg:" + msg + "---" + "code" + msgCode);
            }
        });
    }
}
