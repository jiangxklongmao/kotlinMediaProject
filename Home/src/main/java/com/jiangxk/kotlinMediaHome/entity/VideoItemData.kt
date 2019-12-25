package com.jiangxk.kotlinMediaHome.entity

import com.jiangxk.kotlinMediaCommon.ui.adapter.IMultilItemEntity
import com.jiangxk.kotlinMediaHome.common.Constant

/**
 * @desc
 * @auth jiangxk
 * @time 2019-10-17  16:18
 */
data class VideoItemData(
    var tagName: String = "",   //视频标签
    var type: Int = Constant.ITEM_TYPE_IMAGE,  //item类型
    var videoName: String,   //视频名字
    var videoImg: String,   //视频图片
    var videoLink: String,   //视频链接
    var title: String = "",   //标题
    var titleType: Int = -1
) : IMultilItemEntity {
    override fun getItemType(): Int {
        return this.type
    }

    constructor(titleType: Int) : this("", 0, "", "", "", "", titleType)
}