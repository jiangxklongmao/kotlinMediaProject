package com.jiangxk.kotlinMediaHome.ui.adapter

import android.content.Context
import com.jiangxk.kotlinMediaCommon.ui.adapter.BaseMultiAdapter
import com.jiangxk.kotlinMediaCommon.ui.adapter.BaseViewHolder
import com.jiangxk.kotlinMediaHome.R
import com.jiangxk.kotlinMediaHome.common.Constant.Companion.ITEM_TYPE_IMAGE
import com.jiangxk.kotlinMediaHome.common.Constant.Companion.ITEM_TYPE_TITLE
import com.jiangxk.kotlinMediaHome.entity.VideoItemData

/**
 * @desc 首页Fragment - 视频列表Adapter
 * @auth jiangxk
 * @time 2019-10-18  11:38
 */
class VideoListAdapter(context: Context) : BaseMultiAdapter<VideoItemData>(context) {

    init {
        addItemType(ITEM_TYPE_TITLE, R.layout.home_item_home_video_list_title)
        addItemType(ITEM_TYPE_IMAGE, R.layout.home_item_home_video_list_img)
    }

    override fun onBindItemHolder(holder: BaseViewHolder, position: Int) {
        val itemData = mDatas[position]
        when (itemData.getItemType()) {
            ITEM_TYPE_TITLE -> bindImageItem(holder, itemData)
            ITEM_TYPE_IMAGE -> bindTitleItem(holder, itemData)
        }
    }

    /**
     * 绑定图片布局数据
     * @param holder BaseViewHolder
     * @param itemData VideoItemData
     */
    private fun bindImageItem(holder: BaseViewHolder, itemData: VideoItemData) {
        holder.apply {
            setImageUrl(R.id.img_home_item_video, itemData.videoImg)
            setText(R.id.tv_home_item_title, itemData.videoName)
            setText(R.id.tvVideoTag, itemData.tagName)
        }
    }

    /**
     * 绑定标题布局数据
     * @param holder BaseViewHolder
     * @param itemData VideoItemData
     */
    private fun bindTitleItem(holder: BaseViewHolder, itemData: VideoItemData) {
        holder.setText(R.id.tv_home_item_title, itemData.title)
    }

}