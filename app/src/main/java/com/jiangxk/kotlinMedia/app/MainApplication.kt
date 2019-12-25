package com.jiangxk.kotlinMedia.app

import com.jiangxk.kotlinMediaCommon.common.BaseApplication
import com.tencent.smtt.sdk.QbSdk

/**
 * @desc app - Application
 * @auth jiangxk
 * @time 2019-10-31  17:27
 */
class MainApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        //初始化腾讯X5-WebView
        QbSdk.initX5Environment(this, null)
    }
}