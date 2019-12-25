package com.jiangxk.kotlinMediaHome.component

import com.jiangxk.kotlinMediaCommon.ui.fragment.BaseFragment
import com.jiangxk.kotlinMediaHome.ui.fragment.HomeFragment
import com.jiangxk.provider.service.HomeService

/**
 * @desc Home模块 实现类
 * @auth jiangxk
 * @time 2019-10-31  17:19
 */
class HomeServiceImpl : HomeService {
    override fun getHomeFragment(): BaseFragment {
        return HomeFragment()
    }
}