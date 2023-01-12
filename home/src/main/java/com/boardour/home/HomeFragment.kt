package com.boardour.home

import androidx.recyclerview.widget.LinearLayoutManager
import com.base.BindFragment
import com.boardour.comm.bean.IViewType
import com.boardour.comm.ft_login.UserViewModel
import com.boardour.home.bean.BannerBean
import com.boardout.home.R
import com.boardout.home.databinding.HomeFragmentBinding
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 * 首页
 */
class HomeFragment : BindFragment<HomeViewModel, HomeFragmentBinding>() {

    private val homeData = arrayListOf<IViewType>()
    private var bannerBean: BannerBean? = null
    private lateinit var homeAdapter: HomeAdapter
    private var pageNum = 0
    private var pageCount = 0


    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }


    override fun onBind(binding: HomeFragmentBinding, viewModel: HomeViewModel) {
        // 绑定
        binding.vm = viewModel
    }

    override fun initViews() {
        // RecyclerView设置
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(getAppActivity())
        homeAdapter = HomeAdapter(this, getAppActivity())
        recyclerView.adapter = homeAdapter

        // 1. Banner数据监听
        viewModel.bannerData.observe(this) {
            if (it == null || it.isEmpty()) {
                return@observe
            }
            bannerBean = BannerBean(it)
            homeData.add(0, bannerBean!!)

            // 2. 请求文章列表数据
            viewModel.requestArticleList(pageNum)

        }
        // 文章列表数据监听
        viewModel.articleData.observe(this) {
            pageNum = it.curPage
            pageCount = it.pageCount
            binding.smartRefresh.setEnableLoadMore(pageNum < pageCount)

            homeData.addAll(it.datas)
            homeAdapter.replaceAll(homeData)
        }
        // 网络完成监听
        viewModel.finishRequest.observe(this) {
            binding.smartRefresh.finishRefresh()
            binding.smartRefresh.finishLoadMore()
        }
        // 下拉刷新 加载更多
        binding.smartRefresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                homeData.clear()
                pageNum = 0
                pageCount = 0
                // 请求Banner数据后 -- 请求列表数据
                viewModel.requestBanner(pageState)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                // 请求列表数据
                viewModel.requestArticleList(pageNum)
            }
        })

        // 监听登录状态
        UserViewModel.isLogin.observe(this) {
            if (it == null) {
                return@observe
            }
            initNet()
        }
    }

    override fun initNet() {
        // 调用下拉刷新
        binding.smartRefresh.autoRefresh()
        //viewModel.requestBanner()
    }

    // 控制滚动
    override fun onFragmentVisible(isVisible: Boolean) {
        if (isVisible) {
            homeAdapter.startRoll()
        } else {
            homeAdapter.stopRoll()
        }
    }

}