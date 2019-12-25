package com.jiangxk.kotlinMedia.ui

import android.os.Build
import android.os.CountDownTimer
import android.view.View
import com.jiangxk.kotlinMedia.R
import com.jiangxk.kotlinMediaCommon.ui.activity.BaseActivity

/**
 * @desc
 * @auth jiangxk
 * @time 2019-10-31  17:29
 */
class SplashActivity : BaseActivity() {


    companion object {
        private const val TIME_COUNTDOWN: Long = 3 * 1000
    }

    override fun isSetPaddingTop() = false

    override fun getLayoutId(): Int = R.layout.app_activity_splash

    override fun initView() {}

    override fun initData() {
        //开启倒计时
        object : CountDownTimer(1000, TIME_COUNTDOWN) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                MainActivity.start(context)
                finish()
            }
        }.start()
    }

    override fun initOperate() {
        hideBottomUIMenu()
    }

    private fun hideBottomUIMenu() {
        when {
            Build.VERSION.SDK_INT in 12..18 -> {
                val view = window.decorView
                view.systemUiVisibility = View.GONE
            }
            Build.VERSION.SDK_INT >= 19 -> {
                val decorView = window.decorView
                val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

                decorView.systemUiVisibility = uiOptions
            }
        }
    }

}