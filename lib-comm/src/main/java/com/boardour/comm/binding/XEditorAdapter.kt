package com.boardour.comm.binding

import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import cn.carhouse.views.XEditLayout

/**
 * 自定义一些功能，也可以参考:TextViewBindingAdapter
 */
object XEditorAdapter {
    @JvmStatic
    @BindingAdapter(value = ["editText"], requireAll = false)
    fun setEditText(et: XEditLayout, text: String?) {
        if (text == null) {
            return
        }
        et.editText = text
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "editText")
    fun getEditText(et: XEditLayout): String? {
        return et.editText
    }

    @JvmStatic
    @BindingAdapter("editTextAttrChanged")
    fun editTextChange(et: XEditLayout, attrChange: InverseBindingListener) {
        et.editContent.addTextChangedListener {
            if (attrChange != null) {
                attrChange.onChange()
            }
        }

    }
}