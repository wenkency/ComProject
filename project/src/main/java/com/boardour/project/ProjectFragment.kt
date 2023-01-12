package com.boardour.project

import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import cn.carhouse.views.tab.XTabLayout
import com.base.BaseFragment
import com.base.utils.ViewModelUtils
import com.boardour.project.adapter.PagerAdapter
import com.boardour.project.adapter.TabAdapter
import com.boardout.project.R


class ProjectFragment : BaseFragment() {

    private lateinit var viewModel: ProjectViewModel
    private lateinit var tabAdapter: TabAdapter
    private lateinit var pagerAdapter: PagerAdapter

    override fun getLayoutId(): Int {
        return R.layout.project_fragment
    }

    override fun initData() {
        viewModel = ViewModelUtils.getViewModel(this, ProjectViewModel::class.java)

    }

    override fun initViews() {
        val tabLayout = findViewById<XTabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager>(R.id.view_pager)


        // ViewPager设置
        pagerAdapter = PagerAdapter(getAppActivity(), this, viewModel)
        viewPager.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                pagerAdapter.onPageSelected(position)
            }
        })
        viewPager.adapter = pagerAdapter
        // 防止销毁，性能差点，但不影响
        viewPager.offscreenPageLimit = 3
        // TAB设置
        tabAdapter = TabAdapter(getAppActivity())
        tabLayout.setAdapter(tabAdapter, viewPager, 0)


        // 这里是Tab数据监听
        viewModel.articleData.observe(this) {
            tabAdapter.replaceAll(it)
            pagerAdapter.replaceAll(it)

            viewPager.post {
                pagerAdapter.onPageSelected(0)
            }
        }
        viewModel.finish.observe(this) {
            dismissDialog()
        }

    }

    override fun initNet() {
        showDialog()
        viewModel.requestProject()
    }
}