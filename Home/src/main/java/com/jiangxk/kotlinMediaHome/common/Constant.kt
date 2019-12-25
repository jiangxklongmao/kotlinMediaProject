package com.jiangxk.kotlinMediaHome.common

/**
 * @desc 全局公共参数
 * @auth jiangxk
 * @time 2019-10-17  16:19
 */
class Constant {
    companion object {
        /**
         * 标题类型
         */
        const val ITEM_TYPE_TITLE = 0
        /**
         * 图片类型
         */
        const val ITEM_TYPE_IMAGE = 1


        /*刷新的状态*/
        const val STATE_REFRESH = 2
        const val CATEGORY = "category"
        const val CATEGORY_NEW = 1
        const val CATEGORY_MOVIE = 2
        const val CATEGORY_DANISH = 3
        const val CATEGORY_ZINGY = 4


        /* json缓存Key*/
        const val KEY_JSON = "json"
    }
}