package com.jiangxk.kotlinMediaHome.entity

/**
 * @desc 首页 - top-轮播图数据
 * @auth jiangxk
 * @time 2019-10-17  16:10
 */
data class VideoListData(
    val bannerUrls: List<BannerData>,    // 轮播图
    val videoList: List<VideoItemData>
)
