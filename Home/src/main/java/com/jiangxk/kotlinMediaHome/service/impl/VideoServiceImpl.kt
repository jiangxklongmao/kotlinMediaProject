package com.jiangxk.kotlinMediaHome.service.impl

import android.text.TextUtils
import com.google.gson.Gson
import com.jiangxk.kotlinMediaCommon.common.BaseConstant
import com.jiangxk.kotlinMediaCommon.utils.AppPrefsUtils
import com.jiangxk.kotlinMediaHome.common.Constant
import com.jiangxk.kotlinMediaHome.entity.BannerData
import com.jiangxk.kotlinMediaHome.entity.VideoData
import com.jiangxk.kotlinMediaHome.entity.VideoItemData
import com.jiangxk.kotlinMediaHome.entity.VideoListData
import com.jiangxk.kotlinMediaHome.service.VideoService
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.orhanobut.logger.Logger
import org.jsoup.Jsoup
import javax.inject.Inject

/**
 * @desc 解析数据源：http://www.vip-free.com
 * @auth jiangxk
 * @time 2019-10-31  11:21
 */
class VideoServiceImpl @Inject constructor() : VideoService {

    override fun getVideoList(callBack: VideoService.GetVideoCallBack) {
        val json = AppPrefsUtils.getString(Constant.KEY_JSON)
        if (!TextUtils.isEmpty(json)) {
            callBack.onVideoDataSuccess(Gson().fromJson(json, VideoListData::class.java))
        } else {
            OkGo.get<String>(BaseConstant.BASE_URL)
                .tag(this)
                .execute(object : StringCallback() {
                    override fun onSuccess(response: Response<String>) {
                        val jsonStr = response.body().toString()
                        Logger.i("首页数据:$jsonStr")

                        val document = Jsoup.parse(jsonStr)
                        //轮播图
                        val e1 = document.select("div.hy-video-slide")
                        val banners: MutableList<BannerData> = ArrayList()
                        for (element in e1) {
                            val subE = element.selectFirst("a[href]")
                            val title = subE.attr("title")
                            Logger.i("轮播图数据:title = $title")
                            if (!title.contains("伦理") && !title.contains("老司机")) {
                                var link = subE.attr("href")
                                val style = subE.attr("style")
                                val img =
                                    style.substring(style.indexOf("url") + 4, style.indexOf(")"))
                                link = if (link[0] == '/') link else "/$link"
                                Logger.i("轮播图：link = $link")
                                banners.add(BannerData(title, img, link))
                            }
                        }
                        //影视列表中的分类 新片，电视剧，综艺等
                        val types: MutableList<String> = ArrayList()
                        for (typeElement in document.select("h3[class=margin-0]")) {
                            val tag = typeElement.text()
                            types.add(tag)
                            Logger.i("分类标签: $tag")
                        }

                        //新片抢先看数据源已不存在
                        var videoList: MutableList<VideoItemData> = ArrayList()


                        val titleTypes = arrayOf(
                            Constant.CATEGORY_MOVIE,
                            Constant.CATEGORY_DANISH,
                            Constant.CATEGORY_ZINGY, 0
                        )

                        //其他分类
                        var index = 0
                        for (elementParent in document.select("div[class=hy-layout clearfix]")) {
                            val itemData: MutableList<VideoData> = ArrayList()
                            for (element in elementParent.select("div[class=clearfix] > div")) {
                                val subE = element.selectFirst("a[href]")
                                val tagElement = element.selectFirst("span[class=score]")
                                val tag = if (tagElement == null) "" else tagElement.text()
                                val title = subE.attr("title")
                                val href = subE.attr("href")
                                val link = "/$href"
                                val img = subE.attr("src")
                                itemData.add(VideoData(img, title, tag, link))
                            }
                            if (itemData.size >= 3) {
                                //添加标题
                                val itemTitle = VideoItemData(titleTypes[index])
                                itemTitle.type = Constant.ITEM_TYPE_TITLE
                                itemTitle.title = types[index]
                                videoList.add(itemTitle)
                                //添加内容
                                itemData.forEach {
                                    videoList.add(
                                        VideoItemData(
                                            it.type,
                                            Constant.ITEM_TYPE_IMAGE,
                                            it.name,
                                            it.VideoIcon,
                                            it.playUrl
                                        )
                                    )
                                }
                                videoList.removeAt(videoList.size - 1)
                            }
                        }
                        val data = VideoListData(banners, videoList)
                        val jsonData = Gson().toJson(data)
                        AppPrefsUtils.putString(Constant.KEY_JSON, jsonData)
                        Logger.i(jsonData)
                        callBack.onVideoDataSuccess(data)
                    }
                })
        }
    }
}