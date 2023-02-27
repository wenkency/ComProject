package com.boardour.navi

import androidx.recyclerview.widget.LinearLayoutManager
import cn.carhouse.adapter.XQuickAdapter
import cn.carhouse.adapter.XQuickViewHolder
import cn.carhouse.views.adapter.XCommAdapter
import cn.carhouse.views.adapter.XViewHolder
import com.base.BindFragment
import com.base.utils.FragmentUtils
import com.boardour.comm.RouterHelper
import com.boardour.navi.bean.ArticleBean
import com.boardour.navi.bean.NaviBean
import com.boardout.navi.R
import com.boardout.navi.databinding.NaviFragmentBinding

/**
 * 导航页面
 */
class NaviFragment : BindFragment<NaviViewModel, NaviFragmentBinding>() {
    private lateinit var adapter: XQuickAdapter<NaviBean>
    private lateinit var flowAdapter: XCommAdapter<ArticleBean>

    // 右边选中位置
    private var curPosition = -1
    override fun getLayoutId(): Int {
        return R.layout.navi_fragment
    }

    override fun isNeedLoading(): Boolean {
        return false
    }

    // 绑定ViewModel
    override fun onBind(binding: NaviFragmentBinding, viewModel: NaviViewModel) {
        binding.vm = viewModel
    }

    override fun initViews() {
        // 左边条目相关设置
        val layoutManager = LinearLayoutManager(getAppActivity())
        binding.recyclerView.layoutManager = layoutManager
        adapter = object : XQuickAdapter<NaviBean>(getAppActivity(), R.layout.navi_item) {
            var selectPosition = 0
            override fun convert(holder: XQuickViewHolder, item: NaviBean, position: Int) {
                holder.setText(R.id.tv_name, item.name)
                // 改变背景
                holder.setSelected(R.id.tv_name, position == selectPosition)
                holder.setOnClickListener {
                    selectPosition = position
                    notifyDataSetChanged()
                    // 滚动设置
                    scrollToPosition(layoutManager, position)
                }

                // 设置右边数据
                if (position == selectPosition) {
                    changeRightData(item, position)
                }
            }
        }
        binding.recyclerView.adapter = adapter

        // 右边流式布局相关设置
        flowAdapter =
            object : XCommAdapter<ArticleBean>(getAppActivity(), R.layout.navi_flow_item) {
                override fun convert(holder: XViewHolder, item: ArticleBean, position: Int) {
                    holder.setText(R.id.tv_name, item.title)
                    holder.setOnClickListener {
                        RouterHelper.toWeb(getAppActivity(), item.link)
                    }
                }

            }
        binding.flowLayout.adapter = flowAdapter

        // 数据监听
        viewModel.articleData.observe(this) {
            adapter.replaceAll(it)
        }
    }


    private fun changeRightData(item: NaviBean, position: Int) {
        if (curPosition == position) {
            return
        }
        curPosition = position
        flowAdapter.replaceAll(item.articles)
    }

    fun scrollToPosition(layoutManager: LinearLayoutManager, position: Int) {
        // 滚动到中间的代码
        // val half = binding.recyclerView.measuredHeight / 2
        // layoutManager.scrollToPositionWithOffset(position, half)
        // 滚动
        if (position > 3) {
            layoutManager.scrollToPositionWithOffset(position - 3, 0)
        } else {
            layoutManager.scrollToPositionWithOffset(0, 0)
        }

    }

    override fun initNet() {
        viewModel.requestNavi(dialogState)
    }

    override fun onDestroy() {
        super.onDestroy()
        FragmentUtils.removeFragment(parentFragmentManager,this)
    }
}