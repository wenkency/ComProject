package com.boardour.comm.ft_login;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boardour.comm.RouterPath;

/**
 * 封装一层，方便其它模块调用，没什么
 */
public class LoginProvider {
    private static LoginProvider instance = new LoginProvider();

    public static LoginProvider getInstance() {
        return instance;
    }

    @Autowired(name = RouterPath.LOGIN_PROVIDER)
    public ILoginProvider loginProvider;

    private LoginProvider() {
        //初始化LoginService
        ARouter.getInstance().inject(this);
    }
}
