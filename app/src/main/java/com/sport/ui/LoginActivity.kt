package com.sport.ui

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sport.R
import com.sport.SportApplication.Companion.context
import com.sport.model.Version
import com.sport.utilities.AndroidVersion
import com.sport.utilities.GlobalUtil

/**
 * 登录界面
 */
class LoginActivity : AppCompatActivity() {

    /**
     * 是否正在进行transition动画。
     */
    protected var isTransitioning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onBackPressed() {
        if (!isTransitioning) {
            finish()
        }
    }

    companion object {

        private const val TAG = "MainActivity"

        //有新版本
        private const val INTENT_HAS_NEW_VERSION = "intent_has_new_version"

        //意图版本
        private const val INTENT_VERSION = "intent_version"

        //登录动作
        private val ACTION_LOGIN = "${GlobalUtil.getAppPackage(context)}.ACTION_LOGIN"

        //带转换的操作登录
        private val ACTION_LOGIN_WITH_TRANSITION = "${GlobalUtil.getAppPackage(context)}.ACTION_LOGIN_WITH_TRANSITION"

        //从过渡开始
        private const val START_WITH_TRANSITION = "start_with_transition"

        /**
         * 启动LoginActivity。
         *
         * @param activity
         *          原Activity的实例
         * @param hasNewVersion
         *          是否存在版本更新。
         *
         */
        fun actionStart(content: Activity, hasNewVersion: Boolean, version: Version?) {
            val intent = Intent(content, LoginActivity::class.java)
            content.startActivity(intent)
        }

        /**
         * 启动LoginActivity，并附带Transition动画。
         *
         * @param activity
         * 原Activity的实例
         * @param logo
         * 要执行transition动画的控件
         */
        fun actionStartWithTransition(activity: Activity, logo: View, hasNewVersion: Boolean, version: Version?) {
            val intent = Intent(activity, LoginActivity::class.java).apply {
                putExtra(INTENT_HAS_NEW_VERSION, hasNewVersion)
                putExtra(INTENT_VERSION, version)
                putExtra(START_WITH_TRANSITION, true)
            }

            val options = ActivityOptions.makeSceneTransitionAnimation(
                activity, logo,
                activity.getString(R.string.transition_logo_splash)
            )
            activity.startActivity(intent, options.toBundle())
        }
    }
}
