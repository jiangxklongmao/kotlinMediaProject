package com.jiangxk.kotlinMediaHome.mvp.contract

import com.jiangxk.kotlinMediaCommon.mvp.presenter.BasePresenter
import com.jiangxk.kotlinMediaCommon.mvp.view.BaseView
import com.jiangxk.kotlinMediaHome.entity.VideoListData

/**
 * @desc
 * @auth jiangxk
 * @time 2019-10-17  14:25
 */
interface VideoListContract {
    interface View : BaseView {
        /**
         * 显示视频列表
         * @param data VideoListData 数据集合
         */
        fun showViewList(data: VideoListData)
    }

    interface Presenter : BasePresenter {
        fun getVideoList(isLoading: Boolean)
    }
}