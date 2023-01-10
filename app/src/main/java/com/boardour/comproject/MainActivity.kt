package com.boardour.comproject

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import cn.carhouse.adapter.XFragmentPagerAdapter
import cn.carhouse.titlebar.DefTitleBar
import cn.carhouse.titlebar.utils.TitleBarUtil
import cn.carhouse.views.tab.XTabLayout
import com.base.AppActivity
import com.boardour.home.HomeFragment
import com.boardour.navi.NaviFragment
import com.boardour.project.ProjectFragment
import com.boardour.user.UserFragment

/**
 * 主页面
 */
class MainActivity : AppActivity() {
    // 底部tab数据
    private val tabData = arrayListOf<MainTabBean>()

    // ViewPager对应显示的Fragment
    private val fragments = arrayListOf<Fragment>()

    // 加载布局
    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initTitle(titleBar: DefTitleBar) {
        titleBar.setTitle("玩Android")
        // 隐藏返回按钮
        titleBar.clearBackImage()
    }


    // 初始化数据
    override fun initData() {
        // 设置透明
        TitleBarUtil.statusBarTrans(this)
        // 底部Tab数据
        tabData.add(MainTabBean("首页", R.drawable.home, R.drawable.home_selector))
        tabData.add(MainTabBean("导航", R.drawable.article, R.drawable.article_selector))
        tabData.add(MainTabBean("项目", R.drawable.project, R.drawable.project_selector))
        tabData.add(MainTabBean("我的", R.drawable.user, R.drawable.user_selector))
        // ViewPager数据
        fragments.add(HomeFragment())
        fragments.add(NaviFragment())
        fragments.add(ProjectFragment())
        fragments.add(UserFragment())

    }


    override fun initViews() {
        val tabLayout = findViewById<XTabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        val pagerAdapter = XFragmentPagerAdapter(supportFragmentManager, fragments)
        viewPager.adapter = pagerAdapter
        // 底部TAB
        val tabAdapter = MainTabAdapter(this, tabData, R.layout.item_tab_layout)
        // 与ViewPager关联
        tabLayout.setAdapter(tabAdapter, viewPager, 0)

        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    titleBar?.setTitle("玩Android")
                    return
                }
                titleBar?.setTitle(tabData[position].name)
            }
        })
    }

    // 不需要加载页面
    override fun isNeedLoading(): Boolean {
        return false
    }

    // 退出没有动画
    override fun isFinishActivityAnim(): Boolean {
        return false
    }
}