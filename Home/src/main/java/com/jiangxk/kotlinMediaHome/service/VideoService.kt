package com.jiangxk.kotlinMediaHome.service

import com.jiangxk.kotlinMediaHome.entity.VideoListData

/**
 * @desc 视频数据接口
 * @auth jiangxk
 * @time 2019-10-17  16:47
 */
interface VideoService {
    interface GetVideoCallBack {
        fun onVideoDataSuccess(data: VideoListData)
    }

    fun getVideoList(callBack: GetVideoCallBack)
}