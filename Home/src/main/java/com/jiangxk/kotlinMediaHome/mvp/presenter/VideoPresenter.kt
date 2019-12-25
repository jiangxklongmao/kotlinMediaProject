package com.jiangxk.kotlinMediaHome.mvp.presenter

import com.jiangxk.kotlinMediaHome.entity.VideoListData
import com.jiangxk.kotlinMediaHome.mvp.contract.VideoListContract
import com.jiangxk.kotlinMediaHome.service.VideoService
import javax.inject.Inject

/**
 * @desc HomeFragment-MVP-Presenter
 * @auth jiangxk
 * @time 2019-10-17  14:24
 */
class VideoPresenter @Inject constructor() : VideoListContract.Presenter, VideoService.GetVideoCallBack {

    @Inject
    lateinit var videoService: VideoService
    private var isLoading = false

    @Inject
    lateinit var mView: VideoListContract.View

    override fun getVideoList(isLoading: Boolean) {
        this.isLoading = isLoading
        if (isLoading) mView.showLoading()
        videoService.getVideoList(this)
    }

    override fun onVideoDataSuccess(data: VideoListData) {
        mView.showViewList(data)
        if (isLoading) mView.showContent()
    }


}