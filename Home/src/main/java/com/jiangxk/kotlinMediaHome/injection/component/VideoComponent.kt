package com.jiangxk.kotlinMediaHome.injection.component

import com.jiangxk.kotlinMediaCommon.injection.component.ActivityComponent
import com.jiangxk.kotlinMediaCommon.injection.scope.PerComonentScope
import com.jiangxk.kotlinMediaHome.injection.module.VideoModule
import com.jiangxk.kotlinMediaHome.ui.fragment.HomeFragment
import dagger.Component

/**
 * @desc
 * @auth jiangxk
 * @time 2019-10-31  14:57
 */
@PerComonentScope
@Component(modules = [VideoModule::class], dependencies = [ActivityComponent::class])
interface VideoComponent {
    fun inject(fragment: HomeFragment)
}