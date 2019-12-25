package com.jiangxk.kotlinMediaHome.ui.fragment

import android.view.LayoutInflater
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.jiangxk.kotlinMediaCommon.ext.gridInit
import com.jiangxk.kotlinMediaCommon.ext.player
import com.jiangxk.kotlinMediaCommon.ui.fragment.BaseMvpFragment
import com.jiangxk.kotlinMediaCommon.utils.AppPrefsUtils
import com.jiangxk.kotlinMediaHome.R
import com.jiangxk.kotlinMediaHome.common.Constant
import com.jiangxk.kotlinMediaHome.entity.BannerData
import com.jiangxk.kotlinMediaHome.entity.VideoItemData
import com.jiangxk.kotlinMediaHome.entity.VideoListData
import com.jiangxk.kotlinMediaHome.injection.component.DaggerVideoComponent
import com.jiangxk.kotlinMediaHome.injection.module.VideoModule
import com.jiangxk.kotlinMediaHome.mvp.contract.VideoListContract
import com.jiangxk.kotlinMediaHome.mvp.presenter.VideoPresenter
import com.jiangxk.kotlinMediaHome.ui.adapter.VideoListAdapter
import com.jiangxk.provider.RouterPath
import com.youth.banner.Banner
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.home_fragment_home.*
import org.jetbrains.anko.find

/**
 * @desc 首页 HomeFragment
 * @auth jiangxk
 * @time 2019-10-17  17:17
 */
class HomeFragment : BaseMvpFragment<VideoPresenter>(), VideoListContract.View {

    private val videoData = ArrayList<VideoItemData>()
    private lateinit var mVideoAdapter: VideoListAdapter
    private lateinit var mLRecyclerViewAdapter: LRecyclerViewAdapter
    private lateinit var mBanner: Banner
    private lateinit var imageData: List<BannerData>
    private lateinit var mVideoData: List<VideoItemData>
    private lateinit var path: String

    private lateinit var homeCategory01: CircleImageView
    private lateinit var homeCategory02: CircleImageView
    private lateinit var homeCategory03: CircleImageView
    private lateinit var homeCategory04: CircleImageView

    companion object {
        /**
         * 列数目
         */
        private const val GRID_COLUMNS = 3
    }


    override fun injectComponent() {
        DaggerVideoComponent.builder().activityComponent(mActivityComponent)
            .videoModule(VideoModule(this))
            .build().inject(this)
    }

    override fun getLayoutId() = R.layout.home_fragment_home

    override fun onStop() {
        super.onStop()
        mBanner.stopAutoPlay()
    }

    override fun initView() {
        //设置刷新
        swipeRefresh.setColorSchemeColors(resources.getColor(R.color.home_colorRefresh))
        swipeRefresh.setSize(SwipeRefreshLayout.DEFAULT)
        mVideoAdapter = VideoListAdapter(activity!!)
        mLRecyclerViewAdapter = LRecyclerViewAdapter(mVideoAdapter)
        mRecyclerView.gridInit(activity!!, GRID_COLUMNS, mLRecyclerViewAdapter)

        //添加Header View
        val headViewBanner =
            LayoutInflater.from(context).inflate(R.layout.home_head_banner, null, false)
        mBanner = headViewBanner.find(R.id.mBanner)
        homeCategory01 = headViewBanner.find(R.id.homeCategoty01)
        homeCategory02 = headViewBanner.find(R.id.homeCategoty02)
        homeCategory03 = headViewBanner.find(R.id.homeCategoty03)
        homeCategory04 = headViewBanner.find(R.id.homeCategoty04)

        mLRecyclerViewAdapter.addHeaderView(headViewBanner)

    }

    override fun initData() {
        mPresenter.getVideoList(true)
    }

    override fun setListener() {
        //轮播图点击事件
        mBanner.setOnBannerListener {
            val imgData = imageData[it]
            path = RouterPath.Player.PATH_PLAYER
            toPlayer(imgData.link, imgData.imgUrl, imgData.name)
        }

        swipeRefresh.setOnRefreshListener {
            AppPrefsUtils.remove(Constant.KEY_JSON)
            mPresenter.getVideoList(false)
        }

        //item点击
        mLRecyclerViewAdapter.setOnItemClickListener { _, position ->
            val itemData = mVideoData[position]
            if (itemData.getItemType() == Constant.ITEM_TYPE_TITLE) {   //点击标题

            } else {
                path = if (itemData.title == mVideoData[0].title) {
                    RouterPath.Player.PATH_PLAYER
                } else {
                    RouterPath.Player.PATH_PLAYER_WEB
                }
                toPlayer(itemData.videoLink, itemData.videoImg, itemData.videoName)
            }
        }

        //搜索点击
        tvSearch.setOnClickListener {
            showMessage("搜索")
        }

        //分类点击
        homeCategory01.setOnClickListener {
            //正在热映
            showMessage("正在热映")
        }
        homeCategory02.setOnClickListener {
            //即将上映
            showMessage("即将上映")
        }
        homeCategory03.setOnClickListener {
            //电影排行
            showMessage("电影排行")
        }
        homeCategory04.setOnClickListener {
            //随便看看
            showMessage("随便看看")
        }


    }

    override fun showViewList(data: VideoListData) {
        if (swipeRefresh != null && swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }

        videoData.addAll(data.videoList)
        //设置轮播图数据
        setBanner(data.bannerUrls)
        //设置列表数据
        mLRecyclerViewAdapter.setSpanSizeLookup { _, position ->
            val type = data.videoList[position].type
            if (type == Constant.ITEM_TYPE_TITLE) {
                GRID_COLUMNS
            } else {
                1
            }
        }

        mVideoData = data.videoList
        mVideoAdapter.updateData(mVideoData)
        mLRecyclerViewAdapter.notifyDataSetChanged()
    }

    private fun toPlayer(videoUrl: String, videoImage: String, videoName: String) {
        ARouter.getInstance().build(path)
            .withString(RouterPath.Player.KEY_PLAYER, videoUrl)
            .withString(RouterPath.Player.KEY_IMAGE, videoImage)
            .withString(RouterPath.Player.KEY_NAME, videoName)
            .navigation()
    }

    private fun setBanner(bannerList: List<BannerData>) {
        imageData = bannerList
        val bannerImages = ArrayList<String>()
        val titles = ArrayList<String>()
        for (banner in bannerList) {
            bannerImages.add(banner.imgUrl)
            titles.add(banner.name)
        }
        mBanner.player(titles, bannerImages)
    }
}