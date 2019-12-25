package com.jiangxk.kotlinMediaCommon.ui.fragment

import android.app.Activity
import com.jiangxk.kotlinMediaCommon.common.BaseApplication
import com.jiangxk.kotlinMediaCommon.injection.component.DaggerActivityComponent
import com.jiangxk.kotlinMediaCommon.injection.module.ActivityModule
import com.jiangxk.kotlinMediaCommon.mvp.presenter.BasePresenter
import com.jiangxk.kotlinMediaCommon.mvp.view.BaseView
import javax.inject.Inject

/**
 * @desc
 * @auth jiangxk
 * @time 2019-10-16  17:45
 */
abstract class BaseMvpFragment<T : BasePresenter> : BaseFragment(), BaseView {

    @Inject
    lateinit var mPresenter: T

    lateinit var mActivityComponent: DaggerActivityComponent

    override fun initOperate() {
        initActivityInjection()
        injectComponent()
    }

    /*注册依赖关系*/
    abstract fun injectComponent()

    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder()
            .appComponent((activity?.application as BaseApplication).appComponent)
            .activityModule(ActivityModule(activity as Activity))
            .build() as DaggerActivityComponent
    }

    override fun showEmpty() {

    }

    override fun showError() {

    }
}