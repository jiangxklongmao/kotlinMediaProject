package com.jiangxk.kotlinMediaHome.entity

/**
 * @desc
 * @auth jiangxk
 * @time 2019-10-31  11:53
 */
data class VideoData(
    val VideoIcon: String,//影视图片
    val name: String, //影视名字
    val type: String, //影视类型
    val playUrl: String //播放URL
)