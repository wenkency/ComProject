package cn.carhouse.web;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.AppActivity;
import com.boardour.comm.RouterPath;
import com.boardout.web.R;
import com.tencent.smtt.sdk.WebView;

import cn.carhouse.titlebar.DefTitleBar;
import cn.carhouse.web.bean.WebData;
import cn.carhouse.web.event.IEvent;
import cn.carhouse.web.init.WebChromeClientImpl;
import cn.carhouse.web.init.WebViewInitImpl;
import cn.carhouse.web.utils.WebConfig;

/**
 * 封装的WebActivity
 */
@Route(path = RouterPath.WEB)
public class WebActivity extends AppActivity implements WebChromeClientImpl.OnWebChromeListener {
    private FrameLayout mFlWebContainer;
    private WebViewInitImpl mWebViewInitializer;
    private WebView mWebView;
    private ProgressWebView mProgressWebView;

    /**
     * 打开页面传递过来的参数
     */
    @Autowired(name = RouterPath.WEB_DATA)
    public WebData mWebData;
    // 扩展用吧
    @Autowired
    public String url;
    @Autowired
    public String title;

    @Override
    public int getLayoutId() {
        return R.layout.web_activity_web;
    }

    @Override
    public void initTitle(DefTitleBar titleBar) {
        if (mWebData != null && !TextUtils.isEmpty(mWebData.title)) {
            titleBar.setTitle(mWebData.title);
        }
    }

    @Override
    public void initData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // 注入传递过来的数据
        ARouter.getInstance().inject(this);
        // 获取上个页面传递过来的数据
        if (!TextUtils.isEmpty(url) && mWebData == null) {
            mWebData = new WebData();
            mWebData.title = title;
            mWebData.url = url;
        }
        // WebView初始化对象
        mWebViewInitializer = new WebViewInitImpl(this);
        // WebView初始化
        initWebView();
    }


    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        if (mWebView != null) {
            mWebView.removeAllViews();
        } else {
            mProgressWebView = new ProgressWebView(this);
            mWebView = mWebViewInitializer.initWebView(mProgressWebView.getWebView());
            mWebView.setWebViewClient(mWebViewInitializer.initWebViewClient());
            mWebView.setWebChromeClient(mWebViewInitializer.initWebChromeClient());
            // 注入JS交互
            IEvent event = WebConfig.getInstance().getEvent();
            if (event != null) {
                String jsName = WebConfig.getInstance().getJSName();
                mWebView.addJavascriptInterface(event, jsName);
            }
        }
    }

    @Override
    public void initViews() {
        mFlWebContainer = findViewById(R.id.fl_web_container);
        mWebViewInitializer.setOnWebChromeListener(this);
        if (mFlWebContainer.getChildCount() > 0) {
            mFlWebContainer.removeAllViews();
        }
        mFlWebContainer.addView(mProgressWebView);
        // 加载URL
        if (mWebView != null && mWebData != null) {
            // 跳转并进行页面加载
            WebUtils.getInstance().loadPage(mWebView, mWebData.url);
        }
    }

    // 标题回调
    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (getTitleBar() != null) {
            if (mWebData != null && !TextUtils.isEmpty(mWebData.title)) {
                return;
            }
            getTitleBar().setTitle(title + "");
        }
    }

    // 页面加载进度回调
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (mProgressWebView == null) {
            return;
        }
        ProgressBar progressbar = mProgressWebView.getProgressbar();
        if (progressbar == null) {
            return;
        }
        if (newProgress == 100) {
            progressbar.setVisibility(View.GONE);
        } else {
            if (progressbar.getVisibility() == View.GONE) {
                progressbar.setVisibility(View.VISIBLE);
            }
            progressbar.setProgress(newProgress);
        }
    }


    @Override
    public void onPause() {
        if (mWebView != null) {
            mWebView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (mWebView != null) {
            mWebView.onResume();
        }
        super.onResume();
    }


    @Override
    public void onDestroy() {
        if (mFlWebContainer != null && mWebView != null) {
            mWebView = null;
            mProgressWebView = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mWebView != null && (keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack(); // 浏览网页历史记录 goBack()和goForward()
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 处理相机相册选择
        if (mWebViewInitializer != null) {
            mWebViewInitializer.onActivityResult(requestCode, resultCode, data);
        }
    }

}
