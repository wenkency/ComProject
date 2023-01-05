package cn.carhouse.web;

import android.webkit.JavascriptInterface;

import cn.carhouse.web.event.JSEvent;

public class WebEvent extends JSEvent {

    @JavascriptInterface
    @Override
    public void openImage(String imageUrl) {
    }
}
