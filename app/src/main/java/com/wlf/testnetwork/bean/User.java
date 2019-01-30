package com.wlf.testnetwork.bean;

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

public class User extends BaseBean {

    private User() {}

    private volatile static User mSingleton = null;

    public static User getInstance() {
        if (mSingleton == null) {
            synchronized (User.class) {
                if (mSingleton == null) {
                    mSingleton = new User();
                }
            }
        }
        return mSingleton;
    }

    public String token;
    public String userId;
    public String accessId;
    public String merchantId;

    public void setUserInstance (User user){
        User.getInstance().userId = user.userId;
        User.getInstance().token = user.token;
        User.getInstance().accessId = user.accessId;
        User.getInstance().merchantId = user.merchantId;
    }
}
