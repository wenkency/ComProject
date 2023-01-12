package com.boardour.home

import android.app.Activity
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import cn.carhouse.adapter.XQuickAdapter
import cn.carhouse.adapter.XQuickSupport
import cn.carhouse.adapter.XQuickViewHolder
import cn.carhouse.views.banner.BannerPagerAdapter
import cn.carhouse.views.banner.BannerView
import com.boardour.net.CollectPresenter
import com.boardour.comm.RouterHelper
import com.boardour.comm.bean.IViewType
import com.boardour.comm.ft_login.UserViewModel
import com.boardour.home.bean.ArticleBean
import com.boardour.home.bean.BannerBean
import com.boardour.home.bean.BannerItem
import com.boardout.home.R


/**
 * 首页RecyclerView的适配器
 */
class HomeAdapter(private val owner: LifecycleOwner, activity: Activity) :
    XQuickAdapter<IViewType>(activity) {
    // 多条目定义
    private val support = object : XQuickSupport<IViewType>() {
        // 2种类型
        override fun getViewTypeCount(): Int {
            // 大于2就行
            return 100
        }

        override fun getLayoutId(item: IViewType, position: Int): Int {
            // Banner
            if (item.getItemViewType() == 1) {
                return R.layout.home_banner
            } else if (item.getItemViewType() == 2) {
                // 文章列表
                return R.layout.home_article_item
            }
            return com.boardout.comm.R.layout.comm_item_empty
        }

        override fun getItemViewType(item: IViewType, position: Int): Int {
            return item.getItemViewType()
        }

    }

    // 轮播图片
    private var bannerView: BannerView<BannerItem>? = null

    init {
        // 设置多条目
        setMultiSupport(support)
    }

    override fun convert(holder: XQuickViewHolder, item: IViewType, position: Int) {
        if (item is BannerBean) {
            setBannerData(holder, item)
        } else if (item is ArticleBean) {
            setArticleData(holder, item)
        }
    }

    // 设置Banner数据
    private fun setBannerData(holder: XQuickViewHolder, item: BannerBean) {
        bannerView = holder.getView(R.id.banner_view)
        // 如果设置过Adapter就不设置了
        if (bannerView?.adapter != null) {
            bannerView?.startRoll()
            return
        }
        val adapter =
            object : BannerPagerAdapter<BannerItem>(item.items, R.layout.home_banner_item) {
                override fun convert(holder: XQuickViewHolder, bean: BannerItem, position: Int) {
                    val view = holder.getView<View>(R.id.iv_icon)
                    holder.displayImage(
                        R.id.iv_icon,
                        bean.imagePath,
                        view.width,
                        view.height
                    )
                    holder.setOnClickListener {
                        RouterHelper.toWeb(appActivity, bean.url)
                    }

                }
            }
        bannerView?.adapter = adapter
    }

    // 设置文章数据
    private fun setArticleData(holder: XQuickViewHolder, item: ArticleBean) {
        holder.setText(
            R.id.tv_author,
            if (item.author.isNullOrEmpty()) {
                item.shareUser
            } else {
                item.author
            }
        )
        holder.setText(R.id.tv_title, Html.fromHtml(item.title))
        holder.setText(R.id.tv_chapterName, "${item.superChapterName}/${item.chapterName}")
        holder.setText(R.id.tv_niceDate, item.niceDate)
        if (item.collect) {
            holder.setImageResource(R.id.iv_collect, R.drawable.home_collect_selector)
        } else {
            holder.setImageResource(R.id.iv_collect, R.drawable.home_collect)
        }
        // 点击事件
        holder.setOnClickListener {
            RouterHelper.toWeb(appActivity, item.link)
        }
        // 收藏
        holder.setOnClickListener(R.id.iv_collect) {
            // 判断有没有登录
            if (UserViewModel.isLogin.value == false) {
                Toast.makeText(appActivity, "请先登录!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            CollectPresenter.collect.observe(owner) {
                if (it == true) {
                    item.collect = true
                    notifyDataSetChanged()
                }
            }
            CollectPresenter.unCollect.observe(owner) {
                if (it == true) {
                    item.collect = false
                    notifyDataSetChanged()
                }
            }
            if (item.collect) {
                CollectPresenter.unCollect(appActivity, item.id)
            } else {
                CollectPresenter.collect(appActivity, item.id)
            }

        }
    }

    fun startRoll() {
        bannerView?.startRoll()

    }

    fun stopRoll() {
        bannerView?.stopRoll()
    }
}