package com.boardour.comproject

import android.content.Context
import android.graphics.Color
import cn.carhouse.views.adapter.XCommAdapter
import cn.carhouse.views.adapter.XViewHolder

class MainTabAdapter(context: Context, data: List<MainTabBean>, layoutId: Int) :
    XCommAdapter<MainTabBean>(context, data, layoutId) {
    override fun convert(holder: XViewHolder, item: MainTabBean, position: Int) {
        // 默认设置
        convertTabReset(holder, item, position)
    }

    // 选中设置
    override fun convertTabSelected(
        holder: XViewHolder,
        item: MainTabBean,
        position: Int
    ) {
        holder.setTextColor(R.id.tv_name, Color.parseColor("#333333"))
        holder.setImageResource(R.id.iv_icon, item.selectIcon)
    }

    override fun convertTabReset(
        holder: XViewHolder,
        item: MainTabBean,
        position: Int
    ) {
        holder.setTextColor(R.id.tv_name, Color.parseColor("#666666"))
        holder.setText(R.id.tv_name, item.name)
        holder.setImageResource(R.id.iv_icon, item.icon)
    }
}