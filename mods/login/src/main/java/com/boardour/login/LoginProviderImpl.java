package com.boardour.login;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.boardour.comm.RouterPath;
import com.boardour.comm.ft_login.ILoginProvider;

@Route(path = RouterPath.LOGIN_PROVIDER)
public class LoginProviderImpl implements ILoginProvider {
    @Override
    public void init(Context context) {

    }

    @Override
    public boolean isLogin() {
        return true;
    }

    @Override
    public String userName() {
        return "lven";
    }
}
