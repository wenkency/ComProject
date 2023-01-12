package com.boardour.user

import android.text.Html
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.carhouse.adapter.XQuickAdapter
import cn.carhouse.adapter.XQuickViewHolder
import cn.carhouse.titlebar.DefTitleBar
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.AppActivity
import com.boardour.comm.RouterHelper
import com.boardour.comm.RouterPath
import com.boardour.comm.utils.DensityUtils
import com.boardour.net.callback.NetCallback
import com.boardour.user.bean.ArticleBean
import com.boardour.user.bean.ArticleListBean
import com.boardout.user.R
import com.retrofit.RxPresenter
import com.retrofit.core.RestClient
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 * 收藏页面
 */
@Route(path = RouterPath.COLLECT)
class CollectActivity : AppActivity() {
    private lateinit var refresh: SmartRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: XQuickAdapter<ArticleBean>
    private var curPage = 0
    override fun getLayoutId(): Int {
        return R.layout.user_activity_collect
    }

    override fun isNeedLoading(): Boolean {
        return false
    }

    override fun initTitle(titleBar: DefTitleBar) {
        titleBar.setTitle("收藏")
    }

    override fun initViews() {
        refresh = findViewById(R.id.smart_refresh)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = object : XQuickAdapter<ArticleBean>(
            this,
            R.layout.user_article_item
        ) {
            override fun convert(holder: XQuickViewHolder, item: ArticleBean, position: Int) {
                holder.setText(
                    R.id.tv_author,
                    if (item.author.isNullOrEmpty()) {
                        item.shareUser
                    } else {
                        item.author
                    }
                )
                // 格式化Html文本 Html.fromHtml
                // holder.setText(R.id.tv_title, Html.fromHtml(item.title))

                holder.setText(R.id.tv_desc, Html.fromHtml(item.title))

                holder.setText(R.id.tv_niceDate, item.niceDate)
                // 点击事件
                holder.setOnClickListener {
                    RouterHelper.toWeb(appActivity, item.link)
                }
                // 显示圆角图片
                /*holder.displayRadiusImage(
                    R.id.iv_icon,
                    item.envelopePic,
                    DensityUtils.dp2px(2.0f)
                )*/
            }
        }
        recyclerView.adapter = adapter


        refresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                curPage = 0
                requestCollect()
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                // 请求数据
                requestCollect()
            }

        })
    }

    override fun initNet() {
        requestCollect()
    }

    fun requestCollect() {
        // 收藏接口测试-- cookie
        val url = "/lg/collect/list/$curPage/json"
        RxPresenter.get(this, url,
            object : NetCallback<ArticleListBean>() {
                override fun onSucceed(data: ArticleListBean, client: RestClient) {
                    if (curPage == 0) {
                        adapter.clear()
                    }
                    if (data.datas.isNotEmpty()) {
                        adapter.addAll(data.datas)
                    }
                    curPage = data.curPage
                    refresh.setEnableLoadMore(data.curPage < data.pageCount)
                }

                override fun onAfter() {
                    refresh.finishRefresh()
                    refresh.finishLoadMore()
                }

            })
    }

}