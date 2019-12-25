package com.jiangxk.kotlinMedia.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.jiangxk.kotlinMedia.R
import com.jiangxk.kotlinMediaCommon.ui.activity.BaseActivity
import com.jiangxk.kotlinMediaCommon.ui.fragment.BaseFragment
import com.jiangxk.provider.component.Router
import com.jiangxk.provider.service.HomeService
import kotlinx.android.synthetic.main.app_activity_main.*
import java.util.*

class MainActivity : BaseActivity() {

    private var mHomeFragment: BaseFragment? = null
    private var clickTime: Long = 0
    private val mStack = Stack<BaseFragment>()

    override fun isSetPaddingTop(): Boolean = false

    companion object {

        private const val STATE_BAR_POSITION = 1

        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int = R.layout.app_activity_main

    override fun onBackPressed() {
        exit()
    }


    override fun initView() {
        initFragment()
    }

    override fun initData() {
    }

    override fun setListener() {

        bottomBar.setOnTabSelectListener {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) //显示状态栏
            when (it) {
                R.id.tab_home -> {
                    changeFragment(0)
                }
            }
        }
    }

    /**
     * 初始化 Fragment
     */
    private fun initFragment() {
        val bt = supportFragmentManager.beginTransaction()
        val homeService = Router.getService(HomeService::class.java.simpleName)
        homeService?.let {
            mHomeFragment = (homeService as HomeService).getHomeFragment()
            mHomeFragment?.let {
                bt.add(R.id.rootLay, it)
                mStack.add(it)
            }
        }

        if (!mStack.isEmpty()) {
            bt.commit()
        }
    }

    /**
     * 切换Fragment显示
     * @param position Int
     */
    private fun changeFragment(position: Int) {
        if (position >= mStack.size) {
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            window.decorView.systemUiVisibility = if (position == STATE_BAR_POSITION) {
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            } else {
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }

        val bt = supportFragmentManager.beginTransaction()
        mStack.forEach {
            bt.hide(it)
        }
        bt.show(mStack[position])
        bt.commit()
    }

    /**
     * 连续按两下退出程序
     */
    private fun exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show()
            clickTime = System.currentTimeMillis()
        } else {
            this.finish()
        }
    }

}
