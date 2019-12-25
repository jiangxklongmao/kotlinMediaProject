package com.jiangxk.kotlinMediaHome.injection.module

import com.jiangxk.kotlinMediaHome.mvp.contract.VideoListContract
import com.jiangxk.kotlinMediaHome.service.VideoService
import com.jiangxk.kotlinMediaHome.service.impl.VideoServiceImpl
import dagger.Module
import dagger.Provides

/**
 * @desc Dagger -- VideoModule
 * @auth jiangxk
 * @time 2019-10-31  14:54
 */
@Module
class VideoModule(private val view: VideoListContract.View) {

    @Provides
    fun provideVideoService(videoService: VideoServiceImpl): VideoService {
        return videoService
    }

    @Provides
    fun provideView(): VideoListContract.View {
        return this.view
    }

}