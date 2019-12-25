package com.jiangxk.kotlinMediaHome.component

import com.jiangxk.provider.component.IApplicationLike
import com.jiangxk.provider.component.Router
import com.jiangxk.provider.service.HomeService

/**
 * @desc 插件自动加载该类，实现服务注册
 * @auth jiangxk
 * @time 2019-10-31  17:16
 */
class HomeApplicationLike : IApplicationLike {
    override fun registered() {
        Router.addService(HomeService::class.java.simpleName, HomeServiceImpl())
    }

    override fun unregistered() {
        Router.removeService(HomeService::class.java.simpleName)
    }
}