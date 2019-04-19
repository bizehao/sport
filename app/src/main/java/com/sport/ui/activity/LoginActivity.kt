package com.sport.ui.activity

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.transition.addListener
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.sport.SportApplication.Companion.context
import com.sport.common.callback.KeyboardChangeListener
import com.sport.common.ui.BaseActivity
import com.sport.databinding.ActivityLoginBinding
import com.sport.model.Version
import com.sport.utilities.GlobalUtil
import kotlinx.android.synthetic.main.activity_login.*


/**
 * 登录界面
 */
class LoginActivity : BaseActivity() {

    /**
     * 是否正在进行transition动画。
     */
    private var isTransitioning = false

    /**
     * 布局view的binding
     */
    private lateinit var binding: ActivityLoginBinding

    private lateinit var keyboardChangeListener: KeyboardChangeListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setLayout(com.sport.R.layout.activity_login)

        setupViews()
    }

    private fun setupViews() {

        val isStartWithTransition = intent.getBooleanExtra(START_WITH_TRANSITION, false)
        if (isStartWithTransition) {
            isTransitioning = true
            window.sharedElementEnterTransition.addListener(
                onEnd = {
                    isTransitioning = false
                    val intent = Intent("com.sport.localBroadcast.finishSplash")
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                    fadeElementsIn()
                })
        } else {

        }

        keyboardChangeListener = KeyboardChangeListener(this)

        keyboardChangeListener.setKeyBoardListener { isShow, keyboardHeight ->
            if (isShow) {
                binding.loginBgWallLayout.visibility = View.INVISIBLE
                val params = binding.loginLayoutTop.layoutParams as LinearLayout.LayoutParams
                params.weight = 1.5f
                binding.loginLayoutTop.requestLayout()
            } else {
                binding.loginBgWallLayout.visibility = View.VISIBLE
                val params = loginLayoutTop.layoutParams as LinearLayout.LayoutParams
                params.weight = 6f
                binding.loginLayoutTop.requestLayout()
            }

        }

        binding.loginButton.setOnClickListener {
            MainActivity.actionStart(this)
            finish()
        }

    }

    /**
     * 将LoginActivity的界面元素使用淡入的方式显示出来。
     */
    private fun fadeElementsIn() {
        TransitionManager.beginDelayedTransition(binding.loginLayoutBottom, Fade())
        binding.loginLayoutBottom.visibility = View.VISIBLE
        TransitionManager.beginDelayedTransition(binding.loginBgWallLayout, Fade())
        binding.loginBgWallLayout.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if (!isTransitioning) {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        keyboardChangeListener.destroy()
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
                activity.getString(com.sport.R.string.transition_logo_splash)
            )
            activity.startActivity(intent, options.toBundle())
        }
    }
}
