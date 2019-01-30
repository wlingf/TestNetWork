package com.wlf.testnetwork.network;

import com.yanzhenjie.kalle.simple.SimpleResponse;

/**
 * =============================================
 *
 * @author: wlf
 * @date: 2019/1/30
 * @eamil: 845107244@qq.com
 * 描述:
 * 备注:
 * =============================================
 */

public abstract class NetWorkCallBack<T> {

    public abstract void onSuccess(T t, String body, SimpleResponse response);

    public abstract void onError(String msg, String msgCode, SimpleResponse response);

    public void onCache(T t, String body, SimpleResponse response) {}

    public void onNetError(String msg, Exception e) {}

    public void onFinish() {}

    public void onStart() {}

    public void onCancel() {}
}
