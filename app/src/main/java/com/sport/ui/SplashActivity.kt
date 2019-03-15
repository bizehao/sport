package com.sport.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sport.R
import com.sport.utilities.GlobalUtil
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.NonCancellable.isActive
import timber.log.Timber

/**
 * User: bizehao
 * Date: 2019-03-15
 * Time: 上午11:17
 * Description: 启动页
 */
class SplashActivity : AppCompatActivity() {

    /**
     * 记录进入SplashActivity的时间。
     */
    var enterTime: Long = 0

    /**
     * 判断是否正在跳转或已经跳转到下一个界面。
     */
    var isForwarding = false

    /**
     * 判断是否有新版本
     */
    var hasNewVersion = false

    /**
     * 跳转动画
     */
    lateinit var logoView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        logoView = logo
        startInitRequest()
        enterTime = System.currentTimeMillis()
        delayToForward();
    }

    /**
     * 设置闪屏界面的最大延迟跳转，让用户不至于在闪屏界面等待太久。
     */
    private fun delayToForward() {
        Thread(Runnable {
            GlobalUtil.sleep(MAX_WAIT_TIME.toLong())
            forwardToNextActivity(false, null)
        }).start()
    }

    /**
     * 跳转到下一个Activity。如果在闪屏界面停留的时间还不足规定最短停留时间，则会在这里等待一会，保证闪屏界面不至于一闪而过。
     */
    private fun forwardToNextActivity(hasNewVersion: Boolean, version: Nothing?) {
        if (!isForwarding) {
            isForwarding = true
            val currentTime = System.currentTimeMillis()
            val timeSpent = currentTime - enterTime
            if (timeSpent < MIN_WAIT_TIME) {
                Timber.e("睡眠中")
                GlobalUtil.sleep(MIN_WAIT_TIME - timeSpent)
            }
            runOnUiThread {
                if (false) { //是否登录
                    MainActivity.actionStart(this)
                    finish()
                } else {
                    if (true) { //判断当前Activity是否在前台。
                        LoginActivity.actionStartWithTransition(this, logoView, hasNewVersion, version)
                    } else {
                        LoginActivity.actionStart(this, hasNewVersion, version)
                        finish()
                    }
                }
            }
        }
    }

    /**
     * 开始向服务器发送初始化请求。
     */
    private fun startInitRequest() {
        /*Init.getResponse(object : OriginThreadCallback {
            override fun onResponse(response: Response) {
                if (activity == null) {
                    return
                }
                var version: Version? = null
                val init = response as Init
                GifFun.BASE_URL = init.base
                if (!ResponseHandler.handleResponse(init)) {
                    val status = init.status
                    if (status == 0) {
                        val token = init.token
                        val avatar = init.avatar
                        val bgImage = init.bgImage
                        hasNewVersion = init.hasNewVersion
                        if (hasNewVersion) {
                            version = init.version
                        }
                        if (!TextUtils.isEmpty(token)) {
                            SharedUtil.save(Const.Auth.TOKEN, token)
                            if (!TextUtils.isEmpty(avatar)) {
                                SharedUtil.save(Const.User.AVATAR, avatar)
                            }
                            if (!TextUtils.isEmpty(bgImage)) {
                                SharedUtil.save(Const.User.BG_IMAGE, bgImage)
                            }
                            GifFun.refreshLoginState()
                        }
                    } else {
                        logWarn(TAG, GlobalUtil.getResponseClue(status, init.msg))
                    }
                }
                forwardToNextActivity(hasNewVersion, version)
            }

            override fun onFailure(e: Exception) {
                logWarn(TAG, e.message, e)
                forwardToNextActivity(false, null)
            }
        })*/
    }

    override fun onBackPressed() {
        //屏蔽手机的返回键
    }

    companion object {
        /**
         * 应用程序在闪屏界面最短的停留时间。
         */
        private const val MIN_WAIT_TIME = 2000

        /**
         * 应用程序在闪屏界面最长的停留时间。
         */
        private const val MAX_WAIT_TIME = 3000
    }


}