package com.jiangxk.kotlinMediaCommon.injection.module

import android.content.Context
import com.jiangxk.kotlinMediaCommon.common.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @desc
 * @auth jiangxk
 * @time 2019-10-16  18:44
 */
@Module
class AppModule(private val context: BaseApplication) {
    @Singleton
    @Provides
    fun provideContext(): Context {
        return this.context
    }
}