package com.wlf.testnetwork.network;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.wlf.testnetwork.bean.BaseBean;
import com.wlf.testnetwork.bean.User;
import com.wlf.testnetwork.network.gsonutil.GosnUtil;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.Params;
import com.yanzhenjie.kalle.simple.Callback;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

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

public class NetWorkManager {

    private static final String SUCCESS_CODE = "000000";

    public static <T extends BaseBean> void post (@NonNull Context context, @NonNull String url, @NonNull HashMap<String, String> map, @NonNull NetWorkCallBack<T> callBack) {
        post(context, url, mapTransformParam(map), callBack);
    }

    public static <T extends BaseBean> void post (@NonNull final Context context, @NonNull String url, @NonNull Params params, @NonNull final NetWorkCallBack<T> callBack) {
        Kalle.post(url)
                .tag(context)
                .addHeader("token", isNull(User.getInstance().token))
                .params(params)
                .perform(new Callback<String, String>() {
                    @Override
                    public void onStart() {
                        callBack.onStart();
                    }

                    @Override
                    public void onEnd() {
                        callBack.onFinish();
                    }

                    @Override
                    public void onCancel() {
                        callBack.onCancel();
                    }

                    @Override
                    public void onException(Exception e) {
                        callBack.onNetError("网络异常", e);
                    }

                    @Override
                    public void onResponse(SimpleResponse<String, String> response) {
                        if (response.isSucceed()){
                            try{
                                String body = response.succeed().toString();
                                JSONObject json = new JSONObject(body);
                                String repBody = json.optString("REP_BODY");
                                JSONObject obj = new JSONObject(repBody);
                                Type genType = callBack.getClass().getGenericSuperclass();
                                Class<T> entityClass = (Class<T>)((ParameterizedType)genType).getActualTypeArguments()[0];
                                T t = GosnUtil.parse(repBody, entityClass);
                                if (t != null) {
                                    if (SUCCESS_CODE.equals(obj.optString("RSPCOD"))){
                                        if (response.fromCache()){
                                            callBack.onCache(t, repBody, response);
                                        }
                                        callBack.onSuccess(t, repBody, response);
                                    }else {
                                        String msg = obj.optString("RSPMSG");
                                        String msdCode = obj.optString("RSPCOD");
                                        callBack.onError(msg, msdCode, response);
                                    }
                                }else {
                                    callBack.onError("数据解析出错", "-1000", null);
                                }
                            } catch (ClassCastException e){
                                e.printStackTrace();
                                callBack.onError("数据解析出错", "-1000", null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                callBack.onError("数据解析出错", "-1000", null);
                            }
                        }else {
                            callBack.onError("服务器请求失败，请联系客服", "-10001", null);
                        }
                    }
                });
    }

    public static void cancel (Context context) {
        Kalle.cancel(context);
    }

    private static Params mapTransformParam(Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("sysName", "android");
        //android
        map.put("sysType", "1");
        //系统版本
        map.put("sysVersion", Build.VERSION.RELEASE);
        //APP版本
        map.put("appVersion", "appVersion");
        //设备uuid
        map.put("sysTerNo", "sysTerNo");
        //请求日期
        map.put("txnDate", "txnDate");
        //请求时间
        map.put("txnTime", "txnTime");
        //手机型号
        map.put("sysBrand", Build.MODEL);
        //oem商ID
        map.put("oemId", "100059");
        //App编码
        map.put("appCode", "1000591");
        //当前登录用户ID
        map.put("userId", isNull(User.getInstance().userId));
        //当前登录用户商户ID
        map.put("merchantId", isNull(User.getInstance().merchantId));
        //用户登录后请求令牌
        map.put("token", isNull(User.getInstance().token));
        //接入商编号
        map.put("accessId", isNull(User.getInstance().accessId));

        HashMap<String, Object> signMap = new HashMap<>();
        String sign = MD5Util.generateParams(GosnUtil.parse(map));
        signMap.put("SIGN", sign);

        HashMap<String, Object> data = new HashMap<>();
        data.put("REQ_BODY", map);
        data.put("REQ_HEAD", signMap);

        Params params = Params.newBuilder()
                .add("REQ_MESSAGE", GosnUtil.parse(data))
                .build();
        return params;
    }

    private static String isNull (String s) {
        return TextUtils.isEmpty(s) || "null".equals(s) ? "" : s;
    }
}
