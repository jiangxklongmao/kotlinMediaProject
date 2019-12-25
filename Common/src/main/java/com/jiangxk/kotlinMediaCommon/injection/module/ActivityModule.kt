package com.jiangxk.kotlinMediaCommon.injection.module

import android.app.Activity
import com.jiangxk.kotlinMediaCommon.injection.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * @desc
 * @auth jiangxk
 * @time 2019-10-16  18:59
 */
@Module
class ActivityModule(private val activity: Activity) {
    @ActivityScope
    @Provides
    fun provideActivity(): Activity {
        return this.activity
    }
}