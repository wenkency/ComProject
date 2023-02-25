package com.boardour.login

import android.view.View
import android.widget.Toast
import cn.carhouse.titlebar.DefTitleBar
import cn.carhouse.views.XEditLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.AppActivity
import com.base.utils.ViewModelUtils
import com.boardour.comm.RouterHelper
import com.boardour.comm.RouterPath
import com.boardour.comm.ft_login.UserViewModel
import com.boardout.login.R

/**
 * 登录页面
 */
@Route(path = RouterPath.LOGIN)
class LoginActivity : AppActivity() {
    private val viewModel: LoginViewModel by lazy {
        ViewModelUtils.getViewModel(this, LoginViewModel::class.java)
    }
    private lateinit var etName: XEditLayout
    private lateinit var etPass: XEditLayout

    override fun getLayoutId(): Int = R.layout.activity_login
    override fun initTitle(titleBar: DefTitleBar) {
        titleBar.setTitle("登录页面")
    }

    // 传参
    /*   @Autowired
       @JvmField
       var userName: String? = null*/

    override fun initData() {
        //ARouter.getInstance().inject(this)  // Start auto inject.

        //val user = UserBean("lven")
        //MMKVUtils.putObject("user", user)

        /* LoginViewModel.user.observe(this) {
             //Log.e("TAG", it.username)
         }*/
        UserViewModel.isLogin.observe(this) {
            if (it) {
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        UserViewModel.isLogin.removeObservers(this)
    }

    override fun initViews() {
        etName = findViewById(R.id.et_name)
        etPass = findViewById(R.id.et_pass)
    }

    // 登录
    fun login(view: View) {
        val name = etName.editText
        val pass = etPass.editText
        if (name.isNullOrEmpty()) {
            Toast.makeText(this, etName.editContent.hint, Toast.LENGTH_SHORT).show()
            return
        }
        if (pass.isNullOrEmpty()) {
            Toast.makeText(this, etPass.editContent.hint, Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.login(this, name, pass)
    }

    // 去注册
    fun toRegister(view: View) {
        RouterHelper.toRegister(this)
    }
}