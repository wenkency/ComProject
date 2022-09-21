package com.boardour.comm.ft_login;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * 其它模块调用 ，定义接口
 */
public interface ILoginProvider extends IProvider {
    boolean isLogin();

    String userName();
}
