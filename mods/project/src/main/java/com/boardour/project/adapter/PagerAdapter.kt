package com.boardour.project.adapter

import android.app.Activity
import android.text.Html
import android.util.ArrayMap
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.carhouse.adapter.XQuickAdapter
import cn.carhouse.adapter.XQuickPagerAdapter
import cn.carhouse.adapter.XQuickViewHolder
import com.boardour.comm.RouterHelper
import com.boardour.comm.utils.DensityUtils
import com.boardour.project.ProjectViewModel
import com.boardour.project.bean.ArticleBean
import com.boardour.project.bean.ArticleListBean
import com.boardout.project.R
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

class PagerAdapter(
    private val activity: Activity,
    owner: LifecycleOwner,
    private val viewModel: ProjectViewModel
) : XQuickPagerAdapter<ArticleBean>(
    null, R.layout.project_item_view_pager, false
) {
    private val holderMap = ArrayMap<Int, XQuickViewHolder>()
    private val listMap = ArrayMap<Int, ArticleListBean>()

    init {
        viewModel.articleListData.observe(owner) { item ->
            listMap[item.position] = item
            // 要做一些事
            setData(holderMap[item.position]!!, item, item.position)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        super.destroyItem(container, position, obj)
        // 清空数据
        listMap[position] = null
        val holder = holderMap[position]!!
        val recyclerView: RecyclerView = holder.getView(R.id.recycler_view)
        if (recyclerView.adapter != null) {
            val adapter = recyclerView.adapter as XQuickAdapter<ArticleBean>
            adapter.clear()
        }
    }

    fun setData(holder: XQuickViewHolder, item: ArticleListBean, position: Int) {
        val recyclerView: RecyclerView = holder.getView(R.id.recycler_view)
        if (recyclerView.adapter == null) {
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = object : XQuickAdapter<ArticleBean>(
                activity,
                R.layout.project_article_item
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
                    holder.setText(R.id.tv_title, Html.fromHtml(item.title))

                    holder.setText(R.id.tv_desc, Html.fromHtml(item.desc))

                    holder.setText(R.id.tv_niceDate, item.niceDate)
                    // 点击事件
                    holder.setOnClickListener {
                        RouterHelper.toWeb(appActivity, item.link)
                    }
                    // 显示圆角图片
                    holder.displayRadiusImage(
                        R.id.iv_icon,
                        item.envelopePic,
                        DensityUtils.dp2px(2.0f)
                    )
                }
            }
        }

        val adapter = recyclerView.adapter as XQuickAdapter<ArticleBean>

        if (item.curPage == 1) {
            adapter.clear()
        }
        adapter.addAll(item.datas)


        // 下拉刷新 加载更多
        val smartRefresh: SmartRefreshLayout = holder.getView(R.id.smart_refresh)
        smartRefresh.setEnableLoadMore(item.curPage < item.pageCount)
        smartRefresh.finishRefresh()
        smartRefresh.finishLoadMore()
    }

    override fun convert(holder: XQuickViewHolder, data: ArticleBean, position: Int) {
        holderMap[position] = holder

        // 下拉刷新 加载更多
        val smartRefresh: SmartRefreshLayout = holder.getView(R.id.smart_refresh)

        smartRefresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                listMap[position] = null
                // 请求数据
                viewModel.requestProjectList(position, 1, mData[position].id)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                // 请求数据
                val item = listMap[position]
                if (item != null) {
                    viewModel.requestProjectList(position, item.curPage + 1, mData[position].id)
                }

            }

        })

    }

    fun onPageSelected(position: Int) {
        // 先看成缓存，如果有，就不请求数据了
        val item = listMap[position]
        if (item != null) {
            return
        }
        // 请求数据
        // viewModel.requestProjectList(position, 1, mData[position].id)
        val holder = holderMap[position]!!
        val smartRefresh: SmartRefreshLayout = holder.getView(R.id.smart_refresh)
        smartRefresh.autoRefresh()
    }

}