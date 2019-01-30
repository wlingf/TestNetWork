package com.wlf.testnetwork.contact;

import android.app.Application;

import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.KalleConfig;
import com.yanzhenjie.kalle.OkHttpConnectFactory;
import com.yanzhenjie.kalle.connect.BroadcastNetwork;
import com.yanzhenjie.kalle.connect.http.LoggerInterceptor;
import com.yanzhenjie.kalle.simple.cache.CacheStore;
import com.yanzhenjie.kalle.simple.cache.DiskCacheStore;

import java.util.concurrent.TimeUnit;

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

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initKalle();
    }

    void initKalle () {
        CacheStore cacheStore = DiskCacheStore.newBuilder("/sdcard/db")
                .password("jipai")
                .build();
        KalleConfig config = KalleConfig.newBuilder()
                //全局的连接超时时间
                .connectionTimeout(60000, TimeUnit.MILLISECONDS)
                //全局的写入超时时间
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                //全局统一缓存模式
                .cacheStore(cacheStore)
                //全局网络监测
                .network(new BroadcastNetwork(this))
                //全局网络请求底层框架
                .connectFactory(OkHttpConnectFactory.newBuilder().build())
                //全局cookie
//                .cookieStore()
                //全局拦截器
                .addInterceptor(new LoggerInterceptor("kalle", true))
                //全局转换器
//                .converter(new HttpConverter())
                .build();
        Kalle.setConfig(config);
    }
}
