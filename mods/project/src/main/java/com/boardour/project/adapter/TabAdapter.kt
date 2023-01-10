package com.boardour.project.adapter

import android.content.Context
import android.graphics.Color
import android.text.Html
import android.view.View
import android.view.ViewGroup
import cn.carhouse.views.adapter.XCommAdapter
import cn.carhouse.views.adapter.XViewHolder
import com.boardour.project.bean.ArticleBean
import com.boardout.project.R

/**
 * TAB适配器
 */
class TabAdapter(context: Context) : XCommAdapter<ArticleBean>(context, R.layout.project_item_tab) {

    override fun convert(holder: XViewHolder, item: ArticleBean, position: Int) {
        holder.setText(R.id.tv_name, Html.fromHtml(item.name))
        // 默认设置
        convertTabReset(holder, item, position)
    }

    override fun convertTabReset(holder: XViewHolder, item: ArticleBean, position: Int) {
        holder.setTextColor(R.id.tv_name, Color.parseColor("#666666"))
    }

    override fun convertTabSelected(holder: XViewHolder, item: ArticleBean, position: Int) {
        holder.setTextColor(R.id.tv_name, Color.parseColor("#ff0000"))
    }

    // 底部下划线
    override fun getTabBottomLineView(parent: ViewGroup): View {
        val view = View(parent.context)
        // 这里可以设置自己的宽高：动态宽高（适配屏幕的）
        view.setBackgroundColor(Color.parseColor("#ff0000"))
        return view
    }
}